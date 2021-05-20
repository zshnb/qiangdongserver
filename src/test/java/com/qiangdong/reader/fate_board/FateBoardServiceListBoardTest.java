package com.qiangdong.reader.fate_board;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.WebSocketSimpleClient;
import com.qiangdong.reader.dto.fate_board.FateBoardDto;
import com.qiangdong.reader.exception.InternalException;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.response.PageResponse;
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

public class FateBoardServiceListBoardTest extends BaseTest {
	@Autowired
	private FateBoardServiceImpl fateBoardService;

	@LocalServerPort
	private int randomServerPort;

	@Test
	public void listSuccessful() throws URISyntaxException, IOException, DeploymentException {
		WebSocketContainer container = ContainerProvider.getWebSocketContainer();
		URI uri = new URI("ws://localhost:" + randomServerPort + "/chatServer?user_id=2");
		Session session = container.connectToServer(new WebSocketSimpleClient(), uri);
		session.getBasicRemote().sendText("Hi");

		BaseRequest request = new BaseRequest();
		request.setUserId(1L);
		PageResponse<FateBoardDto> response = fateBoardService.listFateBoard(request);
		assertThat(response.getList().size()).isEqualTo(1);
		FateBoardDto fateBoardDto = response.getList().get(0);
		assertThat(fateBoardDto.getUserId()).isEqualTo(2L);
		session.close();
	}

	@Test
	public void listFailedWhenNoUserOnline() {
		BaseRequest request = new BaseRequest();
		request.setUserId(1L);
		assertException(InternalException.class, () -> {
			fateBoardService.listFateBoard(request);
		});
	}
}
