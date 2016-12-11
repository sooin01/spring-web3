package com.my.app.web.file.controller;

import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
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
	public ResponseEntity<Void> upload(@Valid FileDto fileDto, BindingResult bindingResult) {
		// 유효성 체크1
		bindingResult = new BeanPropertyBindingResult(fileDto, "fileDto");
		validator.validate(fileDto, bindingResult);
		
		// 유효성 체크2
		DataBinder binder = new DataBinder(fileDto);
		binder.setValidator(validator);
		binder.validate();
		bindingResult = binder.getBindingResult();
		
		if (bindingResult.hasErrors()) {
			FieldError fieldError = bindingResult.getFieldError();
			log.error("{}, {}", fieldError.getField(), fieldError.getDefaultMessage());
		}
		
		fileService.file(fileDto);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping(value = "/upload")
	public ResponseEntity<Void> upload(HttpServletRequest request) throws Exception {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		boolean multipart = ServletFileUpload.isMultipartContent(request);
		log.debug("multipart: {}", multipart);
		
		ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
		upload.setProgressListener(new ProgressListener() {
			@Override
			public void update(long pBytesRead, long pContentLength, int pItems) {
				
			}
		});
		FileItemIterator iter = upload.getItemIterator(request);
		while (iter.hasNext()) {
			FileItemStream fileItem = iter.next();
			
			if (fileItem.isFormField()) {
				log.debug("name: {}, value: {}", fileItem.getFieldName(), Streams.asString(fileItem.openStream()));
			} else {
				log.debug("name: {}, name: {}", fileItem.getFieldName(), fileItem.getName());
				
				InputStream is = fileItem.openStream();
				log.debug("File available: {}", is.available());
				
				int total = 0;
				try (BufferedInputStream bis = new BufferedInputStream(is)) {
					byte[] b = new byte[8 * 1024];
					int read = -1;
					
					do {
						if ((read = bis.read(b)) == -1) {
							break;
						}
						
						total += read;
					} while (true);
					
				} catch (Exception e) {
					log.error("file upload exception.", e);
				}
				
				if (total == 0) {
					
				}
				
				log.debug("total read size: {}", total);
			}
		}
		
		stopWatch.stop();
		log.debug("걸린시간: {}", stopWatch.toString());
		
		return ResponseEntity.ok().build();
	}
	
}
