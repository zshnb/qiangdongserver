package com.qiangdong.reader.util;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.utils.SensitiveWordUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class SensitiveWordUtilTest extends BaseTest {
    @Autowired
    private SensitiveWordUtil sensitiveWordUtil;

    @Test
    public void detectSuccessful() {
        String word = "解放军今日去了省长的办公室，发现市长也在里面交流";
        Set<String> badWords = sensitiveWordUtil.getBadWord(word, SensitiveWordUtil.MatchType.MAX_MATCH_TYPE);
        assertThat(badWords.size()).isEqualTo(3);
    }
}
