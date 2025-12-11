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
import com.cms.utility.Log;
import com.cms.utility.Utility;

public class AddTaskPage extends BaseTest{

	public static WebDriver driver2;
	public static JavascriptExecutor js ;
	 private static String generatedDate = null;

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
	
//	@FindBy(xpath="//input[@id='date']")
//	private WebElement ClockinDate ;
	
	@FindBy(xpath="//*[contains(@class,\"react-datepicker-ignore-onclickoutside\")]")
	private WebElement ClockinDate ;
	
	@FindBy(xpath="//input[@id='logout_date']")
	private WebElement ClockoutDate ;
	
	@FindBy(xpath="//input[@id='login_time']")
	private WebElement ClockinTime ; 
	
	@FindBy(xpath="//input[@id='date']")
	private WebElement ClockinDate2 ;
	
	@FindBy(xpath="//input[@id='logout_time']")
	private WebElement ClockoutTime ;
	
	@FindBy(xpath="//input[@id='logout_date']")
	private WebElement ClockoutDate2 ;
	
	@FindBy(xpath="//div[@id='break_duration']")
	private WebElement BreakDuration ;
	
	@FindBy(xpath="//li[normalize-space()='60 minutes']")
	private WebElement BreakDuration2 ;
	
	@FindBy(xpath="//a[normalize-space()='Add Timesheet']")
	private WebElement AddNewTimesheet ;
	
	@FindBy(xpath="//input[@value='Submit']")
	private WebElement Submit ;
	
	@FindBy(xpath="(//*[contains(text(),'ADD TASK')])[1]")
	private WebElement AddTask ;
		
	@FindBy(xpath="//div[@id='core-process']")
	private WebElement CoreProcess ;
	
	@FindBy(xpath="//span[normalize-space()='Cross Functional Process']")
	private WebElement CrossFunctional ;
	
	@FindBy(xpath="//select[@aria-label='Select Sub Process']")
	private WebElement SubProcess ;
	
	@FindBy(xpath="//option[@value='137']")
	private WebElement TechIntegration_Management ;
	
	@FindBy(xpath="//select[@aria-label='Select Activity']")
	private WebElement Activity ;
	
	@FindBy(xpath="//option[@value='52']")
	private WebElement Other ;
		
	@FindBy(xpath="//textarea[@placeholder='Task Description']")
	private WebElement TaskDescription ;
	
	@FindBy(xpath="//option[@value='02:00:00']")
	private WebElement TaskDuration2 ;
	
	@FindBy(xpath="//select[@aria-label='Select Duration']")
	private WebElement TaskDuration ;
	
	@FindBy(xpath="//div[@id='1']")
	private WebElement SuccessfulNotification ;
	
	@FindBy(xpath="//button[normalize-space()='Submit']")
	private WebElement TaskSubmit ;
	
	@FindBy(xpath="//div[@id='1']")
	private WebElement taskSuccessfulMsg ;
	
	@FindBy(xpath="//button[normalize-space()='Select Date Range']")
	private WebElement SelectDateRange ;
	
	@FindBy(xpath="//button[normalize-space()='Last Week']")
	private WebElement Lastweek ;
	
	@FindBy(xpath="//*[contains(text(),\"EDIT\")]")
	private WebElement Edit ;
	
	@FindBy(xpath="	//li[normalize-space()='30 minutes']")
	private WebElement EditBreakDuration ;
	
	@FindBy(xpath="//*[contains(@class,\"alert alert\")]")
	private WebElement TaskAlert ;
	
	@FindBy(xpath="//*[contains(@class,'react-datepicker-wrapper')]//input")
	private WebElement DateFilter ;
	
	@FindBy(xpath="//a[normalize-space()='Home']")
	private WebElement Home ;
	
	@FindBy(xpath="//span[normalize-space()='Search']")
	private WebElement SereachDate ;
	
	@FindBy(xpath="//*[contains(text(),'Attendance')]")
	private WebElement Attendance ;
	
	@FindBy(xpath="(//*[contains(text(),'Time In')])[2]")
	private WebElement TimeIn ;
	
	@FindBy(xpath="(//*[contains(text(),'Lunch/Break Time In')])[2]")
	private WebElement BreakIn ;
	
