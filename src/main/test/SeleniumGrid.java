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
         DesiredCapabilities capability = DesiredCapabilities.chrome();
         capability.setBrowserName("chrome");
         capability.setPlatform(Platform.LINUX);
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
    public void javalearning() {
        driver.get(baseURL);
         

         if (driver.getPageSource().contains("Start learning JavaScript now »")) {
             Assert.assertTrue(true, "learning Link Found");
         } else {
             Assert.assertTrue(false, "Failed: Link not found");
         }

     }

 }
