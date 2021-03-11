 package com.objectrepository.demo;
 import org.openqa.selenium.*;
 import org.openqa.selenium.remote.DesiredCapabilities;
 import java.net.MalformedURLException;
 import java.net.URL;
 import org.openqa.selenium.remote.RemoteWebDriver;
 import org.testng.Assert;
 import org.testng.annotations.*;
 import java.util.concurrent.TimeUnit;
 
 public class SeleniumGridreferences {
     private String baseURL;
     private WebDriver driver;


     @BeforeClass
     public void setUp() throws MalformedURLException {
         baseURL = "https://www.w3schools.com/java/default.asp";
        baseURL = "https://www.flipkart.com";
         System.setProperty("webdriver.chrome.driver", "/var/lib/jenkins/testing/chromedriver");
        System.setProperty("webdriver.chrome.driver", "/var/lib/jenkins/testing/chromedriver");
         driver = new ChromeDriver();
         driver.manage().window().maximize();
         driver.get(baseURL);        
     }

     @AfterClass
     public void afterTest() {
         driver.quit();
     }
     @Test
    public void clickreferences() {
    try
    {
    Thread.sleep(1000); 
    
    
        driver.get(baseURL);
        driver.findElement(By.id("topnavbtn_references")).click();
    
    }
    catch(Exception e)
    {
    
    }
    }
	 }
