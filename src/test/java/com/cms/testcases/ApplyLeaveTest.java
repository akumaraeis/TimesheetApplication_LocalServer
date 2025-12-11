package com.cms.testcases;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.cms.basetest.BaseTest;
import com.cms.utility.Log;
import com.cms.utility.Utility;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ApplyLeaveTest extends BaseTest {
	public SoftAssert sf;
	public JavascriptExecutor js;
	public boolean isSuccessful = false;
	public String clockInDate;
	public boolean isTaskSuccessful = false ;
	public String FinalAlert;
	public String token;



	@BeforeSuite
	public void setupLogging() {
	    Logger.getLogger("org.openqa.selenium.remote.http.WebSocket")
	          .setLevel(Level.SEVERE);  // or WARNING
	}



	@BeforeClass
	@Parameters("browser")
	public void openUrl(String browser) throws IOException
	{
		initBrowser(browser);
		creatingObject();	
		//		Utility.showTooltip("Browser is Launched using Selenium Automation Tool");
		sf = new SoftAssert();
	}

		@BeforeMethod
	public void LaunchUrl() throws IOException, InterruptedException
	{
		//		Thread.sleep(2000);
		launchLocalUrl();
	}

	
		@Test(priority=1)
		public void DeleteTestUserRecord() throws InterruptedException, IOException
		{
			RestAssured.baseURI = "http://192.168.1.10:8085";
			Response loginResponse = RestAssured.given()
					.header("Content-Type", "application/json")
					.body("{ \"username\": \"AutomationTestUser\", \"password\": \"Test@123\" }")
					.when().post("/api/auth/login/")
					.then().statusCode(200)
					.extract().response();
			token = loginResponse.jsonPath().getString("data.token");
			System.out.println("🔐 Token fetched: " + token);
			DeleteAutomationTestUserRecords(token);
		}
		
	@Test(priority=2)
	public void ValidateapplyLeaveFunctionality() throws InterruptedException, IOException
	{

		Utility.showTooltip("Step 1:->After Launching Timesheet Application, Login as User to apply Leave Request using Automation Script");

		Utility.waitForSeconds(2);
		
		lp.SendUserName();

		lp.SendPassword();

		lp.ClickonLoginBtn();
		
		Log.info("User successfully Login in Application");
		
		Utility.waitForSeconds(2);
		String ActualProfileName=lp.GetProfileName();
		String ExpectedProfileName ="Welcome, AutomationTesting";
		Log.info("User Profile successfully Verified in Application");
		
		sf.assertEquals(ActualProfileName, ExpectedProfileName);
		
		Utility.showTooltip("Step 2:->After Selecting Apply Leave Module , all inputs provided using Automation Script");
		
		alp.ClickonApplyLeave();
		
		Utility.waitForSeconds(2);
		
		alp.ClickonApplyLeaveBtn();
		
		Utility.waitForSeconds(2);
		
		alp.SendFromDate();
		
		Utility.waitForSeconds(2);
		
		alp.SendToDate();
		
		Utility.waitForSeconds(2);
		
		alp.SelectLeaveType();
		
		Utility.waitForSeconds(2);
		
		alp.SendComment();
		
		alp.ClickonSubmitBtn();
		
		Thread.sleep(2000);
		Utility.showTooltip("Step 3:->After Applying Leave , Validate the Submission using Automation Script");
		
		Thread.sleep(1000);
		WebElement Toaster = driverR.findElement(By.xpath("//*[@role=\"alert\"]"));
		String Toaster_Msg = Toaster.getText();
		System.out.println("Message Received on Submitting Apply Leave:->"+Toaster_Msg);
		
		Log.info("Leave Applied Successfully");
	
		
		Utility.showTooltip("Step 4:-> Clicking User List using Automation Script");
		
//		sf.assertAll();

	}
	
	public void DeleteAutomationTestUserRecords(String token) {
		given()
		.contentType("application/json")
		.header("Authorization", "Token " + token)
		//		        .body(data)
		.when()
		.post("http://192.168.1.10:8085/api/utils/remove-automation-test-data/")
		.then()
		.statusCode(200)
		.log().all();
	}

		@AfterMethod
	public void closeURL()
	{
		driverR.navigate().to("about:blank");
	}

	@AfterClass
	public void closebrowser()
	{
		teardown();
	}

}



