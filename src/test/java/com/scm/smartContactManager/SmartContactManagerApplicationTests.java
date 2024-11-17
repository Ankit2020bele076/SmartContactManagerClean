package com.scm.smartContactManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.scm.smartContactManager.services.EmailService;

@SpringBootTest
class SmartContactManagerApplicationTests {

	@Autowired
	private EmailService emailService;

	@Test
	void contextLoads() {
	}

	@Test
	void EmailTesting(){
		emailService.sendEmail("ankitpayal160@gmail.com", "Test mail", "hiiii this is scm verification mail");
	}

}
