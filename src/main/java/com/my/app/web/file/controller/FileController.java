package com.my.app.web.file.controller;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.my.app.web.file.dto.FileDto;
import com.my.app.web.file.service.FileService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class FileController {
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private Validator validator;

	@GetMapping(value = "/upload")
	public String upload() {
		return "file/upload";
	}
	
//	@PostMapping(value = "/upload")
	public ResponseEntity<Void> upload(FileDto fileDto) {
//		fileService.file(fileDto);
		
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
		
		return ResponseEntity.ok().build();
	}
	
	@PostMapping(value = "/upload")
	public ResponseEntity<Void> upload(HttpServletRequest request) throws Exception {
		boolean multipart = ServletFileUpload.isMultipartContent(request);
		log.debug("multipart: {}", multipart);
		
		FileDto fileDto = new FileDto();
		
		BindingResult bindingResult = new BeanPropertyBindingResult(fileDto, "fileDto");
		validator.validate(fileDto, bindingResult);
		
//		DataBinder binder = new DataBinder(fileDto);
//		binder.setValidator(validator);
//		binder.validate();
//		BindingResult bindingResult = binder.getBindingResult();
		
		if (bindingResult.hasErrors()) {
			FieldError fieldError = bindingResult.getFieldError();
			log.error("{}, {}", fieldError.getField(), fieldError.getDefaultMessage());
		}
		
		ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
		FileItemIterator iter = upload.getItemIterator(request);
		while (iter.hasNext()) {
			FileItemStream fileItem = iter.next();
			
			if (fileItem.isFormField()) {
				log.debug("name: {}, value: {}", fileItem.getFieldName(), Streams.asString(fileItem.openStream()));
			} else {
				log.debug("name: {}, name: {}", fileItem.getFieldName(), fileItem.getName());
				
				Path path = Paths.get("C:/Users/sooin/Desktop/" + fileItem.getName());
				
				try (BufferedInputStream bis = new BufferedInputStream(fileItem.openStream());
						FileOutputStream fos = new FileOutputStream(path.toFile())) {
					byte[] b = new byte[100 * 1024];
					int read = -1;
					while ((read = bis.read(b)) != -1) {
						fos.write(b, 0, read);
						fos.flush();
					}
				} catch (Exception e) {
					log.error("file upload exception!", e);
				}
				
				log.debug("file size: {}", path.toFile().length());
				path.toFile().delete();
			}
		}
		
		return ResponseEntity.ok().build();
	}
	
}