	@FindBy(xpath="(//*[contains(text(),'Lunch/Break Time Out')])[2]")
	private WebElement BreakOut ;
	
	@FindBy(xpath="(//*[contains(text(),'Time Out')])[4]")
	private WebElement TimeOut ;
	
	@FindBy(xpath="//button[normalize-space()='Confirm']")
	private WebElement ConfirmationBtn ;
	
	// *********Construction Declaration to initialize Data Member********	
	public AddTaskPage(WebDriver driverR)
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

	
	public String GetProfileName() throws InterruptedException
	{
		Utility.ExplicitWait(ProfileName);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:4px solid green;')",ProfileName );
		Thread.sleep(2000);
		
		String Pname = ProfileName.getText();
		return Pname;
		}

	public void ClickonAddNewTimesheet() throws InterruptedException
	{
		Utility.ExplicitWait(AddNewTimesheet);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",AddNewTimesheet );
		AddNewTimesheet.click();
	    Thread.sleep(2000);
	}

	public void SendClockinDate(String getRandomDate) throws InterruptedException
	{
		Utility.ExplicitWait(ClockinDate);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",ClockinDate );

//		ClockinDate.click();
		ClockinDate.sendKeys(getRandomDate);
		
//		System.out.println(getRandomDate());
		
	    Thread.sleep(1000);
//	    return getRandomDate(02);
	}

	public void SendClockoutDate(String getRandomDate) throws InterruptedException
	{
		Utility.ExplicitWait(ClockoutDate);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",ClockoutDate );

//		ClockoutDate.click();
		ClockoutDate.sendKeys(getRandomDate);
	    Thread.sleep(1000);
	}

	public void SelectBreakDuration() throws InterruptedException
	{
		Utility.ExplicitWait(BreakDuration);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",BreakDuration );

		BreakDuration.click();
		Thread.sleep(2000);
		BreakDuration2.click();
	}
	
	public void ClickonSubmit() throws InterruptedException
	{
		Utility.ExplicitWait(Submit);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",Submit );

		Submit.click();
	    Thread.sleep(2000);
	}

	public void ClickonAddTask() throws InterruptedException
	{
		Utility.ExplicitWait(AddTask);
		Utility.showCallout("Adding Task using AutomationScript", AddTask);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",AddTask );
		AddTask.click();
	    Thread.sleep(2000);
	}
	
	public void ClickonSearchDate() throws InterruptedException
	{
		Utility.ExplicitWait(SereachDate);
		Utility.showCallout("Searching Date using AutomationScript", SereachDate);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",SereachDate );
		SereachDate.click();
	    Thread.sleep(2000);
	}
	
	public void SelectCoreProcess() throws InterruptedException
	{
//		Utility.ExplicitWait(CoreProcess);
//		CoreProcess.click();
//	    Thread.sleep(2000);
	    Utility.ExplicitWait(CrossFunctional);
	    CrossFunctional.click();
	}


	public void SelectSubProcess() throws InterruptedException
	{
		Utility.ExplicitWait(SubProcess);
		Utility.showCallout("Selecting Sub Process using AutomationScript", SubProcess);        
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",SubProcess );
//		Utility.safeClick(driver2, js, SubProcess);
		Select S8 = new Select(SubProcess);
		S8.selectByVisibleText("ALIMS SOFTWARE DEVELOPMENT (D): QA & TESTING (Q)");
	    Thread.sleep(2000);
	    
	}

	public void ClickonActivity() throws InterruptedException
	{
		Utility.ExplicitWait(Activity);
		Utility.showCallout2("Selecting Activity using AutomationScript", Activity);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",Activity );
		Select S2 = new Select(Activity);
//		S2.selectByValue("6");
		S2.selectByVisibleText("Rocks & Related ToDos");
	    Thread.sleep(2000);
	}
	
	public void SendTaskDescription() throws InterruptedException
	{
		Utility.ExplicitWait(TaskDescription);
		Utility.showCallout2("Sending Task Description using AutomationScript", TaskDescription);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",TaskDescription );
		TaskDescription.sendKeys("Test Description");
		Thread.sleep(2000);
	}

