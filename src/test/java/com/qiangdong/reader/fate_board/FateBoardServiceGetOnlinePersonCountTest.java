package com.qiangdong.reader.fate_board;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.WebSocketSimpleClient;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.serviceImpl.FateBoardServiceImpl;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;

public class FateBoardServiceGetOnlinePersonCountTest extends BaseTest {
	@Autowired
	private FateBoardServiceImpl fateBoardService;

	@LocalServerPort
	private int randomServerPort;

	@Test
	public void getSuccessful() throws URISyntaxException, IOException, DeploymentException {
		WebSocketContainer container = ContainerProvider.getWebSocketContainer();
		URI uri = new URI("ws://localhost:" + randomServerPort +"/chatServer?user_id=1");
		Session session = container.connectToServer(new WebSocketSimpleClient(), uri);
		session.getBasicRemote().sendText("Hi");
		int count = fateBoardService.getOnlinePersonCount(new BaseRequest()).getData();
		assertThat(count).isEqualTo(1);
		session.close();
	}
}
