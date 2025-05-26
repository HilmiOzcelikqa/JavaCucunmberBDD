package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import steps.PageInitializers;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.time.Duration;

public class CommonMethods extends PageInitializers {

    public static WebDriver driver;
    private static WebDriverWait wait;

    public void openBrowserAndLaunchApplication(){
        ConfigReader.readProperties(Constants.CONFIGURATION_FILEPATH);

        Boolean isHeadless = Boolean.parseBoolean(ConfigReader.getPropertyValue("headless"));

        switch (ConfigReader.getPropertyValue("browser").toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();

                if (isHeadless) {
                    chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--window-size=1920,1080");
                    chromeOptions.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
                    chromeOptions.setExperimentalOption("useAutomationExtension", false);
                    chromeOptions.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.5735.91 Safari/537.36");
                    chromeOptions.addArguments("--disable-gpu");
                }

                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--remote-allow-origins=*");
                chromeOptions.addArguments("--ignore-certificate-errors");
                chromeOptions.addArguments("--disable-popup-blocking");

                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();

                if (isHeadless) {
                    firefoxOptions.addArguments("-headless");
                    firefoxOptions.addArguments("--width=1920");
                    firefoxOptions.addArguments("--height=1080");
                    firefoxOptions.addPreference("general.useragent.override",
                            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.5735.91 Safari/537.36");
                    firefoxOptions.addPreference("dom.webdriver.enabled", false);
                    firefoxOptions.addPreference("useAutomationExtension", false);
                    firefoxOptions.addPreference("media.navigator.enabled", false);
                    firefoxOptions.addPreference("privacy.trackingprotection.enabled", true);
                }

                firefoxOptions.addArguments("--no-sandbox");
                firefoxOptions.addArguments("--disable-dev-shm-usage");
                firefoxOptions.addArguments("--ignore-certificate-errors");
                firefoxOptions.addArguments("--disable-popup-blocking");

                driver = new FirefoxDriver(firefoxOptions);
                break;

            default:
                throw new RuntimeException("Invalid browser name");
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
        driver.get(ConfigReader.getPropertyValue("url"));
        initializePageObjects();
    }


    public static void sendText(WebElement element, String textToSend){
        try {
            waitForVisibility(element);
            element.clear();
            element.sendKeys(textToSend);
        } catch (Exception e) {
            System.out.println("Error sending text to element: " + e.getMessage());
            throw e;
        }
    }

    public static WebDriverWait getWait(){
        if (wait == null) {
            wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        }
        return wait;
    }

    public static void waitForVisibility(WebElement element) {
        getWait().until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForClickability(WebElement element){
        try {
            getWait().until(ExpectedConditions.and(
                ExpectedConditions.visibilityOf(element),
                ExpectedConditions.elementToBeClickable(element)
            ));
        } catch (TimeoutException e) {
            System.out.println("Element not clickable: " + e.getMessage());
            throw e;
        }
    }

    public static void click(WebElement element){
        try {
            waitForClickability(element);
            scrollElementToCenter(element);
            element.click();
        } catch (ElementClickInterceptedException e) {
            System.out.println("Element click intercepted, trying JavaScript click");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            System.out.println("Error clicking element: " + e.getMessage());
            throw e;
        }
    }

    public static void switchIframe(WebElement element){
        driver.switchTo().frame(element);
    }
    public static void switchIframe(int index){
        driver.switchTo().frame(index);
    }
    public static void switchIframe(String nameOrID){
        driver.switchTo().frame(nameOrID);
    }

    public static void switchWindowHandle(){
        String mainWindowHandle = driver.getWindowHandle();
        Set<String> allWindowHandles = driver.getWindowHandles();
        for (String ChildWindow : allWindowHandles) {
            if (!mainWindowHandle.equalsIgnoreCase(ChildWindow)) {
                driver.switchTo().window(ChildWindow);
            }
        }
    }

    public static JavascriptExecutor getJSExecutor(){
        return (JavascriptExecutor) driver;
    }

    public static void jsClick(WebElement element){
        getJSExecutor().executeScript("arguments[0].click();", element);
    }

    public static byte[] takeScreenshot(String fileName){
        TakesScreenshot ts = (TakesScreenshot)driver;
        byte[] picBytes = ts.getScreenshotAs(OutputType.BYTES);
        File sourceFile = ts.getScreenshotAs(OutputType.FILE);

        try {
            FileUtils.copyFile(sourceFile, new File(Constants.SCREENSHOT_FILEPATH + fileName
                    + " " + getTimeStamp("yyyy-MM-dd-HH-mm-ss")+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return picBytes;
    }

    public static String getTimeStamp(String pattern){
        Date date = new Date();
        //to format the date according to our choice we want to implement in this function
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static void hover(WebElement element){
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }
    public static void dragAndDrop(WebElement element,WebElement target){
        Actions actions=new Actions(driver);
        actions.dragAndDrop(element,target).perform();
    }

    public static void scrollToBottom() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public static void scrollToTop() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 0)");
    }

    public static void scrollElementToCenter(WebElement element) {
        try {
            waitForVisibility(element);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", element);
            Thread.sleep(500);
        } catch (Exception e) {
            System.out.println("Error scrolling to element: " + e.getMessage());
        }
    }

    public static void scrollByViewportHeight(double ratio) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Long viewportHeight = (Long) js.executeScript("return window.innerHeight");
        js.executeScript("window.scrollBy(0, arguments[0])", viewportHeight * ratio);
    }

    public static void switchToNewWindow() {
        String mainWindow = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(mainWindow)) {
                driver.switchTo().window(handle);
                break;
            }
        }
    }

    public static List<WebElement> findElements(By by) {
        return driver.findElements(by);
    }

    public static void tearDown(){
        driver.quit();
    }

}