	public void SendTaskDuration() throws InterruptedException
	{
		Utility.ExplicitWait(TaskDuration);
		Utility.showCallout2("Selecting TaskDuration using AutomationScript", TaskDuration);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",TaskDuration );
		TaskDuration.click();		
		Select S9 = new Select(TaskDuration);
		S9.selectByVisibleText("08:00");
		Thread.sleep(2000);		
	}
	
	public void SendTaskDuration2() throws InterruptedException
	{
		Utility.ExplicitWait(TaskDuration);
		Utility.showCallout2("Selecting TaskDuration using AutomationScript", TaskDuration);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",TaskDuration );
		TaskDuration.click();		
		Select S9 = new Select(TaskDuration);
		S9.selectByVisibleText("09:00");
		Thread.sleep(2000);		
	}

	public void SendTaskDuration3() throws InterruptedException
	{
		Utility.ExplicitWait(TaskDuration);
		Utility.showCallout2("Selecting TaskDuration using AutomationScript", TaskDuration);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",TaskDuration );
		TaskDuration.click();		
		Select S9 = new Select(TaskDuration);
		S9.selectByVisibleText("06:00");
		Thread.sleep(2000);		
	}
	
	public void SendTaskDuration4() throws InterruptedException
	{
		Utility.ExplicitWait(TaskDuration);
		Utility.showCallout2("Selecting TaskDuration using AutomationScript", TaskDuration);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",TaskDuration );
		TaskDuration.click();		
		Select S9 = new Select(TaskDuration);
		S9.selectByVisibleText("04:00");
		Thread.sleep(2000);		
	}
	public String GetSuccessfulNotification() throws InterruptedException
	{
		Utility.ExplicitWait(SuccessfulNotification);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",UserName );

		String ActualMsg = SuccessfulNotification.getText();
		System.out.println("Successful Message received on Timesheet"+ActualMsg);
		Thread.sleep(2000);
		return ActualMsg ;
	}
	
	public String GetTaskSuccessfulNotification() throws InterruptedException
	{
		Utility.ExplicitWait(taskSuccessfulMsg);
		Utility.showCallout2("Validation Checks on Task Successful", taskSuccessfulMsg);
//		js = (JavascriptExecutor)driver2;
//		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",taskSuccessfulMsg );
		String ActualMsg = taskSuccessfulMsg.getText();
		System.out.println("Successful Message received on Timesheet"+ActualMsg);
		Thread.sleep(2000);
		return ActualMsg ;
	}

	public void ClickonTaskSubmit() throws InterruptedException
	{
		Utility.ExplicitWait(TaskSubmit);
		Utility.showCallout2("Selecting TaskSubmit using AutomationScript", TaskSubmit);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",TaskSubmit );
		TaskSubmit.click();
	    Thread.sleep(2000);
	}
	
	
	public void getAddedTaskDate() throws InterruptedException
	{
    int length = driver2.findElements(By.xpath("//*[@class=\"MuiTable-root css-1ks3uzw-MuiTable-root\"]/tbody//tr//td")).size();
   for(int i = 2; i <= length; i+=6) {  
	    
	   String Matchingdate = driver2.findElement(By.xpath("(//*[@class='MuiTable-root css-1ks3uzw-MuiTable-root']/tbody//tr//td)[" + i + "]")).getText();
	    
	    // Remove time part from Matchingdate
	    String dateOnly = Matchingdate.split(",")[0].trim();  // Extract only date part

	    // Convert both dates to the same format (MM/dd/yyyy)
	    SimpleDateFormat inputFormat1 = new SimpleDateFormat("MM/dd/yyyy"); // Format of Matchingdate
	    SimpleDateFormat inputFormat2 = new SimpleDateFormat("dd-MM-yyyy"); // Format of generatedDate
	    SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd"); // Common format for comparison

	    try {
	        Date date1 = inputFormat1.parse(dateOnly);   // Parse extracted date (MM/dd/yyyy)
	        Date date2 = inputFormat2.parse(generatedDate); // Parse generatedDate (dd-MM-yyyy)

	        String formattedDate1 = outputFormat.format(date1); // Convert to yyyy-MM-dd
	        String formattedDate2 = outputFormat.format(date2); // Convert to yyyy-MM-dd

	        if (formattedDate1.equals(formattedDate2)) {
	        	// Compare formatted dates
	        	
	           driver2.findElement(By.xpath("xpath=\"(//*[contains(text(),'Add Task')])["+i+"]")).click();
//	        	Utility.ExplicitWait(AddTask);
//	            AddTask.click();
	            Thread.sleep(2000);
	            break;
	        } else {
	            System.out.println("Added Task date not found");
	        }
	    } catch (ParseException e) {
	        e.printStackTrace(); // Handle invalid date parsing
	    }
	}
	}
	
