package com.kh.start.file.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.start.exception.FileUploadException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileService {
	
	private final Path fileLocation;
	
	public FileService() {
		this.fileLocation = Paths.get("uploads").toAbsolutePath().normalize();
	}
	
	public String store(MultipartFile file) {	
		//현재날짜+시간+랜덤숫자+원본파일 확장자
		StringBuilder sb = new StringBuilder();
		String currentTime = new SimpleDateFormat("yyyyMMdd_HHmmss_").format(new Date());
		//log.info("지금시간 :{}", currentTime);
		sb.append(currentTime);
		
		int random = (int)(Math.random() * 900) + 100;
		sb.append(random);
		
		String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		sb.append(ext);			
		
		Path tagetLocation = this.fileLocation.resolve(sb.toString());
		//log.info("저장경로 {}",tagetLocation);
		try {
			Files.copy(file.getInputStream(), tagetLocation, StandardCopyOption.REPLACE_EXISTING);
			return "http://localhost/uploads/" + sb.toString();
		}catch (IOException e) {
			throw new FileUploadException("파일이 이상합니다.");
		}		
	}

}
