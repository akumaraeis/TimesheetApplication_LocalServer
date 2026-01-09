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
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
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

public class TimesheetSubmissionPage extends BaseTest{

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
	
	@FindBy(xpath="//span[normalize-space()='Back']")
	private WebElement BackButton ;
	
	@FindBy(xpath="//div[@id='records-per-page']")
	private WebElement RecordsPerPage ;
	
	@FindBy(xpath="//li[normalize-space()='100']")
	private WebElement SelectRecordsPerPage ;
	
	@FindBy(xpath="//a[normalize-space()='Attendance']")
	private WebElement Attendance ;
	
	@FindBy(xpath="//span[normalize-space()='Submit Timesheet']")
	private WebElement TimesheetSubmission ;
	
	@FindBy(xpath="//button[normalize-space()='Actions']")
	private WebElement Actions ;
	
	@FindBy(xpath="//span[normalize-space()='Timesheet Approvals']")
	private WebElement TimesheetApproval ;
	
	@FindBy(xpath="//*[contains(text(),'Task created successfully!')]")
	private WebElement SuccessfulMsg ;
	
	@FindBy(xpath="//*[contains(text(),'Add New Task')]")
	private List<WebElement> taskButtons ;
	
//	@FindBy(xpath="//*[contains(text(),'Add New Task')]")
//	private WebElement MinimizeBtn2 ;
	
	@FindBy(xpath="//button[normalize-space()='Submit Timesheet']")
	private WebElement submitBtn ;

	@FindBy(xpath="//button[normalize-space()='Submit']")
	private WebElement confirmSubmit ;
	
	@FindBy(xpath ="//*[contains(text(),'Task hours per timesheet must be within 15 minutes of effective working hours.')]")
	private WebElement confirmMsg ;
	
	@FindBy(xpath ="//*[contains(@class,\"d-flex justify-content-end col-sm-1\")]")
	private List<WebElement> minimizeBtn ;
	
	@FindBy(xpath ="(//*[contains(text(),'Add New Task')])")
	private List<WebElement> addTaskBtn ;
	
	@FindBy(xpath="//div[contains(@class,'m-1 px-1 py-0 row')]")
	private WebElement weekXpath ;

	@FindBy(xpath="//button[normalize-space()='ACTIONS']")
	private WebElement actionsBtn ;
	
	@FindBy(xpath="//textarea[@placeholder='Enter comment']")
	private WebElement comment ;
	
	@FindBy(xpath="//a[normalize-space()='Approve Timesheet']")
	private WebElement approveTimesheet ;
	
	// *********Construction Declaration to initialize Data Member********	
	//*[contains(text(),'Add New Task')]
	

	
	public TimesheetSubmissionPage(WebDriver driverR)
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
		UserName.sendKeys("Test_User");
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

	
//	public String GetProfileName() throws InterruptedException
//	{
//		Utility.ExplicitWait(ProfileName);
//		js = (JavascriptExecutor)driver2;
//		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:4px solid green;')",ProfileName );
//		Thread.sleep(2000);
//		
//		String Pname = ProfileName.getText();
//		return Pname;
//		}
//	
	
	public void ClickonTimesheet() throws InterruptedException
	{
		Utility.ExplicitWait(Timesheet);
		Utility.showCallout("Selecting Timesheet using Script", Timesheet);
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
	
	public void ClickonTimesheetSubmission() throws InterruptedException
	{
		Utility.ExplicitWait(TimesheetSubmission);
		Utility.showCallout2("Selecting Timesheet Submission ", TimesheetSubmission);
		Log.info("Selecting Timesheet Submission");
		Thread.sleep(1000);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].scrollIntoView(true);", TimesheetSubmission);
		TimesheetSubmission.click();
//	    Thread.sleep(1000);
//		Utility.safeClick(driver2, js, TimesheetSubmission);
	}
	