	public void ClickonSelectDateRange() throws InterruptedException
	{
		Utility.ExplicitWait(SelectDateRange);
		SelectDateRange.click();
	    Thread.sleep(2000);
	    Lastweek.click();;
	}
	
	public void ClickonAttendance() throws InterruptedException
	{
		js = (JavascriptExecutor)driver2;
//		Utility.safeClick(driver2, js, Attendance);
		Utility.ExplicitWait(Attendance);
		Utility.showCallout("Click on Attendance ", Attendance);
//		Attendance.click();
		Utility.safeClick(driver2, js, Attendance);
	}
//	ConfirmationBtn
	
	public void ClickonConfirmationBtn() throws InterruptedException
	{
		js = (JavascriptExecutor)driver2;
//		Utility.safeClick(driver2, js, Attendance);
		Utility.ExplicitWait(ConfirmationBtn);
		Utility.showCallout("Click on ConfirmationBtn ", ConfirmationBtn);
		Utility.safeClick(driver2, js, ConfirmationBtn);
//		Attendance.click();
	}
	public void ClickonTimeIn() throws InterruptedException
	{
		js = (JavascriptExecutor)driver2;
		Utility.showCallout("Click on ConfirmationBtn ", TimeIn);
		Utility.safeClick(driver2, js, TimeIn);
//		TimeIn.click();
	}
	
	public void ClickonBreakIn() throws InterruptedException
	{
		js = (JavascriptExecutor)driver2;
		Utility.safeClick(driver2, js, BreakIn);
//		BreakIn.click();
	}
	
	public void ClickonBreakOut() throws InterruptedException
	{
		js = (JavascriptExecutor)driver2;
		Utility.safeClick(driver2, js, BreakOut);
//		BreakOut.click();
	}
	public void ClickonTimeOut() throws InterruptedException
	{
		js = (JavascriptExecutor)driver2;
		Utility.safeClick(driver2, js, TimeOut);
//		TimeOut.click();
	}
	public void EditBreakDuration() throws InterruptedException
	{
	    Utility.ExplicitWait(BreakDuration);
	    BreakDuration.click();
	    Utility.ExplicitWait(EditBreakDuration);
	    EditBreakDuration.click();
	    Utility.ExplicitWait(Submit);
	    Submit.click();
	}

	public String GetTaskAlert() throws InterruptedException
	{
		Utility.ExplicitWait(TaskAlert);
		String ActualMsg2 = TaskAlert.getText();
		System.out.println(" Message received on Timesheet"+ActualMsg2);
		Thread.sleep(2000);
		return ActualMsg2 ;
	}

