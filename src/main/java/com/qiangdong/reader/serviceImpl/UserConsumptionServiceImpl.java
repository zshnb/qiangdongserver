package com.qiangdong.reader.serviceImpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qiangdong.reader.dao.ComicMapper;
import com.qiangdong.reader.dao.NovelMapper;
import com.qiangdong.reader.dao.UserMapper;
import com.qiangdong.reader.dto.WorksIncomeUserRankingDto;
import com.qiangdong.reader.entity.Comic;
import com.qiangdong.reader.entity.Novel;
import com.qiangdong.reader.entity.UserConsumption;
import com.qiangdong.reader.dao.UserConsumptionMapper;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.user.ListWorksIncomeUserRankingRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.service.IUserConsumptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiangdong.reader.utils.AssertUtil;
import com.qiangdong.reader.utils.PageUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-22
 */
@Service
public class UserConsumptionServiceImpl extends ServiceImpl<UserConsumptionMapper, UserConsumption>
	implements IUserConsumptionService {
	private final UserConsumptionMapper userConsumptionMapper;
	private final ComicMapper comicMapper;
	private final NovelMapper novelMapper;
	private final PageUtil pageUtil;

	public UserConsumptionServiceImpl(UserConsumptionMapper userConsumptionMapper,
	                                  ComicMapper comicMapper, NovelMapper novelMapper,
	                                  PageUtil pageUtil) {
		this.userConsumptionMapper = userConsumptionMapper;
		this.comicMapper = comicMapper;
		this.novelMapper = novelMapper;
		this.pageUtil = pageUtil;
	}

	/**
	 * 书籍内页-打赏榜单接口
	 * */
	@Override
	public PageResponse<WorksIncomeUserRankingDto> listWorksWorksIncomeUserRanking(
		ListWorksIncomeUserRankingRequest request) {
		switch (request.getWorksType()) {
			case COMIC: {
				Comic comic = comicMapper.selectById(request.getWorksId());
				AssertUtil.assertNotNull(comic, "漫画不存在");
				IPage<WorksIncomeUserRankingDto> userRankingDtoIPage =
					userConsumptionMapper.findComicIncomeUserRankingDtoById(pageUtil.of(request), request.getWorksId(),
						request.getConsumptionType().code());
				List<WorksIncomeUserRankingDto> userRankingDtos = userRankingDtoIPage.getRecords();
				userRankingDtos.sort((o1, o2) -> o2.getSum().compareTo(o1.getSum()));
				return PageResponse.of(userRankingDtos, request.getPageSize());
			}
			case NOVEL: {
				Novel novel = novelMapper.selectById(request.getWorksId());
				AssertUtil.assertNotNull(novel, "小说不存在");
				IPage<WorksIncomeUserRankingDto> userRankingDtoIPage =
					userConsumptionMapper.findNovelIncomeUserRankingDtoById(pageUtil.of(request), request.getWorksId(),
						request.getConsumptionType().code());
				List<WorksIncomeUserRankingDto> userRankingDtos = userRankingDtoIPage.getRecords();
				userRankingDtos.sort((o1, o2) -> o2.getSum().compareTo(o1.getSum()));
				return PageResponse.of(userRankingDtos, request.getPageSize());
			}
			default: throw new InvalidArgumentException("无效的作品类型");
		}
	}
}
