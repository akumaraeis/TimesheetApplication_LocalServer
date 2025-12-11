package com.cms.utility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.core.util.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cms.basetest.BaseTest;

public class Utility extends BaseTest {
	
	
	 public static String captureScreenshot(String testName, String status) {
	        TakesScreenshot ts = (TakesScreenshot) driverR;
	        File source = ts.getScreenshotAs(OutputType.FILE);
	        
	        // Dynamic path for screenshot storage
	        String baseDir = System.getProperty("user.dir") + "\\AEIS-CMS-Screenshots\\";
	        File dir = new File(baseDir);
	        if (!dir.exists()) {
	            dir.mkdirs(); // Ensure the directory exists
	        }
	        
	        String destination = baseDir + "ContactCenter_" + testName + "_" + status + ".jpg";
	        File finalDestination = new File(destination);
	        
	        try {
	            // Convert and save as JPG if necessary
	            BufferedImage img = ImageIO.read(source);
	            ImageIO.write(img, "jpg", finalDestination);
	        } catch (IOException e) {
	            e.printStackTrace(); // Log exception or handle it as needed
	        }
	        
	        return destination; // Return path for reporting
	    }
	

	
//	  public static String captureScreenshot(String methodName, String status) throws IOException {
//	        TakesScreenshot ts = (TakesScreenshot) Base.driverR; // or pass WebDriver as parameter
//	        File src = ts.getScreenshotAs(OutputType.FILE);
//
//	        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
//	        String screenshotDir = System.getProperty("user.dir") + "/Screenshots/" + status + "/";
//	        new File(screenshotDir).mkdirs(); // Create directory if it doesn't exist
//
//	        String filePath = screenshotDir + methodName + "_" + timestamp + ".png";
//	        File dest = new File(filePath);
//	        FileUtils.copyFile(src, dest);
//	        return filePath;
//	    }
//	
    public static String getToken() {
       String token ="Bearer "+"Z4uzBKvHpTJlh5QCf9SDzQyHJoKQJpb6";
//         String token ="Bearer "+"07DphO7pAhaC7jcUkQBsiVaK4aZv8h3f";
		return token ;
    }
    
    
    // Method to wait for visibility of WebElement
    public static WebElement ExplicitWait(WebElement element) {
    	WebDriverWait wait = new WebDriverWait(driverR,Duration.ofSeconds(300));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
    
    public static WebElement ExplicitClickWait(WebElement element) {
    	WebDriverWait wait = new WebDriverWait(driverR,Duration.ofSeconds(300));
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    
    public static void switchToTab(int index) {
        ArrayList<String> tabs = new ArrayList<>(driverR.getWindowHandles());
        driverR.switchTo().window(tabs.get(index));
    }

	 public static void showTooltip(String message) {
		 String script = "var tooltip = document.createElement('div');" +
				    "tooltip.innerHTML = '" + message + "';" +
				    "tooltip.style.position = 'fixed';" +
				    "tooltip.style.top = '100px';" +
				    "tooltip.style.left = '50%';" +
				    "tooltip.style.padding = '20px 40px';" +
				    "tooltip.style.backgroundColor = '#FFD700';" + // 🔹 Bright golden yellow
				    "tooltip.style.color = '#1a1a1a';" +           // 🔹 Dark text for contrast
				    "tooltip.style.fontSize = '24px';" +           // Larger text
				    "tooltip.style.fontWeight = 'bold';" +
				    "tooltip.style.borderRadius = '12px';" +
				    "tooltip.style.boxShadow = '0px 6px 15px rgba(0,0,0,0.3)';" +
				    "tooltip.style.zIndex = '10000';" +
				    "tooltip.style.transform = 'translateX(-50%)';" +
				    "tooltip.style.fontFamily = 'Arial, sans-serif';" +
				    "document.body.appendChild(tooltip);" +
				    "setTimeout(function() { document.body.removeChild(tooltip); }, 2000);";
	        ((JavascriptExecutor) driverR).executeScript(script);
	    }
	 
	 public static void showTooltipWithElementPoint(String message ,WebElement element) {
		 ((JavascriptExecutor) driverR).executeScript(
				    "var rect = arguments[0].getBoundingClientRect();" +  // get element position
				    "var tooltip = document.createElement('div');" +
				    "tooltip.innerHTML = '" + message + "';" +
				    "tooltip.style.position = 'absolute';" +
				    "tooltip.style.top = (rect.top - 60 + window.scrollY) + 'px';" +  // above element
				    "tooltip.style.left = (rect.left + rect.width/2) + 'px';" +       // center align
				    "tooltip.style.transform = 'translateX(-50%)';" +
				    "tooltip.style.padding = '12px 20px';" +
				    "tooltip.style.backgroundColor = '#FFD700';" +  // golden yellow
				    "tooltip.style.color = '#1a1a1a';" +
				    "tooltip.style.fontSize = '18px';" +
				    "tooltip.style.fontWeight = 'bold';" +
				    "tooltip.style.borderRadius = '8px';" +
				    "tooltip.style.boxShadow = '0px 6px 15px rgba(0,0,0,0.3)';" +
				    "tooltip.style.zIndex = '10000';" +
				    "tooltip.style.fontFamily = 'Arial, sans-serif';" +
				    "tooltip.style.textAlign = 'center';" +

				    // Create arrow (triangle)
				    "var arrow = document.createElement('div');" +
				    "arrow.style.position = 'absolute';" +
				    "arrow.style.top = (rect.top - 20 + window.scrollY) + 'px';" +
				    "arrow.style.left = (rect.left + rect.width/2 - 10) + 'px';" +
				    "arrow.style.width = 0;" +
				    "arrow.style.height = 0;" +
				    "arrow.style.borderLeft = '10px solid transparent';" +
				    "arrow.style.borderRight = '10px solid transparent';" +
				    "arrow.style.borderTop = '10px solid #FFD700';" +  // matches tooltip bg
				    "arrow.style.zIndex = '10000';" +

				    "document.body.appendChild(tooltip);" +
				    "document.body.appendChild(arrow);" +
				    "setTimeout(function() { " +
				    "   document.body.removeChild(tooltip);" +
				    "   document.body.removeChild(arrow);" +
				    "}, 10000);",
				    element);
	    }
	 public static void showCallout2(String message, WebElement element) {
		    String script =
		        "var rect = arguments[0].getBoundingClientRect();" +
		        "var callout = document.createElement('div');" +
		        "callout.innerHTML = '" + message + "';" +
		        "callout.style.position = 'absolute';" +
		        "callout.style.left = (rect.left + rect.width/2 + window.scrollX) + 'px';" +
		        "callout.style.top = (rect.bottom + 12 + window.scrollY) + 'px';" +  // ✅ below element
		        "callout.style.transform = 'translateX(-50%)';" +
		        "callout.style.padding = '12px 20px';" +
		        "callout.style.backgroundColor = 'green';" +
		        "callout.style.color = 'white';" +
		        "callout.style.fontSize = '16px';" +
		        "callout.style.fontWeight = 'bold';" +
		        "callout.style.borderRadius = '8px';" +
		        "callout.style.boxShadow = '0 4px 12px rgba(0,0,0,0.3)';" +
		        "callout.style.zIndex = '10000';" +
		        "callout.style.fontFamily = 'Arial, sans-serif';" +
		        "callout.style.textAlign = 'center';" +

		        // ✅ Create arrow pointing UP
		        "var arrow = document.createElement('div');" +
		        "arrow.style.position = 'absolute';" +
		        "arrow.style.top = '-12px';" + // place arrow on top of callout
		        "arrow.style.left = '50%';" +
		        "arrow.style.transform = 'translateX(-50%)';" +
		        "arrow.style.width = '0';" +
		        "arrow.style.height = '0';" +
		        "arrow.style.borderLeft = '10px solid transparent';" +
		        "arrow.style.borderRight = '10px solid transparent';" +
		        "arrow.style.borderBottom = '12px solid green';" +  // ✅ arrow points UP to element
		        "callout.appendChild(arrow);" +

		        "document.body.appendChild(callout);" +
		        "setTimeout(function() { document.body.removeChild(callout); }, 1000);"; // 5 sec

		    ((JavascriptExecutor) driverR).executeScript(script, element);
		}
	 public static void showCallout(String message , WebElement element) {
		 int x = element.getLocation().getX();
		 int y = element.getLocation().getY();

		 // Adjust position for the callout (above the element)
		 int calloutX = x - 20;
		 int calloutY = y - 60;

		 String script2 =  
		     "var callout = document.createElement('div');" +
		     "callout.innerHTML = '" + message + "';" +
		     "callout.style.position = 'absolute';" +
		     "callout.style.left = '" + calloutX + "px';" +
		     "callout.style.top = '" + calloutY + "px';" +
		     "callout.style.padding = '12px 20px';" +
		     "callout.style.backgroundColor = 'green';" + 
		     "callout.style.color = 'white';" + 
		     "callout.style.fontSize = '16px';" +
		     "callout.style.fontWeight = 'bold';" +
		     "callout.style.borderRadius = '8px';" +
		     "callout.style.boxShadow = '0 4px 12px rgba(0,0,0,0.3)';" +
		     "callout.style.zIndex = '10000';" +
		     "callout.style.fontFamily = 'Arial, sans-serif';" +
		     "callout.style.textAlign = 'center';" +

		     // Create arrow (tail)
		     "var arrow = document.createElement('div');" +
		     "arrow.style.position = 'absolute';" +
		     "arrow.style.top = '100%';" +     // places below the callout
		     "arrow.style.left = '30px';" +   // adjust for arrow position
		     "arrow.style.width = '0';" +
		     "arrow.style.height = '0';" +
		     "arrow.style.borderLeft = '12px solid transparent';" +
		     "arrow.style.borderRight = '12px solid transparent';" +
		     "arrow.style.borderTop = '20px solid green';" + // same as callout background
		     "callout.appendChild(arrow);" +

		     "document.body.appendChild(callout);" +
		     "setTimeout(function() { document.body.removeChild(callout); }, 1000);"; // 5 sec

		 ((JavascriptExecutor) driverR).executeScript(script2);
	    }
	
	 public static void safeClick(WebDriver driver, JavascriptExecutor js, WebElement element) {
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

		    try {
		        // ✅ Wait until visible & clickable
		        element = wait.until(ExpectedConditions.elementToBeClickable(element));

		        // ✅ Scroll into view (center)
		        js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", element);

		        // ✅ Try normal Selenium click first
		        element.click();
		        System.out.println("✅ safeClick: Element clicked successfully");
		    } catch (Exception e1) {
		        System.out.println("⚠️ safeClick failed with normal click: " + e1.getMessage());
		        try {
		            // ✅ Fallback: JS Click
		            js.executeScript("arguments[0].click();", element);
		            System.out.println("✅ safeClick: Element clicked using JS executor");
		        } catch (Exception e2) {
		            System.out.println("❌ safeClick failed completely: " + e2.getMessage());
		            throw e2; // rethrow if nothing worked
		        }
		    }
		}

public static WebElement ExpectTimesheetSuccesful(WebDriver driver, By locator, int timeoutSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
    return wait.until(ExpectedConditions.elementToBeClickable(locator));
}

public static WebElement waitForElementToBeClickable(WebDriver driver, By locator, int timeoutSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
    return wait.until(ExpectedConditions.elementToBeClickable(locator));
}

public static void scrollIntoView(WebDriver driver, JavascriptExecutor js, WebElement element) {
    js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", element);
}

public static void waitForSeconds(double seconds) {
    try {
        Thread.sleep((long) (seconds * 1000));
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}

public static void highlightElement(WebElement Element)
{
	JavascriptExecutor js = (JavascriptExecutor)driverR;
	js.executeScript("arguments[0].setAttribute('style','background:yellow;border:4px solid green;')",Element );

}

}