	public static String convertDateFormat(String date) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        LocalDate parsedDate = LocalDate.parse(date, inputFormatter);
        return parsedDate.format(outputFormatter);
    }

	
	public void SendDateFilter2(String Date) throws InterruptedException
	{
		Utility.ExplicitWait(DateFilter);		
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')", DateFilter);
		Thread.sleep(2000);
		js.executeScript("arguments[0].scrollIntoView()",DateFilter);
		Thread.sleep(2000);
		Utility.showCallout2("Selecting Date Filter to pass same date used by API", DateFilter);
		Utility.safeClick(driver2, js, DateFilter);
	//	DateFilter.click();
		Thread.sleep(2000);
		js.executeScript("arguments[0].scrollIntoView()",DateFilter );
		DateFilter.sendKeys(Keys.chord(Keys.CONTROL,"a"));
		DateFilter.sendKeys(Keys.DELETE);
		Thread.sleep(2000);
		DateFilter.sendKeys(Date);
		Thread.sleep(2000);
		Utility.safeClick(driver2, js, SereachDate);
//		SereachDate.click();
	}
	
	public void SendDateFilter(String Date) throws InterruptedException
	{
		Utility.ExplicitWait(DateFilter);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')", DateFilter);
		Thread.sleep(2000);
		js.executeScript("arguments[0].scrollIntoView()",DateFilter);
		Thread.sleep(2000);
		Utility.safeClick(driver2, js, DateFilter);
	//	DateFilter.click();
		Thread.sleep(2000);
		js.executeScript("arguments[0].scrollIntoView()",DateFilter );
		DateFilter.sendKeys(Keys.chord(Keys.CONTROL,"a"));
		DateFilter.sendKeys(Keys.DELETE);
		Thread.sleep(2000);
		DateFilter.sendKeys(Date);
		Thread.sleep(2000);
		Utility.safeClick(driver2, js, SereachDate);
//		SereachDate.click();
	}

	
	public void ClickOnEdit()
	{
		Utility.ExplicitWait(Edit);
		js =(JavascriptExecutor)js;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')", Edit);
		Edit.click();
	}

	public void ClickonHome() throws InterruptedException
	{
		Utility.ExplicitWait(Home);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",Home );
		Home.click();
	    Thread.sleep(2000);
	}

