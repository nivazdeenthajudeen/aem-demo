package com.mailcompany.core.test;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import java.time.Duration;
public class TestingAutomation {
	
	 public static void main(String[] args) {
	        WebDriver driver = new FirefoxDriver();
	        WebDriverWait wait = new WebDriverWait(driver, 10L);
	        try {
	            driver.get("https://google.com/ncr");
	            driver.findElement(By.name("q")).sendKeys("cheese" + Keys.ENTER);
	        } finally {
	            driver.quit();
	        }
	    }

}
