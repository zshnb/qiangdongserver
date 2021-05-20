package com.qiangdong.reader.validate.fate_board;

import cn.hutool.json.JSONUtil;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.fate_board.AddOrUpdateFateBoardRequest;
import com.qiangdong.reader.request.fate_board.MatchFateBoardRequest;
import com.qiangdong.reader.validate.RequestValidator;
import org.springframework.stereotype.Component;

@Component
public class MatchFateBoardValidator extends RequestValidator<MatchFateBoardRequest> {
    @Override
    public void validate(MatchFateBoardRequest request) {
        if (request.getInterest().isEmpty()) {
            throw new InvalidArgumentException("兴趣不能为空");
        }

        if (!JSONUtil.isJsonArray(request.getInterest())) {
            throw new InvalidArgumentException("兴趣格式错误");
        }
    }
}
