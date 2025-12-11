package com.cms.pageObjects;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;

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

public class ApplyLeavePage extends BaseTest{

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
	
	@FindBy(xpath="//input[@placeholder='Enter Username']")
	private WebElement UserName ;
		
	@FindBy(xpath="//input[@placeholder='Enter password']")
	private WebElement Password ;

	@FindBy(xpath="//button[@type='submit']")
	private WebElement Login ;
	
	@FindBy(xpath="//*[contains(@class,\"text-center\")]")
	private WebElement ProfileName ;
	
	@FindBy(xpath="(//div[@class=\"mb-3 w-75 input-group\"]//input)[1]")
	private WebElement ClockinDate ;	
	
	@FindBy(xpath="(//*[text() ='Time In'])[2]")
	private WebElement ClockinTime ;
		
	@FindBy(xpath="(//div[@class=\"mb-3 w-75 input-group\"]//input)[3]")
	private WebElement ClockOutDate ;
	
	@FindBy(xpath="(//*[text() ='Time Out'])[2]")
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
	
	@FindBy(xpath="//button[normalize-space()='Submit']")
	private WebElement BackButton ;
	
	@FindBy(xpath="//button[normalize-space()='Submit']")
	private WebElement SubmitButton ;
	
	@FindBy(xpath="//div[@id='records-per-page']")
	private WebElement RecordsPerPage ;
	
	@FindBy(xpath="//li[normalize-space()='100']")
	private WebElement SelectRecordsPerPage ;
	
	@FindBy(xpath="//*[contains(text(),'My Leaves')]")
	private WebElement ApplyLeave ;
	
	@FindBy(xpath="//button[normalize-space()='Apply Leave']")
	private WebElement ApplyLeaveBtn ;
	
	@FindBy(xpath="(//*[@type=\"text\"])[1]")
	private WebElement FromDate ;
	
	@FindBy(xpath="(//*[@type=\"text\"])[2]")
	private WebElement ToDate ;
	
	@FindBy(xpath="//select[@aria-label='Select Activity']")
	private WebElement LeaveType ;
	
	@FindBy(xpath="//textarea[@placeholder='Enter comment']")
	private WebElement Comment ;
	
	@FindBy(xpath="//a[contains(@class,'border-end rounded-start rounded-pill text-end mb-2 bg-gradient bg-opacity-25 nav-link active')]")
	private WebElement User_List ;
	
	
	// *********Construction Declaration to initialize Data Member********	
	
	
	public ApplyLeavePage(WebDriver driverR)
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
	