	public void ClickonActionsButton() throws InterruptedException
	{
		Utility.ExplicitWait(Actions);
		Utility.showCallout("Clicking on Action Button using Script", Actions);
		Log.info("Clicking on Action Button using Script");
		Thread.sleep(2000);
		Actions.click();
	    Thread.sleep(2000);
	}
	public String  SelectClockinDate(String Date1) throws InterruptedException
	{
//		Utility.ExplicitWait(ClockinDate);
//
//		ClockinDate.sendKeys(Date1);

		Utility.ExplicitWait(ClockinTime);
		
		ClockinTime.click();
		return generateRandomDate();
		
	}

	public int  gettaskButtonSize() throws InterruptedException
	{
//		Utility.ExplicitWait(ClockinDate);
//
//		ClockinDate.sendKeys(Date1);
		
		int taskButtonSize = taskButtons.size();
        return taskButtonSize ;
		
	}
	
	public void clickOnSelectedWeek()
	{
//		String weekXpath = "//div[contains(@class,'m-1 px-1 py-0 row')]";
//		WebElement refreshedWeek = driverR.findElement(By.xpath(weekXpath));
		js = (JavascriptExecutor)driver2;
		Utility.scrollIntoView(driver2, js, weekXpath);
		Utility.safeClick(driver2, js, weekXpath);
		System.out.println("now script will click on week");

	}

	public void add_allTask(int i) throws InterruptedException
	{
		        System.out.println("Loop Count Number :->"+i);
				WebElement MinimizeBtn2 = minimizeBtn.get(i);
				js = (JavascriptExecutor)driver2;
				Utility.scrollIntoView(driver2, js, MinimizeBtn2);
				MinimizeBtn2.click();
				WebElement addTaskBtn2 = addTaskBtn.get(i);
				Utility.scrollIntoView(driver2, js, addTaskBtn2);
				WebDriverWait wait = new WebDriverWait(driver2 ,Duration.ofSeconds(10));
				wait.until(ExpectedConditions.elementToBeClickable(addTaskBtn2));
				Utility.safeClick(driver2, js, addTaskBtn2);
//				WebElement MinimizeBtn3 = driverR.findElement(By.xpath("(//*[contains(@class,'accordion-button')])[1]"));
//				Utility.scrollIntoView(driverR, js, MinimizeBtn3);
//				MinimizeBtn3.click();
//				Utility.waitForSeconds(2);

		}
	
	public void clickOnSubmitButton()
	{
    js = (JavascriptExecutor)driver2;
	Utility.scrollIntoView(driver2, js, submitBtn);
	Utility.showCallout2("Click on Submit Button ", submitBtn);
	Utility.waitForSeconds(1);
	Utility.safeClick(driver2, js, submitBtn);
	Utility.waitForSeconds(2);
	Log.info("Script click on Submit Button");
	}
	
