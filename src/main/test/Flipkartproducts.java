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

public class Flipkartproducts {
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


driver.findElement(By.name("q")).sendKeys("moto g3");
driver.findElement(
By.cssSelector("search-bar-submit.fk-font-13.fk-font-bold"))
.click();

// select the first item in the search results
String css = ".gd-row.browse-grid-row:nth-of-type(1) > div:nth-child(1)>div>div:nth-child(2)>div>a";
driver.findElement(By.cssSelector(css)).click();
}

@AfterClass
public void quit() {
    service.stop();
driver.quit();
}
}
