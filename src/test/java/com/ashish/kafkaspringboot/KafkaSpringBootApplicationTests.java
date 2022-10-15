package com.ashish.kafkaspringboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootTest
class KafkaSpringBootApplicationTests {
	@Autowired
	private KafkaTemplate<String, String> template;

	@Test
	void contextLoads() {

	}
	@Test
	void sendMessageToKafkaProducer(){
		System.out.println("S E N D I N G - Student object from test case");
		template.send("test", new Student("Ashish", "Uniyal").toString());
	}


}
