package com.my.app.web.file.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
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
	public ResponseEntity<Void> upload(ServletInputStream sis) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss.SSS");
		
		try {
			byte[] b = new byte[8 * 1024];
			int read = -1;
			while ((read = sis.read(b)) != -1) {
				System.out.println(format.format(new Date()) + " read: " + read);
//				Thread.sleep(5);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { sis.close(); } catch (IOException e) { }
		}
		
		return ResponseEntity.ok().build();
	}
	
//	@PostMapping(value = "/upload")
	public ResponseEntity<Void> upload(FileDto fileDto) {
//		fileService.file(fileDto);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping(value = "/upload")
	public ResponseEntity<Void> upload(HttpServletRequest request) throws Exception {
		boolean multipart = ServletFileUpload.isMultipartContent(request);
		log.debug("multipart: {}", multipart);
		
		FileDto fileDto = new FileDto();
		
//		BindingResult bindingResult = new BeanPropertyBindingResult(fileDto, "fileDto");
//		validator.validate(fileDto, bindingResult);
		
//		DataBinder binder = new DataBinder(fileDto);
//		binder.setValidator(validator);
//		binder.validate();
//		BindingResult bindingResult = binder.getBindingResult();
		
//		if (bindingResult.hasErrors()) {
//			FieldError fieldError = bindingResult.getFieldError();
//			log.error("{}, {}", fieldError.getField(), fieldError.getDefaultMessage());
//		}
		
//		ProgressListener progressListener = new ProgressListener() {
//			@Override
//			public void update(long pBytesRead, long pContentLength, int pItems) {
//				log.debug(">>> {}, {}, {}", pBytesRead, pContentLength, pItems);
//			}
//		};
		
		ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
//		upload.setProgressListener(progressListener);
		FileItemIterator iter = upload.getItemIterator(request);
		while (iter.hasNext()) {
			FileItemStream fileItem = iter.next();
			
			if (fileItem.isFormField()) {
				log.debug("name: {}, value: {}", fileItem.getFieldName(), Streams.asString(fileItem.openStream()));
			} else {
				log.debug("name: {}, name: {}", fileItem.getFieldName(), fileItem.getName());
				
				StopWatch stopWatch = new StopWatch();
				stopWatch.start();
				int total = 0;
				try (BufferedInputStream bis = new BufferedInputStream(fileItem.openStream())) {
					byte[] b = new byte[100 * 1024];
					int read = -1;
					while ((read = bis.read(b)) != -1) {
						total += read;
					}
				} catch (Exception e) {
					log.error("file upload exception!", e);
				}
				
				stopWatch.stop();
				log.debug("total read size: {}", total);
				log.debug("걸린시간: {}", stopWatch.toString());
			}
		}
		
		return ResponseEntity.ok().build();
	}
	
}
