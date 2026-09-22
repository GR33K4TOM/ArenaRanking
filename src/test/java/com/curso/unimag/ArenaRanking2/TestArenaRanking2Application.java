package com.curso.unimag.ArenaRanking2;

import org.springframework.boot.SpringApplication;

public class TestArenaRanking2Application {

	public static void main(String[] args) {
		SpringApplication.from(ArenaRanking2Application::main).with(TestcontainersConfiguration.class).run(args);
	}

}
