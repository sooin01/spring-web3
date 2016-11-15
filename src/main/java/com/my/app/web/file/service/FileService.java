package com.my.app.web.file.service;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;

import com.my.app.web.file.dto.FileDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileService {

	public void file(FileDto fileDto) {
		log.debug("Content-Type: {}", fileDto.getFile().getContentType());
		log.debug("Name: {}", fileDto.getFile().getName());
		log.debug("Original filename: {}", fileDto.getFile().getOriginalFilename());
		log.debug("Size: {}", fileDto.getFile().getSize());
		
		Path path = Paths.get("C:/Users/sooin/Desktop/test.avi");
		path.toFile().delete();
		try (BufferedInputStream bis = new BufferedInputStream(fileDto.getFile().getInputStream());
				FileOutputStream fos = new FileOutputStream(path.toFile())) {
			byte[] b = new byte[4096];
			
			int read = -1;
			while ((read = bis.read(b)) != -1) {
				fos.write(b, 0, read);
				log.debug("Read: {}", read);
			}
		} catch (Exception e) {
			log.error("File exception.", e);
		}
	}
	
}
