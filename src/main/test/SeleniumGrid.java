 package com.objectrepository.demo;
 import org.openqa.selenium.*;
 import org.openqa.selenium.remote.DesiredCapabilities;
 import java.net.MalformedURLException;
 import java.net.URL;
 import org.openqa.selenium.remote.RemoteWebDriver;
 import org.testng.Assert;
 import org.testng.annotations.*;

 public class SeleniumGrid {

     WebDriver driver;
     String baseURL, nodeURL;

     @BeforeTest
     public void setUp() throws MalformedURLException {
         baseURL = "https://www.w3schools.com/java/default.asp";
         nodeURL = "http://selenium-hub.hema.svc.cluster.local:4444/wd/hub";
         ChromeOptions options = new ChromeOptions();
         capability.setBrowserName("chrome");
         capability.setPlatform(Platform.LINUX);
         System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
         driver = new RemoteWebDriver(new URL(nodeURL), capability);
     }

     @AfterTest
     public void afterTest() {
         driver.quit();
     }
     @Test
     public void sampleTest() {
         driver.get(baseURL);
         

         if (driver.getPageSource().contains("JAVASCRIPT")) {
             Assert.assertTrue(true, "java script Link Found");
         } else {
             Assert.assertTrue(false, "Failed: Link not found");
         }

     }
    @Test
    public void clickreferences() {
    try
    {
    Thread.sleep(30); 
    }
    catch(Exception e)
    {
        driver.get(baseURL);
        driver.findElement(By.id("topnavbtn_references")).click();
     }
    }
    @Test
    public void pythonreferences() {
    try
    {
    Thread.sleep(30); 
    }
    catch(Exception e)
    {
        driver.get(baseURL);
        driver.findElement(By.className("w3-bar-item w3-button")).click();
     }
    }

        @Test
    public void searchingkeywords() {
    try
    {
    Thread.sleep(30); 
    }
    catch(Exception e)
    {
        driver.get(baseURL);
        driver.findElement(By.className("bigbtn")).click();
     }
    }

 }
