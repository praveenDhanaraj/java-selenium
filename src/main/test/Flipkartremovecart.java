package com.sampletestpackage;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

public class Flipkartremovecart {
        private String baseURL;
        private WebDriver driver;
    ChromeDriverService service;


         String username = ""; // Change to your username and passwrod
         String password = "";

// This method is to navigate flipkart URL
@BeforeClass(alwaysRun = true)
public void setUp() throws IOException {
         baseURL = "https://www.flipkart.com";
        service = new ChromeDriverService.Builder().usingDriverExecutable(new File("/var/lib/jenkins/testing/chromedriver")).usingAnyFreePort().build();
        service.start();
    driver = new RemoteWebDriver(service.getUrl(),new ChromeOptions());
}


// To log in flipkart
@Test
public void login() {
    driver.manage().window().maximize();
    driver.get(baseURL);

driver.findElement(
By.cssSelector(".btn-express-checkout.btn-big.current"))
.click();
driver.findElement(By.cssSelector(".remove.fk-inline-block")).click();
Alert a = driver.switchTo().alert();
a.accept();
}

@AfterClass
public void quit() {
    service.stop();
driver.quit();
}
}
