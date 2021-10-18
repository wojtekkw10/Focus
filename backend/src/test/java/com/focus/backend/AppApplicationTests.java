package com.focus.backend;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AppApplicationTests {
	private static WebDriver driver;

	@BeforeAll
	static void init(){
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
		driver = new ChromeDriver();
	}

	@AfterAll
	static void shutDown(){
		driver.quit();
	}

	@Test
	public void shouldNavigateToSignInGivenNoAuthentication(){
		driver.get("http://localhost:4200");

		boolean containsSignIn = StringUtils.containsIgnoreCase(driver.getPageSource(), "SIGN IN");

		assertTrue(containsSignIn);
		assertTrue(driver.getCurrentUrl().contains("login"));
	}

}
