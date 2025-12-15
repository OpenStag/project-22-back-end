package com.openstage.ticketbook;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Ticketbook API", version = "1.0", description = "API for Ticketbook application"))
public class TicketbookApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketbookApplication.class, args);
	}
}
