package com.cms.pageObjects;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
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

public class RegularizationPage extends BaseTest{

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
	
	@FindBy(xpath="//div[@class='pt-2 px-3 fw-bold']")
	private WebElement ProfileName ;
	
	@FindBy(xpath="//span[@class='ms-2']")
	private WebElement LogOut ;
	
	@FindBy(xpath="//a[normalize-space()='Regularizations']")
	private WebElement Regularizations ;
	
	@FindBy(xpath="//a[normalize-space()='Regularization Requests']")
	private WebElement RegularizationRequest ;
	
	@FindBy(xpath="//a[normalize-space()='Regularization Approvals']")
	private WebElement RegularizationApproval ;
	
	@FindBy(xpath="//textarea[@placeholder='Enter reason']")
	private WebElement RegularizationReason ;
	
	@FindBy(xpath="//select[@name='action_status']")
	private WebElement RegularizationStatus ;
	
	@FindBy(xpath="//input[@name='requested_clock_in_time']")
	private WebElement ClockIn ;
	
	@FindBy(xpath="//input[@name='requested_break_in_time']")
	private WebElement BreakIn ;
	
	@FindBy(xpath="//input[@name='requested_break_out_time']")
	private WebElement BreakOut ;
	
	@FindBy(xpath="//input[@name='requested_clock_out_time']")
	private WebElement ClockOut ;
	
	@FindBy(xpath="//button[normalize-space()='Submit']")
	private WebElement SubmitBtn ;
	
	@FindBy(xpath="//*[contains(text(),'Regularization updated successfully!')]")
	private WebElement SuccessMsg ;
	
	// *********Construction Declaration to initialize Data Member********	
	
	public RegularizationPage(WebDriver driverR)
	{
		driver2 = driverR;
		PageFactory.initElements(driverR, this);
	}
	//********time stamp Creation *******************************************
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
	

	public void SendRegularizationReason() throws InterruptedException
	{
		Utility.ExplicitWait(RegularizationReason);
		Utility.showCallout("Sending Regularization Reason", RegularizationReason);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",RegularizationReason );
		RegularizationReason.sendKeys("Approving Regularization Request sent by User");
		Thread.sleep(2000);
	}
	
	public void SelectRegularizationStatus() throws InterruptedException
	{
		Utility.ExplicitWait(RegularizationStatus);
		Utility.showCallout("Selecting Regularization Status", RegularizationStatus);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",RegularizationStatus );
		Select S1 = new Select(RegularizationStatus);
		S1.selectByVisibleText("Approve");
	}
	public void SendClockIn() throws InterruptedException
	{
		Utility.ExplicitWait(ClockIn);
		Utility.showCallout("Sending Requested Clockin Time", ClockIn);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",ClockIn );
		ClockIn.sendKeys("10:30AM");
		Thread.sleep(2000);
	}
	
	public void SendBreakIn() throws InterruptedException
	{
		Utility.ExplicitWait(BreakIn);
		Utility.showCallout("Sending Requested BreakIn Time", BreakIn);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",BreakIn );
		BreakIn.sendKeys("14:00PM");
		Thread.sleep(2000);
	}
	
	public void SendBreakBreakOut() throws InterruptedException
	{
		Utility.ExplicitWait(BreakOut);
		Utility.showCallout("Sending Requested BreakOut Time", BreakOut);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",BreakOut );
		BreakOut.sendKeys("14:30PM");
		Thread.sleep(2000);
	}
	
	public void SendClockOut() throws InterruptedException
	{
		Utility.ExplicitWait(ClockOut);
		Utility.showCallout("Sending Requested ClockOut Time", ClockOut);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",ClockOut );
		ClockOut.sendKeys("19:30PM");
		Thread.sleep(2000);
	}
	
	public void ClickonSubmitBtn() throws InterruptedException
	{
		Utility.ExplicitWait(SubmitBtn);
		Utility.showCallout("Clicking on Submit Button", SubmitBtn);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:4px solid green;')",SubmitBtn );
		SubmitBtn.click();
	}
	public void SendUserName() throws InterruptedException
	{
		Utility.ExplicitWait(UserName);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",UserName );
		UserName.sendKeys("AutomationTestUser");
		Thread.sleep(2000);
	}

	public void SendInvalidUserName() throws InterruptedException
	{
		Utility.ExplicitWait(UserName);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",UserName );
		UserName.sendKeys("InvalidCredential");
		Thread.sleep(2000);
	}
	
	public void SendAdminUserName() throws InterruptedException
	{
		Utility.ExplicitWait(UserName);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",UserName );
		UserName.sendKeys("Faraz");
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

	public void SendAdminPassword() throws InterruptedException
	{
		Utility.ExplicitWait(Password);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",Password );
		Password.sendKeys("Test@123");
		Thread.sleep(2000); 
	}
	public void SendInvalidPassword() throws InterruptedException
	{
		Utility.ExplicitWait(Password);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",Password );
		Password.sendKeys("Test@1234");
		Thread.sleep(2000); 
	}

	public void ClickonLoginBtn() throws InterruptedException
	{
		Utility.ExplicitWait(Login);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:4px solid green;')",Login );
		Login.click();
	}


	public void ClickonRegularizationsBtn() throws InterruptedException
	{
		Utility.ExplicitWait(Regularizations);
		Utility.showCallout("Selecting Regularization ", Regularizations);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:4px solid green;')",Regularizations );
		Regularizations.click();
	}
	
	
	public void ClickonRegularizationRequestBtn() throws InterruptedException
	{
		Utility.ExplicitWait(RegularizationRequest);
		Utility.showCallout("Selecting Regularization ", RegularizationRequest);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:4px solid green;')",RegularizationRequest );
		RegularizationRequest.click();
	}
	
	public void ClickonRegularizationApprovalBtn() throws InterruptedException
	{
		Utility.ExplicitWait(RegularizationApproval);
		Utility.showCallout("Selecting Regularization ", RegularizationApproval);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:4px solid green;')",RegularizationApproval );
		RegularizationApproval.click();
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
	
	public String GetSuccessfulMsg() throws InterruptedException
	{
		Utility.ExplicitWait(SuccessMsg);
		Utility.showCallout2("Validation Checks applied on Alert Msg", SuccessMsg);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:4px solid green;')",SuccessMsg );
		Thread.sleep(2000);
		
		String SuccessfulMsg = SuccessMsg.getText();
		return SuccessfulMsg;
		}
	
	public void ClickonLogoutBtn() throws InterruptedException
	{
		Utility.ExplicitWait(LogOut);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:4px solid green;')",LogOut );
		LogOut.click();
	}



}