/*public static WebDriver driver2;
	public static JavascriptExecutor js ;
	 private static String generatedDate = null;

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

	@FindBy(xpath="//input[@value='2025-04-01']")
	private WebElement Date ;
	
	@FindBy(xpath="//input[@id='logout_date']")
	private WebElement ClockoutDate ;
	
	@FindBy(xpath="//input[@id='date']")
	private WebElement ClockinDate2 ;
	
	@FindBy(xpath="//input[@id='logout_time']")
	private WebElement ClockoutTime ;
	
	@FindBy(xpath="//input[@id='logout_date']")
	private WebElement ClockoutDate2 ;
	
	@FindBy(xpath="//div[@id='break_duration']")
	private WebElement BreakDuration ;
	
	@FindBy(xpath="//li[normalize-space()='60 minutes']")
	private WebElement BreakDuration2 ;
	
	@FindBy(xpath="//a[normalize-space()='Add Timesheet']")
	private WebElement AddNewTimesheet ;
	
	@FindBy(xpath="//input[@value='Submit']")
	private WebElement Submit ;
	
	@FindBy(xpath="(//*[contains(text(),'Add Task')])[1]")
	private WebElement AddTask ;
		
//	@FindBy(xpath="//div[@id='core-process']")
//	private WebElement CoreProcess ;
	
//	@FindBy(xpath="//span[normalize-space()='Cross Functional Process']")
//	private WebElement CrossFunctional ;
	
	@FindBy(xpath="//select[@aria-label='Select Sub Process']")
	private WebElement SubProcess ;
	
	@FindBy(xpath="Integrator's Processes: Quality & Compliance")
	private WebElement TechIntegration_Management ;
	
	@FindBy(xpath="//select[@aria-label='Select Activity']")
	private WebElement Activity ;
	
	@FindBy(xpath="Functional Work / Delivery Tasks / Regular ToDos")
	private WebElement ActivityOption ;
	
	@FindBy(xpath="//option[@value='6']")
	private WebElement Other ;
		
	@FindBy(xpath="//input[@placeholder='Task Description']")
	private WebElement TaskDescription ;
	
	@FindBy(xpath="//option[@value='02:00:00']")
	private WebElement TaskDuration2 ;
	
	@FindBy(xpath="//select[@aria-label='Select Duration']")
	private WebElement TaskDuration ;
	
	@FindBy(xpath="class=\"alert alert-success")
	private WebElement SuccessfulNotification ;
	
	@FindBy(xpath="//span[normalize-space()='Submit']")
	private WebElement TaskSubmit ;
	
	@FindBy(xpath="//div[@id='1']")
	private WebElement taskSuccessfulMsg ;
	
	@FindBy(xpath="//button[normalize-space()='Select Date Range']")
	private WebElement SelectDateRange ;
	
	@FindBy(xpath="//button[normalize-space()='Last Week']")
	private WebElement Lastweek ;
	
	@FindBy(xpath="//*[contains(text(),\"EDIT\")]")
	private WebElement Edit ;
	
	@FindBy(xpath="	//li[normalize-space()='30 minutes']")
	private WebElement EditBreakDuration ;
	
	@FindBy(xpath="(//div[@role=\"alert\"])[2]")
	private WebElement TaskAlert ;
	
	@FindBy(xpath="//*[contains(@class,'react-datepicker-wrapper')]//input")
	private WebElement DateFilter ;
	
	@FindBy(xpath="//a[normalize-space()='Home']")
	private WebElement Home ;
	
	@FindBy(xpath="//span[normalize-space()='Search']")
	private WebElement SereachDate ;
	// *********Construction Declaration to initialize Data Member********	
	public AddTaskPage(WebDriver driverR)
	{
		driver2 = driverR;
		PageFactory.initElements(driverR, this);
	}
	//********time stamp Creation *******************************************
	
    public static String generateRandomDate() {
        Random random = new Random();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
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

	
	public String GetProfileName() throws InterruptedException
	{
		Utility.ExplicitWait(ProfileName);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:4px solid green;')",ProfileName );
		Thread.sleep(2000);
		
		String Pname = ProfileName.getText();
		return Pname;
		}

	public void ClickonAddNewTimesheet() throws InterruptedException
	{
		Utility.ExplicitWait(AddNewTimesheet);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",AddNewTimesheet );
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
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",BreakDuration );

		BreakDuration.click();
		Thread.sleep(2000);
		BreakDuration2.click();
	}
	
	public void ClickonSubmit() throws InterruptedException
	{
		Utility.ExplicitWait(Submit);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",UserName );

		Submit.click();
	    Thread.sleep(2000);
	}

	public void ClickonAddTask() throws InterruptedException
	{
		Utility.ExplicitWait(AddTask);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",AddTask );
		AddTask.click();
	    Thread.sleep(2000);
	}
	
//	public void SelectCoreProcess() throws InterruptedException
//	{
////		Utility.ExplicitWait(CoreProcess);
////		CoreProcess.click();
////	    Thread.sleep(2000);
//	    Utility.ExplicitWait(CrossFunctional);
//	    CrossFunctional.click();
//	}


	public void SelectSubProcess() throws InterruptedException
	{
		Utility.ExplicitWait(SubProcess);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",SubProcess );
		Select S8 = new Select(SubProcess);
		S8.selectByVisibleText("Integrator's Processes: Quality & Compliance");
	    Thread.sleep(2000);
	}

	public void ClickonActivity() throws InterruptedException
	{
		Utility.ExplicitWait(Activity);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",Activity );
		Select S2 = new Select(Activity);
//		S2.selectByValue("6");
		S2.selectByVisibleText("Functional Work / Delivery Tasks / Regular ToDos");
	    Thread.sleep(2000);
	}
	
	public void SendTaskDescription() throws InterruptedException
	{
		Utility.ExplicitWait(TaskDescription);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",TaskDescription );
		TaskDescription.sendKeys("Test Description");
		Thread.sleep(2000);
	}

	public void SendTaskDuration() throws InterruptedException
	{
		Utility.ExplicitWait(TaskDuration);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",TaskDuration );
		TaskDuration.click();		
		Select S9 = new Select(TaskDuration);
		S9.selectByVisibleText("02:00");
		Thread.sleep(2000);
		
	}
	
	public String GetSuccessfulNotification() throws InterruptedException
	{
		Utility.ExplicitWait(SuccessfulNotification);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",UserName );

		String ActualMsg = SuccessfulNotification.getText();
		System.out.println("Successful Message received on Timesheet"+ActualMsg);
		Thread.sleep(2000);
		return ActualMsg ;
	}
	
	public String GetTaskSuccessfulNotification() throws InterruptedException
	{
		Utility.ExplicitWait(taskSuccessfulMsg);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",taskSuccessfulMsg );
		String ActualMsg = taskSuccessfulMsg.getText();
		System.out.println("Successful Message received on Timesheet"+ActualMsg);
		Thread.sleep(2000);
		return ActualMsg ;
	}

	public void ClickonTaskSubmit() throws InterruptedException
	{
		Utility.ExplicitWait(TaskSubmit);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",TaskSubmit );
		TaskSubmit.click();
	    Thread.sleep(2000);
	}
	
	
	public void getAddedTaskDate() throws InterruptedException
	{
    int length = driver2.findElements(By.xpath("//*[@class=\"MuiTable-root css-1ks3uzw-MuiTable-root\"]/tbody//tr//td")).size();
   for(int i = 2; i <= length; i+=6) {  
	    
	   String Matchingdate = driver2.findElement(By.xpath("(//*[@class='MuiTable-root css-1ks3uzw-MuiTable-root']/tbody//tr//td)[" + i + "]")).getText();
	    
	    // Remove time part from Matchingdate
	    String dateOnly = Matchingdate.split(",")[0].trim();  // Extract only date part

	    // Convert both dates to the same format (MM/dd/yyyy)
	    SimpleDateFormat inputFormat1 = new SimpleDateFormat("MM/dd/yyyy"); // Format of Matchingdate
	    SimpleDateFormat inputFormat2 = new SimpleDateFormat("dd-MM-yyyy"); // Format of generatedDate
	    SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd"); // Common format for comparison

	    try {
	        Date date1 = inputFormat1.parse(dateOnly);   // Parse extracted date (MM/dd/yyyy)
	        Date date2 = inputFormat2.parse(generatedDate); // Parse generatedDate (dd-MM-yyyy)

	        String formattedDate1 = outputFormat.format(date1); // Convert to yyyy-MM-dd
	        String formattedDate2 = outputFormat.format(date2); // Convert to yyyy-MM-dd

	        if (formattedDate1.equals(formattedDate2)) {
	        	// Compare formatted dates
	        	
	           driver2.findElement(By.xpath("xpath=\"(//*[contains(text(),'Add Task')])["+i+"]")).click();
//	        	Utility.ExplicitWait(AddTask);
//	            AddTask.click();
	            Thread.sleep(2000);
	            break;
	        } else {
	            System.out.println("Added Task date not found");
	        }
	    } catch (ParseException e) {
	        e.printStackTrace(); // Handle invalid date parsing
	    }
	}
	}
	
	public void ClickonSelectDateRange() throws InterruptedException
	{
		Utility.ExplicitWait(SelectDateRange);
		SelectDateRange.click();
	    Thread.sleep(2000);
	    Lastweek.click();;
	}
	
	public void EditBreakDuration() throws InterruptedException
	{
	    Utility.ExplicitWait(BreakDuration);
	    BreakDuration.click();
	    Utility.ExplicitWait(EditBreakDuration);
	    EditBreakDuration.click();
	    Utility.ExplicitWait(Submit);
	    Submit.click();
	}

	public String GetTaskAlert() throws InterruptedException
	{
		Utility.ExplicitWait(TaskAlert);
		String ActualMsg2 = TaskAlert.getText();
		System.out.println(" Message received on Timesheet"+ActualMsg2);
		Thread.sleep(2000);
		return ActualMsg2 ;
	}

	public static String convertDateFormat(String date) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        LocalDate parsedDate = LocalDate.parse(date, inputFormatter);
        return parsedDate.format(outputFormatter);
    }

	
	public void SendDateFilter(String Date) throws InterruptedException
	{
		Utility.ExplicitWait(DateFilter);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')", DateFilter);
		js.executeScript("arguments[0].scrollIntoView()",DateFilter );
		Thread.sleep(2000);
		DateFilter.click();
		DateFilter.sendKeys(Keys.chord(Keys.CONTROL,"a"));
		DateFilter.sendKeys(Keys.DELETE);
		Thread.sleep(2000);
		DateFilter.sendKeys(Date);
		Thread.sleep(2000);
		SereachDate.click();
	}
	
	public void ClickOnEdit()
	{
		Utility.ExplicitWait(Edit);
		js =(JavascriptExecutor)js;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')", Edit);
		Edit.click();
	}

	public void ClickonHome() throws InterruptedException
	{
		Utility.ExplicitWait(Home);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",Home );
		Home.click();
	    Thread.sleep(2000);
	}
	*/
}







