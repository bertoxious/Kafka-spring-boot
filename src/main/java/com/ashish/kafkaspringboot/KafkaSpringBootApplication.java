package com.ashish.kafkaspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author : Ashish Uniyal
 */
@EnableScheduling
@EnableKafka
@SpringBootApplication
public class KafkaSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaSpringBootApplication.class, args);
		KafkaSpringBootApplication k = new KafkaSpringBootApplication();
		System.out.println("SENDING MESSAGE IS CALLED");
		System.out.println("SENDING MESSAGE FINISHED");
	}

	@KafkaListener(
			id="test",
			topics="test",
			containerFactory = "someRequestDataConcurrentKafkaListenerContainerFactory"
	)
	public void listener(String string){
		System.out.println("GETTING INSIDE KAFKA LISTENER");
		System.out.println(string);
		System.out.println("GETTING OUTSIDE KAFKA LISTENER");
	}


}
