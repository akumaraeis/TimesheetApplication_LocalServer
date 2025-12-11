package com.cms.pageObjects;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

public class TwentyFourhourValidationPage extends BaseTest{

	public static WebDriver driver2;
	public static JavascriptExecutor js ;

	@FindBy(xpath="//*[name()='path' and contains(@d,'M215.103 0')]")
	private WebElement GoogleLogin ;
	
	@FindBy(xpath="//*[@id=\"identifierId\"]")
	private WebElement Google_Id ;
	
	@FindBy(xpath="//*[contains(text(),\"Next\")]")
	private WebElement Next_btn ; 
	
	@FindBy(xpath="//*[@type=\"password\"]")
	private WebElement Google_Pd ;
	
//*****************************************************
//	@FindBy(xpath="//*[contains(text(),'Username')]")
//	private WebElement UserName ;
	
	@FindBy(xpath="//input[@class='form-control my-3']")
	private WebElement UserName ;
		
	@FindBy(xpath="//input[@type='password']")
	private WebElement Password ;

	@FindBy(xpath="//button[normalize-space()='Login']")
	private WebElement Login ;
	
	@FindBy(xpath="//*[contains(@class,\"text-center\")]")
	private WebElement ProfileName ;
	
	@FindBy(xpath="(//div[@class=\"mb-3 w-75 input-group\"]//input)[1]")
	private WebElement ClockinDate ;	
	
	@FindBy(xpath="(//div[@class=\"mb-3 w-75 input-group\"]//input)[2]")
	private WebElement ClockinTime ;
		
	@FindBy(xpath="(//div[@class=\"mb-3 w-75 input-group\"]//input)[3]")
	private WebElement ClockOutDate ;
	
	@FindBy(xpath="(//div[@class=\"mb-3 w-75 input-group\"]//input)[4]")
	private WebElement ClockOutTime ;

	
//	@FindBy(xpath="//input[@id='logout_date']")
//	private WebElement ClockOutTime ;
	
	@FindBy(xpath="//select[@aria-label='Select break duration']")
	private WebElement BreakDuration ;
	
	@FindBy(xpath="//option[@value='01:00:00']")
	private WebElement BreakDuration2 ;
	
	@FindBy(xpath="//a[@class='me-2 fw-bold btn btn-primary btn-sm']")
	private WebElement AddNewTimesheet ;
	
	@FindBy(xpath="//a[normalize-space()='Timesheet']")
	private WebElement Timesheet ;
	
	@FindBy(xpath="//span[normalize-space()='Submit']")
	private WebElement Submit ;
	
	@FindBy(xpath="(//*[contains(text(),'Add Task')])[1]")
	private WebElement AddTask ;
		
	@FindBy(xpath="//div[@id='core-process']")
	private WebElement CoreProcess ;
	
	@FindBy(xpath="//li[normalize-space()='Cross Functional Process']")
	private WebElement CrossFunctional ;
	
	@FindBy(xpath="//select[@aria-label='Select Sub Process']")
	private WebElement SubProcess ;
	
	@FindBy(xpath="//li[normalize-space()='Technology Management Process']")
	private WebElement Technology ;
	
	@FindBy(xpath="//div[@id='activity']")
	private WebElement Activity ;
	
	@FindBy(xpath="//li[normalize-space()='Other']")
	private WebElement Other ;
		
	@FindBy(xpath="//input[@id='other']")
	private WebElement TaskDescription ;
	
	@FindBy(xpath="//li[normalize-space()='02:00']")
	private WebElement TaskDuration2 ;
	
	@FindBy(xpath="//div[@id='duration']")
	private WebElement TaskDuration ;
	
	@FindBy(xpath="class=\"alert alert-success")
	private WebElement SuccessfulNotification ;
	
	@FindBy(xpath="((//*[@class=\"mb-3\"])[6])//button")
	private WebElement TaskSubmit ;
	
	@FindBy(xpath="//*[contains(text(),\"Task added\")]")
	private WebElement taskSuccessfulMsg ;
	
	@FindBy(xpath="//button[normalize-space()='Select Date Range']")
	private WebElement SelectDateRange ;
	
	@FindBy(xpath="//button[normalize-space()='Last Week']")
	private WebElement Lastweek ;
	
	@FindBy(xpath="(//*[contains(text(),\"Edit\")])[1]")
	private WebElement Edit ;
	
	@FindBy(xpath="	//li[normalize-space()='30 minutes']")
	private WebElement EditBreakDuration ;
	
	@FindBy(xpath="(//div[@role=\"alert\"])[2]")
	private WebElement TaskAlert ;
	
