package com.my.app.web.file.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.my.app.web.file.dto.FileResultDto;
import com.my.app.web.file.service.FileService;

@Controller
public class FileController {
	
	private static final Logger log = LoggerFactory.getLogger(FileController.class);
	
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
	public ResponseEntity<FileResultDto> upload(HttpServletRequest request) throws Exception {
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
		
		InputStream inputStream = null;
		String filename =  null;
		
		FileItemIterator iter = upload.getItemIterator(request);
		while (iter.hasNext()) {
			FileItemStream fileItem = iter.next();
			
			if (fileItem.isFormField()) {
				filename = Streams.asString(fileItem.openStream());
				log.debug("name: {}, value: {}", fileItem.getFieldName(), filename);
			} else {
				log.debug("name: {}, name: {}", fileItem.getFieldName(), fileItem.getName());
				inputStream = fileItem.openStream();
				break;
			}
		}
		
		String newFilename = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(filename);
		Path path = Paths.get("C:/Users/sooin/Desktop/upload/" + newFilename);
		Files.deleteIfExists(path);
		Files.createFile(path);
		
		long totalRead = 0;
		try (BufferedInputStream bis = new BufferedInputStream(inputStream);
				OutputStream os = Files.newOutputStream(path)) {
			int read = -1;
			int count = 1;
			byte[] b = new byte[4];
			
			while ((read = bis.read(b)) != -1) {
				os.write(b, 0, read);
				os.flush();
				totalRead += read;
				
				if (totalRead / (count * 10 * 1024 * 1024) > 0) {
					log.debug("{}0MB over. TotalRead: {}", count, totalRead);
					count++;
				}
			}
		} catch (IOException e) {
			log.error("Failed upload.", e);
		}
		
		stopWatch.stop();
		log.debug("걸린시간: {}", stopWatch.toString());
		
		Files.deleteIfExists(path);
		
		FileResultDto fileResultDto = new FileResultDto();
		fileResultDto.setFileName(newFilename);
		fileResultDto.setFileSize(totalRead);
		return ResponseEntity.ok().body(fileResultDto);
	}
	
}
