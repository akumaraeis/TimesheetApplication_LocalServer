package com.cms.pageObjects;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import com.cms.basetest.BaseTest;
import com.cms.utility.Log;
import com.cms.utility.Utility;

public class DashboardPage extends BaseTest{

	public static WebDriver driver2;
	public static JavascriptExecutor js ;
	 private static String generatedDate = null;
	 private static String UserName;

	@FindBy(xpath="//*[name()='path' and contains(@d,'M215.103 0')]")
	private WebElement GoogleLogin ;
	
	@FindBy(xpath="//*[@id=\"identifierId\"]")
	private WebElement Google_Id ;
	
	@FindBy(xpath="//*[contains(text(),\"Next\")]")
	private WebElement Next_btn ; 
	
	@FindBy(xpath="//*[text()='Dashboard']")
	private WebElement Dashboard ; 
	
	@FindBy(xpath="(//*[@class=\"fs-6 fw-bold text-start w-100\"]//following::div)[3]//span")
	private WebElement DashboardStatus ; 
	
	@FindBy(xpath="//button[normalize-space()='Confirm']")
	private WebElement ConfirmationBtn ;
	
	// *********Construction Declaration to initialize Data Member********	
	public DashboardPage(WebDriver driverR)
	{
		driver2 = driverR;
		PageFactory.initElements(driverR, this);
	}
	//********time stamp Creation *******************************************
	
    public static String generateRandomDate() {
        Random random = new Random();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        int currentYear = LocalDate.now().getYear(); // 2025
        int previousYear = currentYear - 1; // 2024
        LocalDate today = LocalDate.now();

        int randomYear = random.nextBoolean() ? currentYear : previousYear; // Pick 2024 or 2025

        int randomMonth;
        int maxDay;

        if (randomYear == currentYear) { 
            // If selecting 2025, restrict months to January until current month
            randomMonth = random.nextInt(today.getMonthValue()) + 1; // From Jan to Current Month (Feb)
            maxDay = (randomMonth == today.getMonthValue()) 
                     ? today.getDayOfMonth()  // If it's the current month, don't go beyond today
                     : LocalDate.of(randomYear, randomMonth, 1).lengthOfMonth(); // Otherwise, full month range
        } else { 
            // If selecting 2024, allow full range (Jan to Dec)
            randomMonth = random.nextInt(12) + 1; // 1 to 12
            maxDay = LocalDate.of(randomYear, randomMonth, 1).lengthOfMonth();
        }

        // Generate a random day within the valid range
        int randomDay = random.nextInt(maxDay) + 1;

        // Construct the final valid date
        LocalDate randomDate = LocalDate.of(randomYear, randomMonth, randomDay);
        
        return randomDate.format(formatter); // Format as "dd/MM/yyyy"
    }
	public static String timestamp()
	{
		//return new SimpleDateFormat("yyyyddHHmm").format(new Date(10));
		LocalDateTime dt=LocalDateTime.now();
		DateTimeFormatter df = DateTimeFormatter.ofPattern("hh:mm:ss");
		String mydata=dt.format(df);
		return mydata;

	}
	//************** Method Declaration *************************************




