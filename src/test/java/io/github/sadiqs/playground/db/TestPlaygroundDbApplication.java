package io.github.sadiqs.playground.db;

import org.springframework.boot.SpringApplication;

public class TestPlaygroundDbApplication {

	public static void main(String[] args) {
		SpringApplication.from(PlaygroundDbApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
