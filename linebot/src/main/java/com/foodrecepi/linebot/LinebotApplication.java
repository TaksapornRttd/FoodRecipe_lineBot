package com.foodrecepi.linebot;

import com.linecorp.bot.client.LineMessagingClient;
import org.springframework.beans.factory.annotation.Value; // ✅ CORRECT IMPORT
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LinebotApplication {

	public static void main(String[] args) {
		SpringApplication.run(LinebotApplication.class, args);
	}

	@Bean
	public LineMessagingClient lineMessagingClient(
			@Value("${line.bot.channelToken}") String token) {
		return LineMessagingClient.builder(token).build();
	}
}

