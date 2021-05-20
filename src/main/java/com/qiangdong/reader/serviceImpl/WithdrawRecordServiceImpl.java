package com.qiangdong.reader.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiangdong.reader.annotation.RequireAuthor;
import com.qiangdong.reader.annotation.RequireAuthorOrEditor;
import com.qiangdong.reader.annotation.RequireEditor;
import com.qiangdong.reader.dao.ComicMapper;
import com.qiangdong.reader.dao.NovelMapper;
import com.qiangdong.reader.dao.RewardMapper;
import com.qiangdong.reader.dao.SubscribeMapper;
import com.qiangdong.reader.dto.WithdrawRecordDto;
import com.qiangdong.reader.entity.Comic;
import com.qiangdong.reader.entity.Novel;
import com.qiangdong.reader.entity.Reward;
import com.qiangdong.reader.entity.Subscribe;
import com.qiangdong.reader.entity.WithdrawRecord;
import com.qiangdong.reader.dao.WithdrawRecordMapper;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.withdraw_record.ApplyWithdrawRequest;
import com.qiangdong.reader.request.withdraw_record.ListWithdrawRecordRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.service.IWithdrawRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiangdong.reader.utils.PageUtil;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-08-29
 */
@Service
public class WithdrawRecordServiceImpl extends ServiceImpl<WithdrawRecordMapper, WithdrawRecord>
    implements IWithdrawRecordService {
    private final SubscribeMapper subscribeMapper;
    private final RewardMapper rewardMapper;
    private final ComicMapper comicMapper;
    private final NovelMapper novelMapper;
    private final WithdrawRecordMapper withdrawRecordMapper;
    private final PageUtil pageUtil;

    public WithdrawRecordServiceImpl(SubscribeMapper subscribeMapper, RewardMapper rewardMapper,
                                     ComicMapper comicMapper, NovelMapper novelMapper,
                                     WithdrawRecordMapper withdrawRecordMapper,
                                     PageUtil pageUtil) {
        this.subscribeMapper = subscribeMapper;
        this.rewardMapper = rewardMapper;
        this.comicMapper = comicMapper;
        this.novelMapper = novelMapper;
        this.withdrawRecordMapper = withdrawRecordMapper;
        this.pageUtil = pageUtil;
    }

    /**
     * 作者申请提现
     * */
    @Override
    @RequireAuthor
    @Transactional(rollbackFor = RuntimeException.class)
    public Response<String> applyWithdraw(ApplyWithdrawRequest request) {
        List<Long> worksIds = comicMapper.selectList(new QueryWrapper<Comic>()
            .select("id")
            .eq("author_id", request.getUserId()))
            .stream()
            .map(Comic::getId)
            .collect(Collectors.toList());

        worksIds.addAll(novelMapper.selectList(new QueryWrapper<Novel>()
            .select("id")
            .eq("author_id", request.getUserId()))
            .stream()
            .map(Novel::getId)
            .collect(Collectors.toList()));

        if (!CollectionUtils.isEmpty(worksIds)) {
            double subscribeCoin = subscribeMapper.selectList(new QueryWrapper<Subscribe>()
                .in("works_id", worksIds))
                .stream()
                .mapToDouble(Subscribe::getCoin)
                .sum();

            int rewardCoin = rewardMapper.selectList(new QueryWrapper<Reward>()
                .in("works_id", worksIds))
                .stream()
                .mapToInt(Reward::getCount)
                .sum();

            BigDecimal income = new BigDecimal(String.valueOf(subscribeCoin + rewardCoin));
            if (income.subtract(request.getMoney()).compareTo(new BigDecimal("0.00")) < 0) {
                throw new InvalidArgumentException("提现失败，余额不足");
            }

            WithdrawRecord withdrawRecord = new WithdrawRecord();
            withdrawRecord.setUserId(request.getUserId());
            withdrawRecord.setMoney(request.getMoney());
            save(withdrawRecord);
            return Response.ok(income.subtract(request.getMoney()).toString());
        }
        throw new InvalidArgumentException("提现失败，暂无收入");
    }

    /**
     * 查看提现记录
     * */
    @Override
    @RequireAuthorOrEditor
    public PageResponse<WithdrawRecordDto> listWithdrawRecords(ListWithdrawRecordRequest request) {
        if (request.getTargetUserId() == 0L) {
            return PageResponse.of(
                withdrawRecordMapper.findByUserId(pageUtil.of(request), request.getUserId()), request.getPageSize());
        } else {
            return PageResponse.of(
                withdrawRecordMapper.findByUserId(pageUtil.of(request), request.getTargetUserId()), request.getPageSize());
        }
    }
}
