package com.qiangdong.reader.service;

import com.qiangdong.reader.dto.user_chat.UserChatDto;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.entity.UserChat;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.user_chat.*;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.response.user_chat.GetChatDetailResponse;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户聊天信息表 服务类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-08-10
 */
public interface IUserChatService extends IService<UserChat> {
    Response<UserChat> sendMessage(SendMessageRequest request, User user);

    PageResponse<UserChatDto> listUserChat(BaseRequest request);

    PageResponse<UserChatDto> getUserChat(GetUserChatRequest request, User user);

    Response<String> deleteUserChat(DeleteUserChatRequest request);

    Response<UserChat> updateUserChat(UpdateUserChatRequest request, UserChat userChat);

    GetChatDetailResponse getChatDetailHeader(GetChatDetailHeaderRequest request);

    Response<String> readUserChat(ReadUserChatRequest request, UserChat user);
}