	public void ClickonUserList() throws InterruptedException
	{
//		WebElement SecureSite = driver2.findElement(By.xpath("//*[contains(text(),\"Continue to site\")]"));
		Utility.ExplicitWait(User_List);
		Utility.showCallout("Clicking on User List", User_List);
		User_List.click();
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

	public void  ClickonApplyLeave() throws InterruptedException
	{
		Utility.ExplicitWait(ApplyLeave);		
		Utility.showCallout("Selecting Apply Leave Module", ApplyLeave);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:4px solid green;')",ApplyLeave );
		ApplyLeave.click();
	}
	
	public void ClickonApplyLeaveBtn() throws InterruptedException
	{
//		Utility.ExplicitWait(ApplyLeaveBtn);
//		Utility.showCallout("Click on Apply Leave Button", ApplyLeaveBtn);
//		int index = 1;
//		boolean clicked = false;
//
//		while (true) {
//		    try {
//		        // Build dynamic XPath with current index
//		        String xpath = "(//*[contains(text(),'Apply Leave')])[" + index + "]";
//		        WebElement button = driver2.findElement(By.xpath(xpath));
//
//		        // Check if button is displayed and enabled
//		        if (button.isDisplayed() && button.isEnabled()) {
//		            button.click();
//		            System.out.println("Clicked on Apply Leave button at index: " + index);
//		            clicked = true;
//		            break;
//		        } else {
//		            System.out.println("Button at index " + index + " is disabled, checking next...");
//		            index++;
//		        }
//
//		    } catch (NoSuchElementException e) {
//		        System.out.println("No more 'Apply Leave' buttons found after index " + index);
//		        break;
//		    }
//		}
//
//		if (!clicked) {
//		    System.out.println("No enabled 'Apply Leave' button found starting from index 6.");
//		}
		
		JavascriptExecutor js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:4px solid green;')",ApplyLeaveBtn );
		ApplyLeaveBtn.click();
	}
	
	public void ClickonApplyLeaveBtn2(String FindDate) throws InterruptedException
	{
//		Utility.ExplicitWait(ApplyLeaveBtn);
//		Utility.showCallout("Finding Date ", ApplyLeaveBtn);
		int index = 8;
		boolean clicked = false;

		while (true) {
		    try {
		        // Build dynamic XPath with current index
		        String xpath = "(//*[contains(text(),'Apply Leave')])[" + index + "]";
		        WebElement button = driver2.findElement(By.xpath(xpath));

		        // Check if button is displayed and enabled
		        if (button.isDisplayed() && button.isEnabled()) {
		            button.click();
		            System.out.println("Clicked on Apply Leave button at index: " + index);
		            clicked = true;
		            break;
		        } else {
		            System.out.println("Button at index " + index + " is disabled, checking next...");
		            index++;
		        }

		    } catch (NoSuchElementException e) {
		        System.out.println("No more 'Apply Leave' buttons found after index " + index);
		        break;
		    }
		}

		if (!clicked) {
		    System.out.println("No enabled 'Apply Leave' button found starting from index 6.");
		}
	}
	
	public void ClickonSubmitBtn() throws InterruptedException
	{
		Utility.ExplicitWait(SubmitButton);
		Utility.showCallout("Clicking on Submit Button", SubmitButton);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:4px solid green;')",SubmitButton );
		SubmitButton.click();
	}
//	public void ClickonAddNewTimesheet() throws InterruptedException
//	{
//		Utility.ExplicitWait(AddNewTimesheet);
//		Thread.sleep(2000);
//		JavascriptExecutor js = (JavascriptExecutor)driver2;
//		js.executeScript("arguments[0].scrollIntoView(true);", AddNewTimesheet);
//		AddNewTimesheet.click();
//	    Thread.sleep(2000);
//	}
	
	public String  SelectClockinDate(String Date1) throws InterruptedException
	{
//		Utility.ExplicitWait(ClockinDate);
//
//		ClockinDate.sendKeys(Date1);

		Utility.ExplicitWait(ClockinTime);
		
		ClockinTime.click();
		return generateRandomDate();
		
	}


	public void SelectClockOutDate(String Date) throws InterruptedException
	{
//		Utility.ExplicitWait(ClockOutDate);
//
//		ClockOutDate.sendKeys(Date);
//		
		Utility.ExplicitWait(ClockOutTime);
	
		ClockOutTime.click();
		
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
	public void SelectLeaveType() throws InterruptedException
	{
		Utility.ExplicitWait(LeaveType);
		Utility.showCallout("Selecting Leave Type", LeaveType);
		Select s4 = new Select(LeaveType);
		List<WebElement> options = s4.getOptions();

		boolean fullDayFound = false;
		boolean halfDayFound = false;

		for (WebElement option : options) {
		    String text = option.getText().trim(); // remove extra spaces
		    if (text.equals("Full Day - Casual Leave")) {
		        s4.selectByVisibleText("Full Day - Casual Leave");
		        System.out.println("Selected: Full Day - Casual Leave");
		        fullDayFound = true;
		        break;
		    } else if (text.equals("Half Day - Casual Leave")) {
		       
		    	halfDayFound = true;
		    }
		}

		if (!fullDayFound && halfDayFound) {
		    s4.selectByVisibleText("Half Day - Casual Leave");
		    System.out.println("Selected: Half Day - Casual Leave");
		} else if (!fullDayFound && !halfDayFound) {
		    System.out.println("Neither 'Full Day - Casual Leave' nor 'Half Day - Casual Leave' found.");
		}
	}
	
	
	public void SelectLeaveType2() throws InterruptedException
	{
		Utility.ExplicitWait(LeaveType);
		Utility.showCallout("Selecting Leave Type", LeaveType);
		Select s4 = new Select(LeaveType);
		List<WebElement> options = s4.getOptions();

		boolean fullDayFound = false;
		boolean halfDayFound = false;

		for (WebElement option : options) {
		    String text = option.getText().trim(); // remove extra spaces
		    if (text.equals("Half Day - Casual Leave")) {
		        s4.selectByVisibleText("Half Day - Casual Leave");
		        System.out.println("Selected: Half Day - Casual Leave");
		        halfDayFound = true;
		        break;
		    } else if (text.equals("Full Day - Casual Leave")) {
		       
		    	fullDayFound = true;
		    }
		}

//		if (!fullDayFound && halfDayFound) {
//		    s4.selectByVisibleText("Full Day - Casual Leave");
//		    System.out.println("Selected: Half Day - Casual Leave");
//		} else if (!fullDayFound && !halfDayFound) {
//		    System.out.println("Neither 'Full Day - Casual Leave' nor 'Half Day - Casual Leave' found.");
//		}
	}
	
	public void SendFromDate() throws InterruptedException
	{
		Utility.ExplicitWait(FromDate);
		Utility.showCallout("Sending Comment", FromDate);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",FromDate );
		FromDate.sendKeys(Keys.CONTROL + "a");  
		FromDate.sendKeys(Keys.DELETE);
		Thread.sleep(1000); 
		driver2.findElement(By.xpath("//div[text()='5']")).click();
//		FromDate.sendKeys("25/11/2025");
		Thread.sleep(1000); 
	}
	
	public void SendToDate() throws InterruptedException
	{
		Utility.ExplicitWait(ToDate);
		Utility.showCallout("Sending Comment", ToDate);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",ToDate );
		ToDate.sendKeys(Keys.CONTROL + "a");  
		ToDate.sendKeys(Keys.DELETE);
		Thread.sleep(1000); 
		driver2.findElement(By.xpath("//div[text()='6']")).click();
//		ToDate.sendKeys("26/11/2025");
		Thread.sleep(2000); 
	}
	public void SendComment() throws InterruptedException
	{
		Utility.ExplicitWait(Comment);
		Utility.showCallout("Sending Comment", Comment);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",Comment );
		Comment.sendKeys("TestComment");
		Thread.sleep(2000); 
	}

	
	public void ClickonSubmit() throws InterruptedException
	{
		Utility.ExplicitWait(Submit);
		Utility.showCallout("Clicking on Submit", Submit);
		Submit.click();
	    Thread.sleep(2000);
	}

	public void SendTaskDescription() throws InterruptedException
	{
		Utility.ExplicitWait(TaskDescription);
		Utility.showCallout("Sending task Description", TaskDescription);
		TaskDescription.sendKeys("Test Description");
		Thread.sleep(2000);
	}
	public String GetTaskAlert() throws InterruptedException
	{
		Utility.ExplicitWait(TaskAlert);
		Utility.showCallout2("Validation check applied on Alert Message", TaskAlert);
		String ActualMsg2 = TaskAlert.getText();
		System.out.println(" Message received on Timesheet"+ActualMsg2);
		Thread.sleep(2000);
		return ActualMsg2 ;
	}

}






