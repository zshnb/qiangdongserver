package com.qiangdong.reader.controller;


import com.qiangdong.reader.dto.user_chat.UserChatDto;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.entity.UserChat;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.user_chat.*;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.response.user_chat.GetChatDetailResponse;
import com.qiangdong.reader.serviceImpl.UserChatServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户聊天信息表 前端控制器
 * </p>
 *
 * @author qiangdong app
 * @since 2020-08-10
 */
@RestController
@RequestMapping("/user-chat")
public class UserChatController {

    @Autowired
    private UserChatServiceImpl userChatService;

    /**
     * 发送信息
     */
    @PostMapping("/send-message")
    public Response<UserChat> sendMessage(@RequestBody SendMessageRequest request){
        return userChatService.sendMessage(request, new User());
    }

    /**
     * 展示聊天列表
     */
    @PostMapping("/list-chat")
    public PageResponse<UserChatDto> listUserChat(@RequestBody BaseRequest request){
        return userChatService.listUserChat(request);
    }

    /**
     * 聊天详情
     */
    @PostMapping("/detail")
    public PageResponse<UserChatDto> getUserChat(@RequestBody GetUserChatRequest request) {
        return userChatService.getUserChat(request, new User());
    }

    /**
     * 删除聊天信息
     */
    @DeleteMapping("/delete-batch")
    public Response<String> deleteUserChat(@RequestBody DeleteUserChatRequest request) {
        return userChatService.deleteUserChat(request);
    }

    @PostMapping("/update")
    public Response<UserChat> updateUserChat(@RequestBody UpdateUserChatRequest request){
        return userChatService.updateUserChat(request, new UserChat());
    }

    @PostMapping("/detail/header")
    public GetChatDetailResponse getChatDetailHeader(@RequestBody GetChatDetailHeaderRequest request){
        return userChatService.getChatDetailHeader(request);
    }

    @PostMapping("/read")
    public Response<String> readUserChat(@RequestBody ReadUserChatRequest request){
        return userChatService.readUserChat(request, new UserChat());
    }
}
