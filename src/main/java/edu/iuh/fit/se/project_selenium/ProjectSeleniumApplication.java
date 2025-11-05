package edu.iuh.fit.se.project_selenium;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import edu.iuh.fit.se.project_selenium.util.PasswordHashGenerator;

@SpringBootApplication
public class ProjectSeleniumApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectSeleniumApplication.class, args);
        PasswordHashGenerator.main(args);
    }

}