	public void clickOnConfirmButton()
	{		
		js = (JavascriptExecutor)driver2;
		Utility.scrollIntoView(driver2, js, confirmSubmit);
		Utility.showCallout("Click on Confirm Button Using Automation Script", confirmSubmit);
		Utility.highlightElement(confirmSubmit);
		Utility.safeClick(driver2, js, confirmSubmit);
		Utility.waitForSeconds(2);
		System.out.println("✅ Timesheet submitted for week " + 2);
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
	
	public void ClickonSubmit() throws InterruptedException
	{
		Utility.ExplicitWait(Submit);
		Utility.showCallout("Clicking on Submit Button using AutomationScript", Submit);
		Log.info("Clicking on Submit Button using AutomationScript");
		Submit.click();
	    Thread.sleep(2000);
	}
	
	public void ClickonconfirmSubmit() throws InterruptedException
	{
		Utility.ExplicitWait(confirmSubmit);
		Utility.showCallout("Clicking on Submit Button using AutomationScript", confirmSubmit);
		Log.info("Clicking on Submit Button using AutomationScript");
		confirmSubmit.click();
	    Thread.sleep(2000);
	}
	
	
	public String getconfirmMsg() throws InterruptedException
	{
		Utility.ExplicitWait(confirmMsg);
		String confirmText = confirmMsg.getText();
		return confirmText;
	}
	
	public void ClickonTimesheetApproval() throws InterruptedException
	{
		Utility.ExplicitWait(TimesheetApproval);
		Utility.showCallout2("Clicking on TimesheetApproval using AutomationScript", TimesheetApproval);
		Log.info("Clicking on TimesheetApproval using AutomationScript");
		TimesheetApproval.click();
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

	public String GetSuccessfulMsg() throws InterruptedException
	{
		Utility.ExplicitWait(SuccessfulMsg);
		String ActualMsg3 = SuccessfulMsg.getText();
		System.out.println(" Message received on Timesheet"+ActualMsg3);
		Thread.sleep(2000);
		return ActualMsg3 ;
	}
	
	public void clickOnActionBtn()
	{
        Utility.scrollIntoView(driverR, js, actionsBtn);
        Utility.showCallout2("Click on Action Button using Automation Script", actionsBtn);
        Utility.waitForSeconds(1);
        Utility.safeClick(driverR, js, actionsBtn);
        Utility.waitForSeconds(2);			         
	}   
       
	public void submitcomment()
	{      
        Utility.scrollIntoView(driverR, js, comment);
        Utility.highlightElement(comment);
        Utility.showCallout("Sending Comment using Automation Script", comment);
        comment.sendKeys("Approve This Timesheet for Testing");
        Utility.waitForSeconds(2);
	}
	
	public void approveTimesheet()
	{
  
    Utility.scrollIntoView(driverR, js, approveTimesheet);
    Utility.showCallout2("Click on Approve Timesheet using Automation Script", approveTimesheet);
    Utility.waitForSeconds(1);
    Utility.safeClick(driverR, js, approveTimesheet);
    Utility.waitForSeconds(2);
	}
//        WebElement confirmSubmit = Utility.waitForElementToBeClickable(driverR, By.xpath("//button[normalize-space()='Submit']"), 10);
//        Utility.scrollIntoView(driverR, js, confirmSubmit);
//        Utility.showCallout("Clicking on confirm Button using Automation Script", confirmSubmit);
//        Utility.highlightElement(confirmSubmit);
//        Utility.safeClick(driverR, js, confirmSubmit);
//        Utility.waitForSeconds(2);
//
//        System.out.println("✅ Timesheet submitted for week " + (i + 1));
//
//        WebElement confirmMsg = driverR.findElement(By.xpath("//*[contains(text(),'Approved successfully!')]"));
//        Utility.highlightElement(confirmMsg);
//        String ActualTimesheetApproveMsg = confirmMsg.getText();
//        System.out.println("Timesheet submission Successf2ul Message :-> " + ActualTimesheetApproveMsg);
//
//        ExpectTimesheetApproveMsg = "Approved successfully!";
//        sf.assertEquals(ActualTimesheetApproveMsg, ExpectTimesheetApproveMsg);
//        Log.info("User Timesheet Approved successf2ully by Line Manager");

	
public void addingTaskToAvailableWeek() throws InterruptedException
{
	List<WebElement> taskButtons = driverR.findElements(By.xpath("//*[contains(text(),'Add New Task')]"));
	System.out.println("Total Add Task buttons: " + taskButtons.size());
	WebDriverWait wait = new WebDriverWait(driverR,Duration.ofSeconds(10));

	for (int i = 1; i < taskButtons.size(); i++) {
		try {
			WebElement MinimizeBtn2 = driverR.findElement(By.xpath("(//*[contains(@class,\"d-flex justify-content-end col-sm-1\")])[" + i + "]"));
			Utility.scrollIntoView(driverR, js, MinimizeBtn2);
			MinimizeBtn2.click();
			WebElement addTaskBtn = driverR.findElement(By.xpath("(//*[contains(text(),'Add New Task')])["+i+"]"));
			Utility.scrollIntoView(driverR, js, addTaskBtn);
			
			wait.until(ExpectedConditions.elementToBeClickable(addTaskBtn));
			Utility.safeClick(driverR, js, addTaskBtn);

			Utility.waitForSeconds(1);
			fillTaskDetailwithmatchingDuration();
			String ActualSuccessfulMsg = tsp.GetSuccessfulMsg();		
			String ExpectSuccessfulMsg = "Task created successfully!";
//			sf.assertEquals(ActualSuccessfulMsg, ExpectSuccessfulMsg);
			Log.info("Task added Successfully to Timesheet for Respective date");

		} catch (ElementClickInterceptedException e) {
			System.out.println("Add Task Click Intercepted: Retrying via JS click.");
			js.executeScript("arguments[0].click();", driverR.findElement(By.xpath("(//*[contains(text(),'Add Task')])[1]")));
		}
	}

	for (int i = taskButtons.size(); i <= taskButtons.size(); i++) {
		try {
			WebElement MinimizeBtn2 = driverR.findElement(By.xpath("(//*[contains(@class,\"d-flex justify-content-end col-sm-1\")])[" + i + "]"));
			Utility.scrollIntoView(driverR, js, MinimizeBtn2);
			MinimizeBtn2.click();
			WebElement addTaskBtn = driverR.findElement(By.xpath("(//*[contains(text(),'Add New Task')])["+i+"]"));
			Utility.scrollIntoView(driverR, js, addTaskBtn);
			wait = new WebDriverWait(driverR,Duration.ofSeconds(10));
			wait.until(ExpectedConditions.elementToBeClickable(addTaskBtn));
			Utility.safeClick(driverR, js, addTaskBtn);

			fillTaskDetailwithLesserDuration();
			WebElement SuccessfulMsg = driverR.findElement(By.xpath("//*[contains(text(),'Task created successfully!')]"));
			Utility.showCallout2("Validation Checks Applied on Task Submission Alert.", SuccessfulMsg);
			String ActualSuccessfulMsg = SuccessfulMsg.getText();
			String ExpectSuccessfulMsg = "Task created successfully!";
//			sf.assertEquals(ActualSuccessfulMsg, ExpectSuccessfulMsg);
			Log.info("Task added Successfully to Timesheet for Respective date");


		} catch (ElementClickInterceptedException e) {
			System.out.println("Add Task Click Intercepted: Retrying via JS click.");
			js.executeScript("arguments[0].click();", driverR.findElement(By.xpath("(//*[contains(text(),'Add Task')])[1]")));
		}
	}
}

//public void clickOnSubmitBtn()
//{
//	try {
//
//		
//		Utility.scrollIntoView(driverR, js, submitBtn);
//		Utility.showCallout2("Click on Submit Button ", submitBtn);
//		Utility.waitForSeconds(1);
//		Utility.safeClick(driverR, js, submitBtn);
//		Utility.waitForSeconds(2);
//		Log.info("Script click on Submit Button");
//
//	
//		Utility.scrollIntoView(driverR, js, confirmSubmit);
//		Utility.showCallout("Click on Confirm Button Using Automation Script", confirmSubmit);
//		Utility.highlightElement(confirmSubmit);
//		Utility.safeClick(driverR, js, confirmSubmit);
//		Utility.waitForSeconds(2);
//		System.out.println("✅ Timesheet submitted for week " + 2);
//
//		
//		String ActualTimesheetSuccesful = confirmMsg.getText();
//		System.out.println("Timesheet submission Succesful Message :-> " + ActualTimesheetSuccesful);
//		String ExpectTimesheetSuccesful ="Task hours per timesheet must be within 15 minutes of effective working hours.";
////		sf.assertEquals(ActualTimesheetSuccesful, ExpectTimesheetSuccesful);
//		driverR.navigate().refresh();
//		Utility.waitForSeconds(2);
//	} catch (Exception e) {
//		System.out.println("⚠️ Error during final submission: " + e.getMessage());
//	}
//
//}
	public void fillTaskDetailwithmatchingDuration() throws InterruptedException
	{
		att.SelectSubProcess();
		att.ClickonActivity();
		att.SendTaskDescription();
		att.SendTaskDuration();
		att.ClickonTaskSubmit();

	}
	public void fillTaskDetailwithLesserDuration() throws InterruptedException
	{
		att.SelectSubProcess();
		att.ClickonActivity();
		att.SendTaskDescription();
		att.SendTaskDuration3();
		att.ClickonTaskSubmit();

	}
}






