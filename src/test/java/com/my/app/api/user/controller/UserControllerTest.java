package com.my.app.api.user.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.Test;
import org.springframework.http.MediaType;

import com.my.TestContext;

public class UserControllerTest extends TestContext {

	@Test
	public void testGetUsers() throws Exception {
		mockMvc.perform(get("/users").accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andDo(print())
		.andDo(document("/users", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
	}

}
