package com.gr8fulnez.Jaiz_bank_app;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Jaiz Bank App",
				description = "Backend Rest APIs for Jaiz Bank",
				version = "v1.0",
				contact = @Contact(
						name = "John Oluwole",
						email = "oluwolejohn72@gmail.com",
						url = "https://github.com/gr8fulnez"
				),
				license = @License(
						name = "Gr8fulnez",
						url = "https://github.com/gr8fulnez"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Jaiz Bank App Documentation",
				url = "https://github.com/gr8fulnez"
		)
)
public class JaizBankAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(JaizBankAppApplication.class, args);
	}

}
