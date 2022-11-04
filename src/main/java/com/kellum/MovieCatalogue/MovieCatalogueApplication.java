package com.kellum.MovieCatalogue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.kellum.MovieCatalogue.logging.KellumLogger;

@SpringBootApplication
public class MovieCatalogueApplication {

	public static void main(String[] args) {
		KellumLogger.shared.logUsage("Application Started");
		SpringApplication.run(MovieCatalogueApplication.class, args);
	}

}
