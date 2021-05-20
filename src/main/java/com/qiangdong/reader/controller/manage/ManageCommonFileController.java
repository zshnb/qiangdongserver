package com.qiangdong.reader.controller.manage;

import com.qiangdong.reader.controller.CommonFileController;
import com.qiangdong.reader.exception.InternalException;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.utils.OssUtil;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/manage/upload")
public class ManageCommonFileController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommonFileController.class);

	@Autowired
	private OssUtil ossUtil;

	@PostMapping("/notice-upload")
	public Response<String> uploadNotice(@RequestParam("file") MultipartFile file,
	                                     @RequestParam("noticeId") String noticeId) {
		try {
			String resultPath = ossUtil.uploadNoticePicture(file, noticeId);
			return Response.ok(resultPath);
		} catch (IOException e) {
			LOGGER.error(String.format("upload %s failed", noticeId), e);
			throw new InternalException("上传文件异常，请稍后重试");
		}
	}

	@PostMapping("/app-file-upload")
	public Response<String> uploadApkFile(@RequestParam("file") MultipartFile file,
	                                      @RequestParam("versionName") String versionName) {
		try {
			String resultPath = ossUtil.uploadAppFile(file, versionName);
			return Response.ok(resultPath);
		} catch (IOException e) {
			LOGGER.error(String.format("upload %s failed", versionName), e);
			throw new InternalException("上传文件异常，请稍后重试");
		}
	}

}
