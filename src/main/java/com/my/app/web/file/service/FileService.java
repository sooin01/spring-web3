package com.my.app.web.file.service;

import java.io.BufferedInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.my.app.web.file.dto.FileDto;

@Service
public class FileService {
	
	private static final Logger log = LoggerFactory.getLogger(FileService.class);

	public void file(FileDto fileDto) {
		log.debug("Content-Type: {}", fileDto.getFile().getContentType());
		log.debug("Name: {}", fileDto.getFile().getName());
		log.debug("Original filename: {}", fileDto.getFile().getOriginalFilename());
		log.debug("Size: {}", fileDto.getFile().getSize());
		
		Path path = Paths.get("C:/Users/sooin/Desktop/test.avi");
		path.toFile().delete();
		
		try (BufferedInputStream bis = new BufferedInputStream(fileDto.getFile().getInputStream())) {
			byte[] b = new byte[2048];
			
			int read = -1;
			while ((read = bis.read(b)) != -1) {
				log.debug("Read: {}", read);
			}
		} catch (Exception e) {
			log.error("File exception.", e);
		}
	}
	
}
