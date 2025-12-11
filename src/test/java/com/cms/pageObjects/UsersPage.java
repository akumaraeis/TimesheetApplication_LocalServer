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

import com.cms.basetest.BaseTest;
import com.cms.utility.Utility;

public class UsersPage extends BaseTest{

	public static WebDriver driver2;
	public static JavascriptExecutor js ;
	 private static String generatedDate = null;

	@FindBy(xpath="//*[name()='path' and contains(@d,'M215.103 0')]")
	private WebElement GoogleLogin ;
	
	@FindBy(xpath="//*[@id=\"identifierId\"]")
	private WebElement Google_Id ;
	
	@FindBy(xpath="//*[contains(text(),\"Next\")]")
	private WebElement Next_btn ; 
	
	@FindBy(xpath="//a[normalize-space()='Users']")
	private WebElement Users ; 
	
	@FindBy(xpath="//*[contains(text(),'AutomationTesting')]/following-sibling::small")
	private WebElement DashboardStatus ; 
	
	@FindBy(xpath="//button[normalize-space()='Confirm']")
	private WebElement ConfirmationBtn ;
	
	@FindBy(xpath="//input[@placeholder='Search User']")
	private WebElement SearchField ; 
	
	@FindBy(xpath="//button[normalize-space()='Search']")
	private WebElement SearchBtn ; 
		
	@FindBy(xpath="//*[@class=\"fw-bold text-end\"]//following::td[1]")
	private WebElement UserNameonList ; 
	
	@FindBy(xpath="//button[normalize-space()='Actions']")
	private WebElement ActionBtn ; 
	
	@FindBy(xpath="//a[contains(text(),'Modify Client’s Scheduled Time In/Out')]")
	private WebElement ActionOption ; 
	
	@FindBy(xpath="//input[@name='clock_in_time']")
	private WebElement TimeIn ;
	
	@FindBy(xpath="//input[@name='break_in_time']")
	private WebElement BreakIn ;
	
	@FindBy(xpath="//input[@name='break_out_time']")
	private WebElement BreakOut ;
	
	@FindBy(xpath="//input[@name='clock_out_time']")
	private WebElement TimeOut ;
	
	@FindBy(xpath="//*[contains(text(),'Time in/out updated successfully!')]")
	private WebElement Alert ;
	
	// *********Construction Declaration to initialize Data Member********	
	public UsersPage(WebDriver driverR)
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

	public void ClickonUsers() throws InterruptedException
	{
		JavascriptExecutor js = (JavascriptExecutor)driverR;
		Utility.ExplicitWait(Users);
		Utility.showCallout2("it will click on Users", Users);
		Utility.safeClick(driver2, js, Users);
	    Utility.waitForSeconds(2);
	}
	
	public String getUserNameonList()
	{
		Utility.ExplicitWait(UserNameonList);
		Utility.showCallout2("User Dashboard Status", UserNameonList);
		String UserNameOnList = UserNameonList.getText();
		return UserNameOnList;
	}
	
	public void SendUserName(String UserName) throws InterruptedException
	{
		JavascriptExecutor js = (JavascriptExecutor)driverR;
		Utility.ExplicitWait(SearchField);
		Utility.showCallout2("it will click on dashboard", SearchField);
		SearchField.sendKeys(UserName);
		Utility.waitForSeconds(2);
		Utility.safeClick(driver2, js, SearchBtn);
	}
	
	public void ClickonActionOption() throws InterruptedException
	{
		JavascriptExecutor js = (JavascriptExecutor)driverR;
		Utility.ExplicitWait(ActionBtn);	
		Utility.showCallout2("it will click on ActionBtn", ActionBtn);
		Utility.safeClick(driver2, js, ActionBtn);
		Utility.waitForSeconds(2);
		Utility.safeClick(driver2, js, ActionOption);		
	    Utility.waitForSeconds(2);
	}
	
	public void SendTimeIn() throws InterruptedException
	{
		JavascriptExecutor js = (JavascriptExecutor)driverR;
		Utility.ExplicitWait(TimeIn);
		Utility.showCallout2("it will click on dashboard", TimeIn);
		TimeIn.sendKeys("10:30");
		Utility.waitForSeconds(2);
	}

	public void SendBreakIn() throws InterruptedException
	{
		JavascriptExecutor js = (JavascriptExecutor)driverR;
		Utility.ExplicitWait(BreakIn);
		Utility.showCallout2("it will click on dashboard", BreakIn);
		BreakIn.sendKeys("12:30");
		Utility.waitForSeconds(2);
	}
	
	public void SendBreakOut() throws InterruptedException
	{
		JavascriptExecutor js = (JavascriptExecutor)driverR;
		Utility.ExplicitWait(BreakOut);
		Utility.showCallout2("it will click on BreakOut", BreakOut);
		BreakOut.sendKeys("14:30");
		Utility.waitForSeconds(2);
	}
	
	public void SendTimeOut() throws InterruptedException
	{
		JavascriptExecutor js = (JavascriptExecutor)driverR;
		Utility.ExplicitWait(TimeOut);
		Utility.showCallout2("it will click on TimeOut", TimeOut);
		TimeIn.sendKeys("19:30");
		Utility.waitForSeconds(2);
	}
	
	public String getNotificationAlert()
	{
		Utility.ExplicitWait(Alert);
		Utility.showCallout2("User Dashboard Status", Alert);
		String SuccesfulMsg = Alert.getText();
		return SuccesfulMsg;
	}
	
}







