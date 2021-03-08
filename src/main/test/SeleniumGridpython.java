 package com.objectrepository.demo;
 import org.openqa.selenium.*;
 import org.openqa.selenium.remote.DesiredCapabilities;
 import java.net.MalformedURLException;
 import java.net.URL;
 import org.openqa.selenium.remote.RemoteWebDriver;
 import org.testng.Assert;
 import org.testng.annotations.*;
 import java.util.concurrent.TimeUnit;
 
 public class SeleniumGridpython {

     WebDriver driver;
     String baseURL, nodeURL;

     @BeforeClass
     public void setUp() throws MalformedURLException {
         baseURL = "https://www.w3schools.com/java/default.asp";
         nodeURL = "http://zalenium.hema.svc.cluster.local:4444/wd/hub";
         DesiredCapabilities capability = DesiredCapabilities.chrome();
         capability.setPlatform(Platform.LINUX);
         driver = new RemoteWebDriver(new URL(nodeURL), capability);
         driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);         
     }

     @AfterClass
     public void afterTest() {
         driver.quit();
     }
     @Test
    public void pythonreferences() {
    try
    {
    Thread.sleep(3000); 
    
        driver.get(baseURL);
        driver.findElement(By.className("w3-bar-item w3-button")).click();
     }
    
    catch(Exception e)
    {
        
    }
    }
	}