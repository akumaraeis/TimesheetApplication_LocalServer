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

public class LoginPage extends BaseTest{

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
	
	@FindBy(xpath="//span[@class='fw-bold']")
	private WebElement ProfileName ;
	
	@FindBy(xpath="//span[@class='ms-2']")
	private WebElement LogOut ;
	
	// *********Construction Declaration to initialize Data Member********	
	
	public LoginPage(WebDriver driverR)
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
	

	public void SendUserName() throws InterruptedException
	{
		Utility.ExplicitWait(UserName);
		Utility.showCallout2("Entering user Name", UserName);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",UserName );
		UserName.sendKeys("AutomationTestUser");
//		UserName.sendKeys("akumar@ndtatlas.com");
		Thread.sleep(2000);
	}
	
	public void SendUserName2() throws InterruptedException
	{
		Utility.ExplicitWait(UserName);
		Utility.showCallout2("Entering user Name", UserName);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",UserName );
		UserName.sendKeys("tmptest@test.com");
		
		Thread.sleep(2000);
	}
	
	public void SendInvalidUserName() throws InterruptedException
	{
		Utility.ExplicitWait(UserName);
		Utility.showCallout2("Entering user Name", UserName);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",UserName );
		UserName.sendKeys("InvalidCredential");
		Thread.sleep(2000);
	}
	
	public void SendLine_ManagerName() throws InterruptedException
	{
		Utility.ExplicitWait(UserName);

		Utility.showCallout2("Entering user Name", UserName);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",UserName );
		UserName.sendKeys("linemtest@test.com");
		Thread.sleep(2000);
	}
	
	public void SendAdminUserName2() throws InterruptedException
	{
		Utility.ExplicitWait(UserName);
		Utility.showCallout2("Entering Test Line-Manager Credential", UserName);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",UserName );
		UserName.sendKeys("app_supermgr");


		Thread.sleep(2000);
	}
	public void SendPassword() throws InterruptedException
	{
		Utility.ExplicitWait(Password);
		Utility.showCallout2("Entering user Password", Password);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",Password );
		Password.sendKeys("Test@123");
		Utility.waitForSeconds(2);
	}

	public void SendAdminPassword() throws InterruptedException
	{
		Utility.ExplicitWait(Password);

		Utility.showCallout2("Entering Line Manager Password", Password);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",Password );
		Password.sendKeys("Test@123");
		Utility.waitForSeconds(2);
	}
	public void SendInvalidPassword() throws InterruptedException
	{
		Utility.ExplicitWait(Password);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid green;')",Password );
		Password.sendKeys("Test@1234");
		Utility.waitForSeconds(2);
	}

	public void ClickonLoginBtn() throws InterruptedException
	{
		Utility.ExplicitWait(Login);
		Utility.showCallout2("Click on Login Button", Login);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:4px solid green;')",Login );
		Utility.waitForSeconds(2);
		Login.click();
	}

	
	public String GetProfileName() throws InterruptedException
	{
		Utility.ExplicitWait(ProfileName);
		Utility.showCallout2("Validating Profile Name after Login", ProfileName);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:4px solid green;')",ProfileName );
		Thread.sleep(2000);
		
		String Pname = ProfileName.getText();
		return Pname;
		}
	
	public void ClickonLogoutBtn() throws InterruptedException
	{
		Utility.ExplicitWait(LogOut);
		js = (JavascriptExecutor)driver2;
		js.executeScript("arguments[0].setAttribute('style','background:yellow;border:4px solid green;')",LogOut );
		LogOut.click();
	}
}






