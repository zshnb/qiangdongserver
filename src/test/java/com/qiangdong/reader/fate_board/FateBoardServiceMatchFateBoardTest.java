package com.qiangdong.reader.fate_board;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.fate_board.MatchFateBoardRequest;
import com.qiangdong.reader.serviceImpl.FateBoardServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FateBoardServiceMatchFateBoardTest extends BaseTest {
    @Autowired
    private FateBoardServiceImpl fateBoardService;

    @Test
    public void matchFailedWhenInterestIsEmpty() {
        MatchFateBoardRequest request = new MatchFateBoardRequest();
        assertException(InvalidArgumentException.class, () -> fateBoardService.matchFateBoard(request));
    }

    @Test
    public void matchFailedWhenInterestIsNotJsonArray() {
        MatchFateBoardRequest request = new MatchFateBoardRequest();
        request.setInterest("hello");
        assertException(InvalidArgumentException.class, () -> fateBoardService.matchFateBoard(request));
    }

    @Test
    public void matchFailedWhenFateBoardNotExist() {
        MatchFateBoardRequest request = new MatchFateBoardRequest();
        request.setUserId(-1L);
        assertException(InvalidArgumentException.class, () -> fateBoardService.matchFateBoard(request));
    }
}