	@FindBy(xpath="//span[normalize-space()='Back']")
	private WebElement BackButton ;
	
	@FindBy(xpath="//div[@id='records-per-page']")
	private WebElement RecordsPerPage ;
	
	@FindBy(xpath="//li[normalize-space()='100']")
	private WebElement SelectRecordsPerPage ;
	// *********Construction Declaration to initialize Data Member********	
	
	
	public TwentyFourhourValidationPage(WebDriver driverR)
	{
		driver2 = driverR;
		PageFactory.initElements(driverR, this);
	}
	//********time stamp Creation *******************************************
	
    public static String generateRandomDate() {
        Random random = new Random();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
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
		DateTimeFormatter df = DateTimeFormatter.ofPattern("ddhhmm");
		String mydata=dt.format(df);
		return mydata;

	}
	//************** Method Declaration *************************************


	public void ClickonContinueSite() throws InterruptedException
	{
		WebElement SecureSite = driver2.findElement(By.xpath("//*[contains(text(),\"Continue to site\")]"));
		Utility.ExplicitWait(SecureSite);
		SecureSite.click();
	    Thread.sleep(5000);
	}
	

	public void SendUserName() throws InterruptedException
	{
		Utility.ExplicitWait(UserName);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",UserName );
		UserName.sendKeys("AutomationTestUser");
		Thread.sleep(2000);
	}
	
	public void SendPassword() throws InterruptedException
	{
		Utility.ExplicitWait(Password);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",Password );
		Password.sendKeys("Test@123");
		Thread.sleep(2000); 
	}

	public void ClickonLoginBtn() throws InterruptedException
	{
		Utility.ExplicitWait(Login);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:4px solid green;')",Login );
		Login.click();
	}
	
	public void ClickonBackBtn() throws InterruptedException
	{
		Utility.ExplicitWait(BackButton);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:4px solid green;')",BackButton );
		BackButton.click();
	}

	
	public String GetProfileName() throws InterruptedException
	{
		Utility.ExplicitWait(ProfileName);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:4px solid green;')",ProfileName );
		Thread.sleep(2000);
		
		String Pname = ProfileName.getText();
		return Pname;
		}
	
	
	public void ClickonTimesheet() throws InterruptedException
	{
		Utility.ExplicitWait(Timesheet);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:4px solid green;')",Timesheet );
		Timesheet.click();
	}

	public void ClickonAddNewTimesheet() throws InterruptedException
	{
		Utility.ExplicitWait(AddNewTimesheet);
		Thread.sleep(2000);
		JavascriptExecutor js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].scrollIntoView(true);", AddNewTimesheet);
		AddNewTimesheet.click();
	    Thread.sleep(2000);
	}
	
	public String  SelectClockinDate(String Date1) throws InterruptedException
	{
		Utility.ExplicitWait(ClockinDate);

		ClockinDate.sendKeys(Date1);

		Utility.ExplicitWait(ClockinTime);
		
		ClockinTime.sendKeys("10:30");
		return generateRandomDate();
		
	}


	public void SelectClockOutDate(String Date) throws InterruptedException
	{
		Utility.ExplicitWait(ClockOutDate);

		ClockOutDate.sendKeys(Date);
		
		Utility.ExplicitWait(ClockOutTime);
	
		ClockOutTime.sendKeys("19:30");
		
//		System.out.println(getRandomDate());
		
	    Thread.sleep(1000);
//	    return getRandomDate(02);
	}


	public void SelectBreakDuration() throws InterruptedException
	{
		Utility.ExplicitWait(BreakDuration);
		BreakDuration.click();
		Thread.sleep(2000);
		BreakDuration2.click();
	}
	
	public void ClickonSubmit() throws InterruptedException
	{
		Utility.ExplicitWait(Submit);
		Submit.click();
	    Thread.sleep(2000);
	}

	public void SendTaskDescription() throws InterruptedException
	{
		Utility.ExplicitWait(TaskDescription);
		TaskDescription.sendKeys("Test Description");
		Thread.sleep(2000);
	}
	public String GetTaskAlert() throws InterruptedException
	{
		Utility.ExplicitWait(TaskAlert);
		String ActualMsg2 = TaskAlert.getText();
		System.out.println(" Message received on Timesheet"+ActualMsg2);
		Thread.sleep(2000);
		return ActualMsg2 ;
	}

	public String adaysTodate(String days, int daysToadd)
	{
		DateTimeFormatter Formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate localDate = LocalDate.parse(days, Formatter);
		localDate = localDate.plusDays(daysToadd);		
		return localDate.format(Formatter);
	}
}






