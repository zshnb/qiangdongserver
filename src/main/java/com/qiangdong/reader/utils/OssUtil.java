package com.qiangdong.reader.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qiangdong.reader.config.TencentOssConfig;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class OssUtil {
	private final COSClient cosClient;
	private final TencentOssConfig ossConfig;
	private final UuidUtil uuidUtil;

	public OssUtil(COSClient cosClient, TencentOssConfig ossConfig, UuidUtil uuidUtil) {
		this.cosClient = cosClient;
		this.ossConfig = ossConfig;
		this.uuidUtil = uuidUtil;
	}

	public String uploadNovelChapterTxt(MultipartFile file, String novelId) throws IOException {
		String fileName = uuidUtil.uuid();
		String key = String.format("%s/%s.txt", novelId, fileName);
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentEncoding(StandardCharsets.UTF_8.name());
		PutObjectRequest putObjectRequest =
			new PutObjectRequest(ossConfig.getNovelBucketName(), key, file.getInputStream(), objectMetadata);
		cosClient.putObject(putObjectRequest);
		cosClient.shutdown();
		return String.format("%s/%s", ossConfig.getNovelDomain(), key);
	}

	public String uploadNovelChapterTxt(String content, Long novelId) throws IOException {
		String uuid = uuidUtil.uuid();
		File file = File.createTempFile(uuid, ".txt");
		FileUtils.write(file, content, StandardCharsets.UTF_8);
		String fileName = uuidUtil.uuid();
		String key = String.format("%s/%s.txt", novelId, fileName);
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentEncoding(StandardCharsets.UTF_8.name());
		PutObjectRequest putObjectRequest =
			new PutObjectRequest(ossConfig.getNovelBucketName(), key, new FileInputStream(file), objectMetadata);
		cosClient.putObject(putObjectRequest);
		cosClient.shutdown();
		return String.format("%s/%s", ossConfig.getNovelDomain(), key);
	}

	public String uploadComicChapterPicture(MultipartFile file, String comicId) throws IOException {
		String fileName = uuidUtil.uuid();
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		String key = String.format("%s/%s.%s", comicId, fileName, extension);
		ObjectMetadata objectMetadata = new ObjectMetadata();
		PutObjectRequest putObjectRequest =
			new PutObjectRequest(ossConfig.getComicBucketName(), key, file.getInputStream(), objectMetadata);
		cosClient.putObject(putObjectRequest);
		cosClient.shutdown();
		return String.format("%s/%s", ossConfig.getComicDomain(), key);
	}

	public String uploadNoticePicture(MultipartFile file, String noticeId) throws IOException {
		String fileName = uuidUtil.uuid();
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		String key = String.format("%s/%s.%s", noticeId, fileName, extension);
		ObjectMetadata objectMetadata = new ObjectMetadata();
		PutObjectRequest putObjectRequest =
			new PutObjectRequest(ossConfig.getNoticeBucketName(), key, file.getInputStream(), objectMetadata);
		cosClient.putObject(putObjectRequest);
		cosClient.shutdown();
		return String.format("%s/%s", ossConfig.getNoticeDomain(), key);
	}

	public String uploadAppFile(MultipartFile file, String versionId) throws IOException {
		String fileName = uuidUtil.uuid();
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		String key = String.format("%s/%s.%s", versionId, fileName, extension);
		ObjectMetadata objectMetadata = new ObjectMetadata();
		PutObjectRequest putObjectRequest =
			new PutObjectRequest(ossConfig.getApkFileBucketName(), key, file.getInputStream(), objectMetadata);
		cosClient.putObject(putObjectRequest);
		cosClient.shutdown();
		return String.format("%s/%s", ossConfig.getApkFileDomain(), key);
	}

	public String uploadImage(MultipartFile file) throws IOException {
		String fileName = uuidUtil.uuid();
		File tempFile = File.createTempFile(fileName, ".jpg");
		Thumbnails.of(file.getInputStream())
			.scale(1.0)
			.outputQuality(0.3F)
			.outputFormat("jpg")
			.toFile(tempFile);
		String key = String.format("images/%s.jpg", fileName);
		ObjectMetadata objectMetadata = new ObjectMetadata();
		PutObjectRequest putObjectRequest =
			new PutObjectRequest(ossConfig.getImageBucketName(), key, new FileInputStream(tempFile), objectMetadata);
		cosClient.putObject(putObjectRequest);
		cosClient.shutdown();
		return String.format("%s/%s", ossConfig.getImageDomain(), key);
	}
}
