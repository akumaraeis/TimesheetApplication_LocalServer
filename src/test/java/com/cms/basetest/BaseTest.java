package com.cms.basetest;



import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.cms.listener.Listener;
import com.cms.pageObjects.AddTaskPage;
import com.cms.pageObjects.AddTimesheetPage;
import com.cms.pageObjects.AddTimesheetPage2;
import com.cms.pageObjects.ApplyLeavePage;

import com.cms.pageObjects.ChangePasswordPage;

import com.cms.pageObjects.DashboardPage;
import com.cms.pageObjects.Gross_TaskHourValidationPage;
import com.cms.pageObjects.InValid_TimesheetEntryValidationPage;
import com.cms.pageObjects.LoginPage;
import com.cms.pageObjects.MultipleClockInScenarioPage;
import com.cms.pageObjects.PreviousEntryClockOutScenarioPage;
import com.cms.pageObjects.RegularizationPage;
import com.cms.pageObjects.TimeOverlappingScenarioPage;
import com.cms.pageObjects.TimesheetReportsPage;
import com.cms.pageObjects.TimesheetSlotMinimumHourValidationPage;
import com.cms.pageObjects.TimesheetSubmissionPage;
import com.cms.pageObjects.TwentyFourhourValidationPage;
import com.cms.pageObjects.UsersPage;
import com.cms.utility.Utility;

import io.github.bonigarcia.wdm.WebDriverManager;



public class BaseTest{	
	// public ReadConfig rc;
	public static RemoteWebDriver driverR;
	//	public static WebDriver driverR;
	//    public String baseurl = rc.getApplicationURL();
	public static  WebDriver driverL;
	public static LoginPage lp;
	public static AddTimesheetPage atp;
	public static AddTimesheetPage2 atp2;
	public static AddTaskPage att;
	public static Gross_TaskHourValidationPage gtp;
	public static TimeOverlappingScenarioPage tol;
	public static MultipleClockInScenarioPage mcp;
	public static TwentyFourhourValidationPage tvp;
	public static InValid_TimesheetEntryValidationPage itp;
	public static TimesheetSlotMinimumHourValidationPage tmp;
	public static PreviousEntryClockOutScenarioPage pcp;
	public static TimesheetSubmissionPage tsp;
	public static ApplyLeavePage alp;
	public static DashboardPage tdp;
	public static UsersPage tup;
	public static ChangePasswordPage tcp;
	public static RegularizationPage trp;
	public static TimesheetReportsPage tsrp;

	
	  private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	    public static WebDriver getDriver() {
	        return driver.get();
	    }

	//****To Run Code in Virtual Cloud Machine.
	public void initBrowser(String Browsername ) throws IOException
	{
//		WebDriver driverR = null;
		switch(Browsername.toLowerCase())
		{
		case "firefox" :

			FirefoxOptions firefoxOptions = new FirefoxOptions();
//			firefoxOptions.addArguments("start-maximized");
			firefoxOptions.addArguments("--incognito");
			firefoxOptions.addArguments("--disable-notifications");
			firefoxOptions.addArguments("--disable-blink-features=AutomationControlled");
			firefoxOptions.addArguments("--disable-extensions");

			driverR = new FirefoxDriver(firefoxOptions);
			break;
		case "chrome" :
			ChromeOptions chromeOptions = new ChromeOptions();
//			chromeOptions.addArguments("--start-maximized");
			chromeOptions.addArguments("--incognito");
//			chromeOptions.addArguments("--headless");
//			chromeOptions.addArguments("--disable-notifications");
//			chromeOptions.addArguments("--disable-blink-features=AutomationControlled");

			chromeOptions.addArguments("--disable-extensions");
			         driverR = new ChromeDriver(chromeOptions);
			break;
		case "edge" :
			 System.setProperty("webdriver.edge.driver", "C:\\Users\\ATLAS-ADMIN\\Downloads\\edgedriver_win64\\msedgedriver.exe");
			EdgeOptions edgeOptions = new EdgeOptions();
//			edgeOptions.addArguments("start-maximized");
		    edgeOptions.addArguments("--incognito");
			driverR = new EdgeDriver(edgeOptions);
		default:
			System.err.println("Please provide valid Machine_Name");
			break;
		}
//		driver.set(driverR);
		driverR.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(300)); 
		driverR.manage().deleteAllCookies();

		

	}



	public void launchUrl() throws IOException, InterruptedException
	{
		driverR.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));		
		//	driverR.get(getFile("baseurl"));
		driverR.manage().window().maximize();
		//	driverR.get("https://contactcenter.intelledash.com/");
		driverR.get("https://timesheet.ndtatlas.com/login");
//		driverR.get("https://timesheet.ndtatlas.com/");
//		driverR.get("https://contactcenter.intelledash.com/?token=07DphO7pAhaC7jcUkQBsiVaK4aZv8h3f");
			Thread.sleep(1000);	
//		WebElement SecureSite = driverR.findElement(By.xpath("//*[contains(text(),\"Continue to site\")]"));
//		Utility.ExplicitWait(SecureSite);
//		SecureSite.click();
//		Thread.sleep(5000);

	}

	public void launchLocalUrl() throws IOException, InterruptedException
	{
//		WebDriver driverR = getDriver();
		driverR.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));	
		driverR.manage().window().maximize();
		driverR.get(getFile("baseUrl"));
		Thread.sleep(1000);	
	}

	public static String getFile(String filename) throws IOException
	{
		Properties prop = new Properties();
//		String p1 = "C:\\Users\\ATLAS-ADMIN\\eclipse-workspace\\automation-cc\\src\\test\\resources\\Config File\\config.properties" ;
		String p1 = System.getProperty("user.dir")+"/src/test/resources/Config File/config.properties" ;
		FileInputStream fis = new FileInputStream(p1);
		prop.load(fis);
		String data = prop.getProperty(filename);
		return data;
	}

	public  String getCellData(String filePath, int row, int col) {
		String cellData = null;
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			int currentRow = 0;

			while ((line = br.readLine()) != null) {
				if (currentRow == row) {
					String[] columns = line.split(",");
					if (col < columns.length) {
						cellData = columns[col];
					}
					break;
				}
				currentRow++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return cellData;
	}

	public void creatingObject() throws IOException
	{
	
		 lp = new LoginPage(driverR);
		 atp = new AddTimesheetPage(driverR);
		 att = new AddTaskPage(driverR);
		 gtp = new Gross_TaskHourValidationPage(driverR);
		 atp2 = new AddTimesheetPage2(driverR);
		 tol = new TimeOverlappingScenarioPage(driverR);
		 mcp= new MultipleClockInScenarioPage(driverR);
		 tvp= new TwentyFourhourValidationPage(driverR);
		 itp= new InValid_TimesheetEntryValidationPage(driverR);
		 tmp = new TimesheetSlotMinimumHourValidationPage(driverR);
		 pcp =new PreviousEntryClockOutScenarioPage(driverR);
		 tsp =new TimesheetSubmissionPage(driverR);
		 alp =new ApplyLeavePage(driverR);
		 tdp = new DashboardPage(driverR);
		 tup = new UsersPage(driverR);
		 tcp = new ChangePasswordPage(driverR);
		 trp = new RegularizationPage(driverR);
		 tsrp =new TimesheetReportsPage(driverR);

	}


	public void teardown()
	{
		driverR.quit();
	}

}
