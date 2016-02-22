package com.wbitech.uploadify.controller;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wbitech.uploadify.dao.VideoUploadTempDao;
import com.wbitech.uploadify.entity.VideoUploadTemp;

@Controller
public class UploadController {

	static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
	static Integer MEMBER_ID = 10011;

	DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");

	@Autowired
	VideoUploadTempDao videoUploadTempDao;

	@RequestMapping("/player")
	public String player(String fileName, Model model, HttpServletRequest request) {
		String videoPath = request.getServletContext().getRealPath("video");
		File file = new File(videoPath + File.separator + fileName);
		if (file.exists()) {
			model.addAttribute("fileName", fileName);
		}
		return "player";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String upload(Model model, HttpServletRequest request) {
		String videoPath = request.getServletContext().getRealPath("video");
		File file = new File(videoPath);
		if (file.exists() && file.isDirectory()) {
			model.addAttribute("videos", file.list());
		}
		return "upload";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public void upload(String fileName, Long size, Long lastModifiedDate, MultipartFile file,
			HttpServletRequest request) {
		String videoPath = request.getServletContext().getRealPath("video");
		File videoPathDir = new File(videoPath);
		if (!videoPathDir.exists()) {
			videoPathDir.mkdirs();
		}

		VideoUploadTemp videoUploadTemp = videoUploadTempDao.find(MEMBER_ID, fileName, size, lastModifiedDate);
		if (videoUploadTemp == null) {
			try {
				videoUploadTemp = new VideoUploadTemp();
				videoUploadTemp.setOriginalFileName(fileName);
				videoUploadTemp.setServerFileName(fileName + "." + MEMBER_ID + ".temp");
				videoUploadTemp.setFileSize(size);
				videoUploadTemp.setLastModifiedTime(lastModifiedDate);
				videoUploadTemp.setMemberId(MEMBER_ID);
				videoUploadTemp.setStatus(0);

				File videoFile = new File(videoPath, videoUploadTemp.getServerFileName());
				FileUtils.copyInputStreamToFile(file.getInputStream(), videoFile);
				videoUploadTemp.setUploadedSize(file.getSize());
				videoUploadTempDao.insert(videoUploadTemp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			RandomAccessFile randomAccessFile = null;
			try {
				File videoFile = new File(videoPath, videoUploadTemp.getServerFileName());
				randomAccessFile = new RandomAccessFile(videoFile, "rw");
				randomAccessFile.seek(randomAccessFile.length());
				randomAccessFile.write(file.getBytes());
				videoUploadTemp.setUploadedSize(randomAccessFile.length());
				if (randomAccessFile.length() == videoUploadTemp.getFileSize()) {
					videoUploadTemp.setStatus(1);
					System.out.println(fileName + "上传完成");
				}else if(randomAccessFile.length() > videoUploadTemp.getFileSize()){
					//文件异常
					FileUtils.deleteQuietly(videoFile);
					videoUploadTemp.setUploadedSize(0L);
				}
				videoUploadTempDao.update(videoUploadTemp);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (randomAccessFile != null) {
						randomAccessFile.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (videoUploadTemp.getStatus() == 1) {
				try {
					String suffx = videoUploadTemp.getServerFileName().replace("." + MEMBER_ID, "").replace(".temp", "")
							.substring(fileName.lastIndexOf("."), fileName.length());
					String name = videoUploadTemp.getOriginalFileName().substring(0,
							videoUploadTemp.getOriginalFileName().lastIndexOf(".")) + dateFormat.format(new Date());
					File srcFile = new File(videoPath, videoUploadTemp.getServerFileName());
					FileUtils.copyFile(srcFile, new File(videoPath, name + suffx));
					FileUtils.deleteQuietly(srcFile);
					videoUploadTempDao.delete(videoUploadTemp);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@RequestMapping("/getUploadedSize")
	@ResponseBody
	public long getUploadedSize(String fileName, Long size, Long lastModifiedDate, HttpServletRequest request) {
		String videoPath = request.getServletContext().getRealPath("video");
		VideoUploadTemp videoUploadTemp = videoUploadTempDao.find(MEMBER_ID, fileName, size, lastModifiedDate);
		if (videoUploadTemp != null) {
			String serverFileName = videoUploadTemp.getServerFileName();
			File file = new File(videoPath + File.separator + serverFileName);
			if (file.exists()) {
				System.out.println("已存在文件大小：" + file.length());
				return file.length();
			}
		}
		return 0;
	}
}
