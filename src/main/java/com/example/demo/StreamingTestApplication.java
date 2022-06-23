package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StreamingTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreamingTestApplication.class, args);
        Command<Employee> command = new SpecificCommand();
        Employee e = command.executeCommand();
        System.out.println(e.toString());
        System.out.println("Test");
	}

}
