package com.qiangdong.reader.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiangdong.reader.dao.ComicMapper;
import com.qiangdong.reader.dao.NovelMapper;
import com.qiangdong.reader.dao.UserMapper;
import com.qiangdong.reader.dto.user.UserDto;
import com.qiangdong.reader.entity.Comic;
import com.qiangdong.reader.entity.Novel;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.enums.user.UserCreatorIdentityEnum;
import com.qiangdong.reader.enums.user.UserSexEnum;
import com.qiangdong.reader.repository.UserRepository;
import com.qiangdong.reader.search.UserForSearch;
import java.time.LocalDateTime;
import java.time.Period;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.util.DigestUtils;

@Component
public class UserUtil {
    private final NovelMapper novelMapper;
    private final ComicMapper comicMapper;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserUtil(NovelMapper novelMapper, ComicMapper comicMapper, UserMapper userMapper,
                    UserRepository userRepository) {
        this.novelMapper = novelMapper;
        this.comicMapper = comicMapper;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public boolean isValidMobile(String mobile) {
        String regex = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    public String blurPhone(String phone){
        String phoneBlurRegex = "(\\d{3})\\d{4}(\\d{4})";
        String phoneBlurReplaceRegex = "$1****$2";
        return phone.replaceAll(phoneBlurRegex, phoneBlurReplaceRegex);
    }

    public int calculationAuthorCompletion(User user) {
        double record = 8;
        int notNullRecord = 8;
        if (user.getNickname().isEmpty()) {
            notNullRecord--;
        }
        if (user.getIdCard().isEmpty()) {
            notNullRecord--;
        }
        if (user.getAvatar().isEmpty()) {
            notNullRecord--;
        }
        if (user.getEmail().isEmpty()) {
            notNullRecord--;
        }
        if (user.getSex().equals(UserSexEnum.NONE)) {
            notNullRecord--;
        }
        if (user.getAuthName().isEmpty()){
            notNullRecord--;
        }
        if (user.getQqAccount().isEmpty()){
            notNullRecord--;
        }
        if (user.getAddress().isEmpty()){
            notNullRecord--;
        }
        return (int) (int)((notNullRecord / record) * 100);
    }

    public boolean validPassword(String encryptionPassword, String unencryptedPassword) {
        return (!encryptionPassword.equals(DigestUtils.md5DigestAsHex(unencryptedPassword.getBytes())));
    }

    /**
     * 设置作者创作者标识
     * */
    public void setCreatorIdentity(UserDto userDto) {
        int novelCount = novelMapper.selectCount(new QueryWrapper<Novel>()
            .eq("author_id", userDto.getId()));
        int comicCount = comicMapper.selectCount(new QueryWrapper<Comic>()
            .eq("author_id", userDto.getId()));
        if (novelCount != 0 && comicCount != 0) {
            userDto.setCreatorIdentity(UserCreatorIdentityEnum.AUTHOR_AND_PAINTER);
        } else if (comicCount != 0) {
            userDto.setCreatorIdentity(UserCreatorIdentityEnum.PAINTER);
        } else if (novelCount != 0) {
            userDto.setCreatorIdentity(UserCreatorIdentityEnum.AUTHOR);
        }
    }

    /**
     * 更新登录时间
     * */
    public void afterLogin(User user) {
        LocalDateTime now = LocalDateTime.now();
        if (!user.getLastLoginTime().toLocalDate().isEqual(now.toLocalDate())) {
            user.setLastLoginTime(LocalDateTime.now());
            userMapper.updateById(user);
        }
    }

    public int getAge(User user) {
        return Period.between(user.getBirthday().toLocalDate(), LocalDateTime.now().toLocalDate()).getYears();
    }

    public void indexUser(User user) {
        UserForSearch userForSearch = new UserForSearch();
        userForSearch.setId(user.getId());
        userForSearch.setUsername(user.getUsername());
        userForSearch.setAvatar(user.getAvatar());
        userRepository.save(userForSearch);
    }
}
