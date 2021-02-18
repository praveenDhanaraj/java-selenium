from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.keys import Keys
import time

CHROMEDRIVER_PATH = '/usr/bin/chromedriver'
WINDOW_SIZE = "1920,1080"
chrome_options = Options()
chrome_options.add_argument("--headless")
chrome_options.add_argument("--window-size=%s" % WINDOW_SIZE)
chrome_options.add_argument('--no-sandbox')
driver = webdriver.Chrome(executable_path=CHROMEDRIVER_PATH,
                          chrome_options=chrome_options
                          )
## Below is the example to check selenium grid GUI and click configuration
driver.get("http://192.168.8.20:4444/grid/console")
print(driver.title)
driver.find_element_by_xpath("//*[@id='left-column']/div/div[1]/ul/li[2]/a").click();

## Below is the example to check Google and Gmail website url
driver.get("https://www.google.com/")
print(driver.title)
driver.get("http://gmail.com")
print(driver.title)

## Below is a small example to validate the Title of a particular website and to automate a simple search on a website.
driver.get("https://github.com")
print(driver.title)
assert "GitHub" in driver.title
elem = driver.find_element_by_name("q")
elem.send_keys("testname")
elem.send_keys(Keys.RETURN)
assert "No results found." not in driver.page_source

## Below is the example to login facebook with username and password
time.sleep(15)
driver.get ("https://www.facebook.com")
driver.find_element_by_id("email").send_keys('fakeemail@crossbrowsertesting.com')
driver.find_element_by_id("pass").send_keys("fakepassword1")
time.sleep(5)
driver.find_element_by_name("login").click()

## Below is the example to check facebook logo
time.sleep(15)
driver.get("https://www.flipkart.com/")
check_facebook_logo = driver.find_element_by_xpath("//*[@id='container']/div/div[1]/div[1]/div[2]/div[1]/div/a[1]/img")

## Go to python website and type 'getting started with python' in the search bar and print the current url
time.sleep(15)
driver.get("https://www.python.org")
print(driver.title)
search_bar = driver.find_element_by_name("q")
search_bar.clear()
search_bar.send_keys("getting started with python")
search_bar.send_keys(Keys.RETURN)
print(driver.current_url)

## Go to click readio button and check box in guru99 website
time.sleep(15)
driver.get("http://demo.guru99.com/test/radio.html")
print(driver.title)
driver.find_element_by_id("vfb-7-1").click()
driver.find_element_by_id("vfb-6-0").click()
driver.close()
