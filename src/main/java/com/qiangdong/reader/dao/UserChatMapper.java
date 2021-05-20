package com.qiangdong.reader.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiangdong.reader.dto.user_chat.UserChatDto;
import com.qiangdong.reader.entity.UserChat;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 用户聊天信息表 Mapper 接口
 * </p>
 *
 * @author qiangdong app
 * @since 2020-08-10
 */
@Repository
public interface UserChatMapper extends BaseMapper<UserChat> {

    List<UserChatDto> findUserChatByUserId(@Param("userId") Long userId,
                                           @Param("pageNumber") Integer pageNumber,
                                           @Param("pageSize")Long pageSize);

    IPage<UserChatDto> findChatByUserIdAndChatUser(Page<?> page,
                                                   @Param("userId") Long userId,
                                                   @Param("chatUserId") Long chatUserId);

    UserChat findLastUsernameByUserIdAndChatUserId(@Param("userId") Long userId,
                                                   @Param("chatUserId") Long chatUserId);
}
