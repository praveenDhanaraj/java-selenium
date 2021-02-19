import unittest
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.desired_capabilities import DesiredCapabilities
import time
import socket
driver = webdriver.Remote(command_executor='http://selenium-hub.hema.svc.cluster.local:4444/wd/hub',
              desired_capabilities=DesiredCapabilities.CHROME)
driver.get("https://www.google.co.in/")
print(driver.title)
print(socket.gethostname())
driver.quit()