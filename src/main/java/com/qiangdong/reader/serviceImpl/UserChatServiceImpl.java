package com.qiangdong.reader.serviceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.gson.JsonObject;
import com.qiangdong.reader.dao.BlockUserMapper;
import com.qiangdong.reader.dao.FollowRelationMapper;
import com.qiangdong.reader.dao.UserMapper;
import com.qiangdong.reader.dto.user_chat.UserChatDto;
import com.qiangdong.reader.entity.BlockUser;
import com.qiangdong.reader.entity.FollowRelation;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.entity.UserChat;
import com.qiangdong.reader.dao.UserChatMapper;
import com.qiangdong.reader.enums.common.CommonReadStatusEnum;
import com.qiangdong.reader.enums.common.TopEnum;
import com.qiangdong.reader.enums.user_chat.ChatStatusEnum;
import com.qiangdong.reader.enums.user_chat.ChatTagEnum;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.user_chat.*;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.response.user_chat.GetChatDetailResponse;
import com.qiangdong.reader.service.IUserChatService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiangdong.reader.utils.AppPushUtil;
import com.qiangdong.reader.utils.AssertUtil;
import com.qiangdong.reader.utils.PageUtil;
import com.qiangdong.reader.websocket.WebSocketService;
import com.qiangdong.reader.dto.WebSocketMessageDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户聊天信息表 服务实现类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-08-10
 */
@Service
public class UserChatServiceImpl extends ServiceImpl<UserChatMapper, UserChat> implements IUserChatService {
    private final UserChatMapper userChatMapper;
    private final PageUtil pageUtil;
    private final WebSocketService webSocketService;
    private final UserMapper userMapper;
    private final FollowRelationMapper followRelationMapper;
    private final BlockUserMapper blockUserMapper;
    private final AppPushUtil appPushUtil;

    public UserChatServiceImpl(UserChatMapper userChatMapper,
                               PageUtil pageUtil,
                               WebSocketService webSocketService,
                               UserMapper userMapper,
                               FollowRelationMapper followRelationMapper,
                               BlockUserMapper blockUserMapper,
                               AppPushUtil appPushUtil) {
        this.userChatMapper = userChatMapper;
        this.pageUtil = pageUtil;
        this.webSocketService = webSocketService;
        this.userMapper = userMapper;
        this.followRelationMapper = followRelationMapper;
        this.blockUserMapper = blockUserMapper;
        this.appPushUtil = appPushUtil;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Response<UserChat> sendMessage(SendMessageRequest request, User user) {
        User receiver = userMapper.selectById(request.getReceiver());
        AssertUtil.assertNotNull(receiver, "用户不存在");

        UserChat senderChat = new UserChat();
        BeanUtils.copyProperties(request, senderChat);
        senderChat.setChatUserId(request.getReceiver());
        senderChat.setSender(request.getUserId());
        senderChat.setReadStatus(CommonReadStatusEnum.READ);
        String username = request.getUsername().isEmpty() ? receiver.getUsername() : request.getUsername();
        senderChat.setUsername(username);

        BlockUser blockUser = blockUserMapper.selectOne(new QueryWrapper<BlockUser>()
            .eq("user_id", request.getReceiver())
            .eq("target_user_id", request.getUserId()));
        if (blockUser != null) {
            senderChat.setStatus(ChatStatusEnum.BLOCKED);
            save(senderChat);
            return Response.ok(senderChat);
        }

        // 更新接受者的消息记录
        UserChat receiverChat = new UserChat();
        BeanUtils.copyProperties(request, receiverChat);
        receiverChat.setUserId(request.getReceiver());
        receiverChat.setSender(request.getUserId());
        receiverChat.setChatUserId(request.getUserId());
        receiverChat.setType(request.getType());
        receiverChat.setMessage(request.getMessage());
        receiverChat.setReadStatus(CommonReadStatusEnum.UNREAD);
        receiverChat.setUsername(user.getUsername());

        List<UserChat> userChats = new ArrayList<UserChat>();
        userChats.add(senderChat);
        userChats.add(receiverChat);
        saveBatch(userChats);

        // 发送在线消息
        WebSocketMessageDto message = new WebSocketMessageDto();
        BeanUtil.copyProperties(senderChat, message);
        webSocketService.sendMsg(message);
        // APP信息推送
        appPushUtil.pushSingleUserChat(request.getReceiver(), user.getUsername(), receiver.getAvatar());
        return Response.ok(senderChat);
    }

    @Override
    public PageResponse<UserChatDto> listUserChat(BaseRequest request) {
        Integer pageNumber = (request.getPageNumber().intValue() - 1) * request.getPageSize().intValue();
        List<UserChatDto> listUserChat =
            userChatMapper.findUserChatByUserId(request.getUserId(), pageNumber, request.getPageSize());
        return PageResponse.of(listUserChat, request.getPageSize());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public PageResponse<UserChatDto> getUserChat(GetUserChatRequest request, User user) {
        IPage<UserChatDto> userChatDtos =
            userChatMapper
                .findChatByUserIdAndChatUser(pageUtil.of(request), request.getUserId(), request.getChatUserId());
        update(new UpdateWrapper<UserChat>()
            .set("read_status", CommonReadStatusEnum.READ)
            .eq("user_id", request.getUserId())
            .eq("chat_user_id", request.getChatUserId()));
        return PageResponse.of(userChatDtos, request.getPageSize());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Response<String> deleteUserChat(DeleteUserChatRequest request) {
        if (!request.getChatUserIds().isEmpty()) {
            remove(new QueryWrapper<UserChat>()
                .eq("user_id", request.getUserId())
                .in("chat_user_id", request.getChatUserIds()));
        } else {
            removeByIds(request.getUserChatIds());
        }
        return Response.ok();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Response<UserChat> updateUserChat(UpdateUserChatRequest request, UserChat userChat) {
        update(new UpdateWrapper<UserChat>()
            .set(!request.getUsername().isEmpty(), "username", request.getUsername())
            .set(request.getTop().equals(TopEnum.DEFAULT), "top", request.getTop())
            .eq("user_id", request.getUserId())
            .eq("chat_user_id", userChat.getChatUserId()));
        BeanUtils.copyProperties(request, userChat);
        return Response.ok(userChat);
    }

    @Override
    public GetChatDetailResponse getChatDetailHeader(GetChatDetailHeaderRequest request) {
        UserChat userChat =
            userChatMapper.findLastUsernameByUserIdAndChatUserId(request.getUserId(), request.getChatUserId());
        GetChatDetailResponse response = new GetChatDetailResponse();
        if (userChat == null) {
            User user = userMapper.selectById(request.getChatUserId());
            response.setUsername(user.getUsername());
            response.setTop(TopEnum.DEFAULT);
            response.setChatTag(ChatTagEnum.NONE);
        } else {
            response.setUsername(userChat.getUsername());
            response.setTop(userChat.getTop());
            response.setChatTag(userChat.getChatTag());
        }
        FollowRelation followRelation = followRelationMapper.selectOne(new QueryWrapper<FollowRelation>()
            .eq("followed_id", request.getChatUserId())
            .eq("follower_id", request.getUserId()));

        boolean follow = false;
        if (followRelation != null) {
            follow = true;
        }
        response.setFollow(follow);
        return response;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Response<String> readUserChat(ReadUserChatRequest request, UserChat userChat) {
        update(new UpdateWrapper<UserChat>()
            .set("read_status", CommonReadStatusEnum.READ)
            .eq("id", request.getUserChatId()));
        return Response.ok();
    }
}
