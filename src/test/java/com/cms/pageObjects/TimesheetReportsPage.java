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

public class TimesheetReportsPage extends BaseTest{

	public static WebDriver driver2;
	public static JavascriptExecutor js ;
	 private static String generatedDate = null;

	@FindBy(xpath="//*[name()='path' and contains(@d,'M215.103 0')]")
	private WebElement GoogleLogin ;
	
	@FindBy(xpath="//*[@id=\"identifierId\"]")
	private WebElement Google_Id ;
	
	@FindBy(xpath="//*[contains(text(),\"Next\")]")
	private WebElement Next_btn ; 
	
	@FindBy(xpath="//span[normalize-space()='Reports']")
	private WebElement TimesheetReports ; 
	
	@FindBy(xpath="//input[@id='report-by-week']")
	private WebElement Reportsbyweek ; 
	
	@FindBy(xpath="//input[@id='report-by-month']")
	private WebElement ReportsbyMonth ; 
	
	@FindBy(xpath="//select[@name='week_year']")
	private WebElement Selectweek ;
	
	// *********Construction Declaration to initialize Data Member********	
	public TimesheetReportsPage(WebDriver driverR)
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

	public void ClickonTimesheetReports() throws InterruptedException
	{
		JavascriptExecutor js = (JavascriptExecutor)driverR;		
		Utility.ExplicitWait(TimesheetReports);
		Utility.showCallout2("it will click on TimesheetReports", TimesheetReports);
		Utility.safeClick(driver2, js, TimesheetReports);
	    Thread.sleep(2000);
	}
	
	public void SelectReportByWeek() throws InterruptedException
	{
		JavascriptExecutor js = (JavascriptExecutor)driverR;		
		Utility.ExplicitWait(Reportsbyweek);
		Utility.showCallout2("it will click on Reportsbyweek", Reportsbyweek);
		Utility.safeClick(driver2, js, Reportsbyweek);
	    Thread.sleep(2000);
	} 
	
	public void SelectReportByMonth() throws InterruptedException
	{
		JavascriptExecutor js = (JavascriptExecutor)driverR;		
		Utility.ExplicitWait(ReportsbyMonth);
		Utility.showCallout2("it will click on ReportsbyMonth", ReportsbyMonth);
		Utility.safeClick(driver2, js, ReportsbyMonth);
	    Thread.sleep(2000);
	} 
	
	public void SelectReport() throws InterruptedException
	{
		JavascriptExecutor js = (JavascriptExecutor)driverR;		
		Utility.ExplicitWait(Selectweek);
		Utility.showCallout2("it will click on Selectweek", Selectweek);
		Select s1 = new Select(Selectweek);
		s1.selectByIndex(1);
		
		
	} 
	
}