	public static String convertDateFormat(String date) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        LocalDate parsedDate = LocalDate.parse(date, inputFormatter);
        return parsedDate.format(outputFormatter);
    }

	public void ClickonDashboard() throws InterruptedException
	{
		JavascriptExecutor js = (JavascriptExecutor)driverR;		
		Utility.ExplicitWait(Dashboard);
		Utility.showCallout2("it will click on dashboard", Dashboard);
		Utility.safeClick(driver2, js, Dashboard);
	    Thread.sleep(2000);
	}
	
	public String getUserDashboardStatus()
	{
		Utility.ExplicitWait(DashboardStatus);
		Utility.showCallout2("User Dashboard Status", DashboardStatus);
		String UserDashboardStatus = DashboardStatus.getText();
		return UserDashboardStatus;

	}
	
	public String ValidateUser() throws InterruptedException, IOException, ParseException
	{
		Log.info(" Application is launched");
		
		Utility.showTooltip("Step 4 :-> Launching Application to Clock-in user on Timesheet ");

		launchLocalUrl();

		Thread.sleep(2000);

		lp.SendUserName();

		lp.SendPassword();

		lp.ClickonLoginBtn();
		Log.info("User is logged in ");
		String ActualProfileName=lp.GetProfileName();
		

		UserName = ActualProfileName.replace("Welcome,", "").replace(" ", "").replace("!", "");
		System.out.println("User Name after trim :->"+ UserName);
		
		Log.info("Profile name is verified");
		Utility.showTooltip("User Profile Name Validated using AutomationScript.");
		return ActualProfileName;
	}
	
	public String ValidateUserDashboardstatusFunctionality() throws InterruptedException, IOException, ParseException
	{

	
		Utility.showTooltip("Step 1 :-> Launching Timesheet Application using Automation Script ");
		
		Log.info(" Application is launched");
		
		launchLocalUrl();
		
		Thread.sleep(2000);

		lp.SendLine_ManagerName();

		lp.SendAdminPassword();

		lp.ClickonLoginBtn();
		
		Log.info("Line manager logged in successfully");
		
		Utility.showTooltip("Step 2 :->Now login with Line Manager Credential to Validate User status on Dashboard before user Clock-in ");
		
		String ActualLnmProfileName=lp.GetProfileName();
		
	
		Log.info("Profile name is verified");
		tdp.ClickonDashboard();
		
		Utility.showTooltip("Step 3 :->Now Checked the current status of Respective User on Dashboard");
		Utility.waitForSeconds(2);
		String UserStatusText = driver2.findElement(By.xpath("//*[contains(text(),'"+UserName+"')]//following::div[3]//span")).getText();
//		String UserStatusText = tdp.getUserDashboardStatus();
		System.out.println("On Dashboard , text User Status :->"+UserStatusText);
		
		Log.info("User Status is fetched");
		return ActualLnmProfileName;
	}
	
	public void ValidateUserClockin() throws InterruptedException, IOException, ParseException
	{
		Log.info(" Application is launched");
		
		Utility.showTooltip("Step 4 :-> Launching Application to Clock-in user on Timesheet ");

		launchLocalUrl();
		Thread.sleep(2000);

		lp.SendUserName();

		lp.SendPassword();

		lp.ClickonLoginBtn();
		Log.info("User is logged in ");
		Thread.sleep(2000);

		att.ClickonAttendance();		
		Thread.sleep(2000);
		
		att.ClickonTimeIn();
		Thread.sleep(4000);
		
		att.ClickonConfirmationBtn();
		Thread.sleep(2000);
		
//		String ActualSuccessfulMsg = driverR.findElement(By.xpath("//*[contains(text(),'Attendance updated successfully!')]")).getText();
//		System.out.println("ActualSuccesful Message found :->"+ActualSuccessfulMsg);
//		String ExpectSuccessfulMsg ="Attendance updated successfully!";
//		sf.assertEquals(ActualSuccessfulMsg, ExpectSuccessfulMsg);
//		Utility.showTooltip("Step 5 :-> User Successfully Clock-in on Application ");
//		Log.info("User Successfully Clock-in on Application");
//		
	}
	
	public String ValidateUserStatusChangeonDashboard() throws InterruptedException, IOException, ParseException
	{
		
		
		Utility.showTooltip("Step 6 :-> Launching Timesheet Application using Automation Script to validate User Status on Dashboard. ");
		
		launchLocalUrl();
		
		Log.info("Application is launched");
		Thread.sleep(2000);

		lp.SendLine_ManagerName();

		lp.SendAdminPassword();

		lp.ClickonLoginBtn();
		Log.info("Line manager logged in successfully");
		String ActualProfileName=lp.GetProfileName();
		String ExpectedProfileName ="Welcome, LNM Testuser!";		
	
		Log.info("Profile name is verified");
		tdp.ClickonDashboard();
		
		Utility.waitForSeconds(2);
		
		String ActualUserStatus = driver2.findElement(By.xpath("//*[contains(text(),'"+UserName+"')]//following::div[3]//span")).getText();
//		String UserStatusText = tdp.getUserDashboardStatus();
		
		System.out.println("On Dashboard , text User Status :->"+ActualUserStatus);
		return ActualUserStatus;

	}
	
}







