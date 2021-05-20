package com.qiangdong.reader;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ClientEndpoint()
public class WebSocketSimpleClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketSimpleClient.class);

    @OnOpen
    public void onOpen(Session session) {}

    @OnMessage
    public void onMessage(String message) {
        LOGGER.info("Client onMessage:" + message);
    }

    @OnClose
    public void onClose() {}

}
