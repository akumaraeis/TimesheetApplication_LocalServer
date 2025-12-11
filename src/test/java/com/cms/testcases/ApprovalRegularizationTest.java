package com.cms.testcases;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.cms.basetest.BaseTest;
import com.cms.utility.Log;
import com.cms.utility.Utility;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApprovalRegularizationTest extends BaseTest {
	public SoftAssert sf;
	public JavascriptExecutor js;
	public String RegularizeDate;
	public String trimRegularizeDate;
	public String token;

	@BeforeClass
	@Parameters("browser")
	public void openUrl(String browser) throws IOException
	{
		initBrowser(browser);
		creatingObject();	
		//		Utility.showTooltip("Browser is Launched using Selenium Automation Tool");
		
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
//	        Utility.waitForSeconds(2);
	        DeleteAutomationTestUserRecords(token);
//	        Utility.waitForSeconds(1);

		}


	@Test(priority=1)
	public void ValidateRegularizationFunctionality() throws InterruptedException, IOException
	{

		sf = new SoftAssert();
//		launchLocalUrl();
		Utility.showTooltip("Step 1:->After Launching Timesheet Application, Login as User to submit Regularization Request using Automation Script");

		Thread.sleep(2000);
		
		lp.SendUserName();
		Log.info("Input User Name");
		
		lp.SendPassword();
		Log.info("Input User Password");
		
		lp.ClickonLoginBtn();
		Log.info("login submitted with  user credential ");
		
		Utility.waitForSeconds(2);
		
	   String ActualProfileName=lp.GetProfileName();
	   String ExpectedProfileName ="Welcome, AutomationTesting!";
	   
	   sf.assertEquals(ActualProfileName, ExpectedProfileName);
	   Log.info("Profile Name is verified.");
	   
	   Utility.showTooltip("Step 2:->Selecting Regularization Module so that user can send Regularization using Automation Script");
	   
	   Utility.waitForSeconds(3);
	   
       trp.ClickonRegularizationRequestBtn();
       Log.info("User Navigate to Regularization page.");
       
       Utility.waitForSeconds(3);
       
       List<WebElement> table = driverR.findElements(By.xpath("//table[@class=\"shadow table table-sm table-striped table-bordered\"]//tbody//tr"));
       int tablesize = table.size();
       
       Utility.showTooltip("Step 3:->Selecting Respective Date for sending Regularization using Automation Script");
       Utility.waitForSeconds(3);
       for(int i=1 ;i<=tablesize ;i++)
       {
           String ActionStatusText = driverR.findElement(By.xpath("(//*[contains(text(),'Regularize')])['+i+']")).getText();
           boolean ActionButtonStatus = driverR.findElement(By.xpath("(//*[contains(text(),'Regularize')])['+i+']")).isEnabled();
           System.out.println("status Text found on Action Button :->"+ActionStatusText);
           System.out.println("Boolean Status found on Action Button :->"+ActionButtonStatus);//
           String RegularizeDate = driverR.findElement(By.xpath("//*[@class=\"shadow table table-sm table-striped table-bordered\"]//tbody//td[2]//span['+i+']")).getText();
           System.out.println("Selected Regularize Date :->"+RegularizeDate);
           trimRegularizeDate = RegularizeDate.replaceAll("\\s*\\(.*\\)", "");
           System.out.println("Trim Regularize Date :->"+trimRegularizeDate);
           Utility.showTooltip("New Regularization Request details Submitted using AutomationScript.");
           if((ActionStatusText.equalsIgnoreCase("Regularize"))&& (ActionButtonStatus == true) )      	   
           {
        	   WebElement ActionButton = driverR.findElement(By.xpath("(//*[contains(text(),'Regularize')])['+i+']"));
        	   js =(JavascriptExecutor)driverR;
        	   Utility.safeClick(driverR, js, ActionButton);
        	   Thread.sleep(2000);
        	   trp.SendRegularizationReason();
        	   trp.SendClockIn();
        	   trp.SendBreakIn();
        	   trp.SendBreakBreakOut();
        	   trp.SendClockOut();
        	   trp.ClickonSubmitBtn();
        	   
        	   String ActualMsg = trp.GetSuccessfulMsg();
        	   String ExpectMsg ="Regularization updated successfully!";
        	   sf.assertEquals(ActualMsg, ExpectMsg);
        	   Log.info("Regularization Request Sent successfully by User.");
        	   Utility.showTooltip("Regularization Request Sent successfully by User.");
        	   
        	    if(ActualMsg.equalsIgnoreCase("Regularization updated successfully!"))
        	    {
        	    	System.out.println("Loop Breaks");
        	    	break;
        	    }
        	    else
        	    {
        	    	System.out.println("Otherwise Loop Continues");
        	    }
           }
           else
           {
        	 System.out.println("Either Regularize or Action Button is not enabled");  
           }
           
           }
    	   sf.assertAll();
	}

       
	@Test(priority=2)
	public void ValidateApproveRegularizationFunctionality() throws InterruptedException, IOException
	{

		sf = new SoftAssert();
//		launchLocalUrl();
		Utility.showTooltip("Step 5:->Again opening Timesheet Application,Now Login as Line Manager to Approve Regularization Request sent using Automation Script");
		
		Utility.waitForSeconds(3);
		
		lp.SendAdminUserName2();
		Log.info("Input Line-Manager Credential");
		
		lp.SendAdminPassword();
		Log.info("Input Line-Manager Password");
		
		lp.ClickonLoginBtn();
		Log.info("Line-Manager Logged Successfully."); 
		
		Utility.waitForSeconds(2);
		
	   String ActualProfileName=lp.GetProfileName();
	   String ExpectedProfileName ="Welcome, Admin User!";
	   
	   
	   sf.assertEquals(ActualProfileName, ExpectedProfileName);
	   Log.info("Profile name validate successfully"); 
	   
	   Utility.showTooltip("Step 6:->Selecting Reularization Request Module & Approve the same using Automation Script");
	   
       trp.ClickonRegularizationApprovalBtn();
       
       Utility.waitForSeconds(2);
       
       Log.info("Navigate to RegularizationRequestBtn "); 
       
       driverR.findElement(By.xpath("(//td[contains(text(),'"+trimRegularizeDate+"')])//following::td[1]//button")).click();
       
       Utility.waitForSeconds(2);
       
       trp.SendRegularizationReason();
              
       trp.SelectRegularizationStatus();
       
       trp.ClickonSubmitBtn();
       
	   String ActualSuccessfulMsg= trp.GetSuccessfulMsg();
	   String ExpectedSuccessfulMsg ="Regularization updated successfully!";
	   
	   sf.assertEquals(ActualSuccessfulMsg, ExpectedSuccessfulMsg);
	   Log.info("Regularization Request approved successfully by Line-Manager.");

       sf.assertAll();
       }
	public void DeleteAutomationTestUserRecords(String token) {
//	    HashMap<String, String> data = new HashMap<>();
//	    data.put("test_timestamp", timestamp);

//	    System.out.println("→ Sending to " + endpoint + ": " + timestamp);

	    given()
	        .contentType("application/json")
	        .header("Authorization", "Token " + token)
//	        .body(data)
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



