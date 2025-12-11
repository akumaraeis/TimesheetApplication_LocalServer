package com.cms.testcases;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.NoSuchElementException;


import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.cms.basetest.BaseTest;
import com.cms.utility.Log;
//import com.cms.utility.LogHelper;
import com.cms.utility.Utility;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import java.util.List;


public class TimesheetSubmissionApprovalScenario3 extends BaseTest {
	public SoftAssert sf;
	public SoftAssert sf2;
	public JavascriptExecutor js;
	public boolean isSuccessful = false;
	public String clockInDate;
	public boolean isTaskSuccessful = false ;
	public String FinalAlert;
	boolean keepProcessing = true;
	//	private int index=2;
	public String clockInTime;
	public String Timein;
	public String updatedTimeStr;
	public  String token;
	String Start_date ;
	String weekContainerXPath;
	int totalWeeks ;
	public WebElement MinimizeBtn;
	public String ActualUserName;
	public String ExpectTimesheetApproveMsg;
	public String dateStr;

	@BeforeClass
	@Parameters("browser")
	public void openUrl(String browser) throws IOException
	{
		initBrowser(browser);
		creatingObject();	
		//		Utility.showTooltip("Browser is Launched using Selenium Automation Tool");
		
	}

	//	@BeforeMethod
	public void LaunchUrl() throws IOException, InterruptedException
	{
		//		Thread.sleep(2000);
		LaunchUrl();
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
		//        Utility.waitForSeconds(2);
		DeleteAutomationTestUserRecords(token);
		//        Utility.waitForSeconds(1);

	}


	@Test(priority=2)
	public void ValidateTimesheetSubmissionFunctionality() throws InterruptedException, IOException
	{
		sf = new SoftAssert();
		Utility.showTooltip("Executing Automation Script to Check Timesheet Submission by User and Approval Functionality by Line Manager.");

		Utility.waitForSeconds(5);
		
		launchLocalUrl();
		Log.info("Url is launched");

		Utility.showTooltip("Step 1:->After Launching Timesheet Application, Login as User to submit Weekly Timesheet using Automation Script");

		Utility.waitForSeconds(2);

		lp.SendUserName();
		Log.info("input user name");
		
		lp.SendPassword();
		Log.info("input user Password");
		
		lp.ClickonLoginBtn();
		Log.info("Click on Login Button");

		String ActualProfileName=lp.GetProfileName();
		String ExpectedProfileName ="Welcome, AutomationTesting";
		Log.info("Profile Name is verified");
		
		String str =  "Welcome, AutomationTesting";
		ActualUserName = str.replace("Welcome, ", "");
		System.out.println(ActualUserName);


		sf.assertEquals(ActualProfileName, ExpectedProfileName);
		tsp.ClickonTimesheetSubmission();
	
		Utility.waitForSeconds(3);
		Utility.showTooltip("Step 2:-> Selecting Respective Week to Validate Timesheet submission Using Automation Script");
        Thread.sleep(5000);
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

		List<WebElement> allWeeks = driverR.findElements(By.xpath("//div[contains(@class,'p-1 shadow mb-2 bg-gradient border-2')]"));
		System.out.println("Total week blocks found: " + allWeeks.size());
		totalWeeks = allWeeks.size();
		System.out.println("Total week size so that required for Loop"+ totalWeeks);
		// API Login

		Utility.showTooltip("Step 3 :->After Selecting Week,Timesheet Entry from (Mon-Fri) are creating in Background using API RestAssured ");
		for (int index = 2; index <= totalWeeks; index++) {
			String weekXPath = "(//div[contains(@class,'p-1 shadow mb-2 bg-gradient border-2')])[" + index + "]";
			WebElement weekElement = driverR.findElement(By.xpath(weekXPath));
			js = (JavascriptExecutor) driverR;
			js.executeScript("arguments[0].scrollIntoView(true);", weekElement);
			Utility.safeClick(driverR, js, weekElement);

			WebDriverWait wait = new WebDriverWait(driverR, Duration.ofSeconds(10));
			WebElement statusElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[contains(@class,'px-3 py-1')])[2]")));
			String status = statusElement.getText().replace("\u00A0", " ").trim();
			System.out.println("Week " + index + " Status: '" + status + "'");

			if (status.equalsIgnoreCase("NOT SUBMITTED")) {
			    String startText = driverR.findElement(By.xpath("(" + weekXPath + "/div/div/span)[1]")).getText().trim();
			    String endText = driverR.findElement(By.xpath("(" + weekXPath + "/div/div/span)[3]")).getText().trim();

			    DateTimeFormatter inputFormatter2 = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.ENGLISH);
			    LocalDate startDate = LocalDate.parse(startText, inputFormatter2);
			    LocalDate endDate = LocalDate.parse(endText, inputFormatter2);
			    System.out.println("Parsed Start Date: " + startDate + " | End Date: " + endDate);

			    DateTimeFormatter outputFormatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			    for (LocalDate date = startDate.plusDays(2); !date.isAfter(endDate); date = date.plusDays(1)) {
			        String dateStr = date.format(outputFormatter2);
			        System.out.println("Date Stamp entry for timestamp for send Attendance Method " + dateStr);
			        System.out.println("🗓️ Submitting entries for:->" + dateStr);

			        // ⚡ Reset baseTime at the start of each day
			        String baseTime = "04:30:00";

			        for (int i = 0; i < 2; i++) {
			            System.out.println("------ Iteration: " + i + " ------");

			            if (i > 0) {
			                baseTime = addHours(baseTime, 6);
			                System.out.println("Updated baseTime after CLOCK_OUT: " + baseTime);
			            } else {
			                System.out.println("Initial baseTime: " + baseTime);
			            }

			            try {
			                sendAttendanceData3(token, dateStr, baseTime, "CLOCK_IN");
			       
			                sendAttendanceData3(token, dateStr, addHours(baseTime, 1), "BREAK_IN");
			        
			                sendAttendanceData3(token, dateStr, addHours(baseTime, 2), "BREAK_OUT");
			              
			                sendAttendanceData3(token, dateStr, addHours(baseTime, 5), "CLOCK_OUT");
			               
			            } catch (Exception e) {
			                System.out.println("⚠ Error in iteration " + i + ": " + e.getMessage());
			                e.printStackTrace();
			            }
			        }
			    }
			
//	            
//				for (LocalDate date = startDate.plusDays(3); !date.isAfter(endDate); date = date.plusDays(1)) {
//					dateStr = date.format(outputFormatter2);
//					System.out.println("Date Stamp entry for timestamp for send Attendance Method "+dateStr);
//					System.out.println("🗓️ Submitting entries for:->" + dateStr);
//					sendAttendanceData(token,  dateStr + "T" + baseTime + "Z", "clockin");
//					sendAttendanceData2(token, dateStr + "T" + addHours(baseTime, 1) + "Z", "breakin");
//					sendAttendanceData2(token, dateStr + "T" + addHours(baseTime, 2) + "Z", "breakout");
//					sendAttendanceData2(token, dateStr + "T" + addHours(baseTime, 9) + "Z", "clockout");
//					Utility.waitForSeconds(2);
//					Log.info("Timesheet entry Created Successfully for date :->"+dateStr);
//				}
				// Add task and submit timesheet
				driverR.navigate().refresh();
				Utility.waitForSeconds(2);
				WebElement refreshedWeek = Utility.waitForElementToBeClickable(driverR, By.xpath(weekXPath), 10);
				Utility.scrollIntoView(driverR, js, refreshedWeek);
				Utility.safeClick(driverR, js, refreshedWeek);


				// 1) Find all accordion items (each date)
				List<WebElement> accordionItems = driverR.findElements(By.xpath("//div[contains(@class,'accordion-item')]"));
				System.out.println("Total date accordion items: " + accordionItems.size());

				Utility.showTooltip("Step 4: Adding tasks to all entries for each date");

				for (int i = 0; i < accordionItems.size(); i++) {
				    try {
				        // Re-find the accordion item each loop to avoid StaleElementReferenceException
				        WebElement accordionItem = driverR.findElements(By.xpath("//div[contains(@class,'accordion-item')]")).get(i);

				        // Find the accordion button inside this item and expand if not already expanded
				        WebElement accordionBtn = accordionItem.findElement(By.xpath(".//button[contains(@class,'accordion-button')]"));
				        Utility.scrollIntoView(driverR, js, accordionBtn);
				        wait.until(ExpectedConditions.elementToBeClickable(accordionBtn));

				        String aria = accordionBtn.getAttribute("aria-expanded");
				        if (aria == null || !"true".equalsIgnoreCase(aria)) {
				            // Click to expand
				            try {
				                accordionBtn.click();
				            } catch (ElementClickInterceptedException ex) {
				                js.executeScript("arguments[0].click();", accordionBtn);
				            }
				            // small wait for the content to render
				            Utility.waitForSeconds(1);
				        }

				        // 2) Now find entry blocks inside THIS accordion item.
				        //    Adjust this XPath if your entry container has a known class.
				        List<WebElement> entries = accordionItem.findElements(
				            By.xpath(".//div[.//text()[contains(.,'CLOCK-IN')] and .//text()[contains(.,'CLOCK-OUT')]]")
				        );
				        System.out.println("Date #" + (i + 1) + " entries found: " + entries.size());

				        // If the generic entry XPath didn't work, try finding Add Task buttons directly inside the item:
				        if (entries.isEmpty()) {
				            System.out.println("Fallback: locating Add Task buttons directly within accordion item");
				            entries = new ArrayList<>();
				            List<WebElement> fallbackButtons = accordionItem.findElements(By.xpath(".//button[contains(normalize-space(.),'Add Task')]"));
				            // Create a wrapper element per button so we can treat them like 'entries'
				            for (WebElement btn : fallbackButtons) {
				                // NOTE: this is a lightweight fallback — we will use btn directly later
				                // Add a dummy element placeholder (we will not use the entries list's elements directly)
				                entries.add(btn); // just to get correct count; will refetch button for each index below
				            }
				        }

				        // 3) Loop through each entry and click its Add Task button
				        for (int j = 0; j < entries.size(); j++) {
				            try {
				                // Re-locate the current accordionItem and its entries/buttons to prevent stale references
				                accordionItem = driverR.findElements(By.xpath("//div[contains(@class,'accordion-item')]")).get(i);

				                // Preferred: locate the entry container and then find the Add Task button inside it
				                List<WebElement> freshEntries = accordionItem.findElements(
				                    By.xpath(".//div[.//text()[contains(.,'CLOCK-IN')] and .//text()[contains(.,'CLOCK-OUT')]]")
				                );

				                WebElement addTaskBtn;
				                if (freshEntries.size() > j) {
				                    WebElement entryContainer = freshEntries.get(j);
				                    addTaskBtn = entryContainer.findElement(By.xpath(".//button[contains(normalize-space(.),'Add Task')]"));
				                } else {
				                    // fallback: there may be no distinct entry containers, so find Add Task buttons directly
				                    List<WebElement> fallbackButtons = accordionItem.findElements(By.xpath(".//button[contains(normalize-space(.),'Add Task')]"));
				                    if (fallbackButtons.size() <= j) {
				                        System.out.println("No Add Task button found for index " + j + " in date #" + (i + 1));
				                        continue;
				                    }
				                    addTaskBtn = fallbackButtons.get(j);
				                }

				                // Scroll and click the Add Task button (with JS fallback)
				                Utility.scrollIntoView(driverR, js, addTaskBtn);
				                wait.until(ExpectedConditions.elementToBeClickable(addTaskBtn));
				                try {
				                    addTaskBtn.click();
				                } catch (ElementClickInterceptedException ex) {
				                    System.out.println("Click intercepted — using JS click");
				                    js.executeScript("arguments[0].click();", addTaskBtn);
				                }

				                // 4) Wait for modal / form to appear, then fill it
				                //    Adjust modal XPath if it differs on your page
				                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'modal') or contains(@class,'task-modal')]")));

				                // Call your existing helper methods to fill the form
				                att.SelectSubProcess();
				                att.ClickonActivity();
				                att.SendTaskDescription();
				                att.SendTaskDuration();
				                att.ClickonTaskSubmit();

				                // Wait for success message and small pause so DOM settles
				                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Task created successfully!')]")));
				                Utility.waitForSeconds(1);

				                Log.info("Task added successfully to entry " + (j + 1) + " of date " + (i + 1));

				            } catch (StaleElementReferenceException | IndexOutOfBoundsException staleEx) {
				                // The DOM refreshed; retry this index by decrementing j (or simply continue — we refetch at top)
				                System.out.println("Stale or index problem while processing entry " + j + " — will retry.");
				                j--; // retry same index (be careful: if it keeps failing, you'll want a retry limit)
				                Utility.waitForSeconds(1);
				            }
				        }
				        
				    }catch (Exception e) {
				                System.out.println( e.getMessage());
				                e.printStackTrace();
				            }
				        }


//				for (int i = 5; i <=taskButtons.size(); i++) {
//					try {
//						
//						WebElement MinimizeBtn2 = driverR.findElement(By.xpath("(//*[contains(@class,'accordion-button')])[5]"));
//						Utility.scrollIntoView(driverR, js, MinimizeBtn2);
//						MinimizeBtn2.click();
//						WebElement addTaskBtn = driverR.findElement(By.xpath("(//*[contains(text(),'Add Task')])[1]"));
//						JavascriptExecutor js2 = (JavascriptExecutor)driverR;
//						js2.executeScript("window.scrollBy(0, 1000);"); 
//						
//						Utility.scrollIntoView(driverR, js, addTaskBtn);
//						wait.until(ExpectedConditions.elementToBeClickable(addTaskBtn));
//						Utility.safeClick(driverR, js, addTaskBtn);
//
//						att.SelectSubProcess();
//						att.ClickonActivity();
//						att.SendTaskDescription();
//						att.SendTaskDuration4();
//						att.ClickonTaskSubmit();
//						Utility.waitForSeconds(1);
//
//						WebElement SuccessfulMsg = driverR.findElement(By.xpath("//*[contains(text(),'Task created successfully!')]"));
////						Utility.highlightElement(SuccessfulMsg);
//						Utility.showCallout2("Validation Checks Applied on Task Submission Alert.", SuccessfulMsg);
//						String ActualSuccessfulMsg = SuccessfulMsg.getText();
//						String ExpectSuccessfulMsg = "Task created successfully!";
//						sf.assertEquals(ActualSuccessfulMsg, ExpectSuccessfulMsg);
//						Log.info("Task added Successfully to Timesheet for Respective date");
//
////						WebElement MinimizeBtn3 = driverR.findElement(By.xpath("(//*[contains(@class,'accordion-button')])["+i+"]"));
////						Utility.scrollIntoView(driverR, js, MinimizeBtn3);
////						MinimizeBtn3.click();
////						Utility.waitForSeconds(2);
//					} catch (ElementClickInterceptedException e) {
//						System.out.println("Add Task Click Intercepted: Retrying via JS click.");
//						js.executeScript("arguments[0].click();", driverR.findElement(By.xpath("(//*[contains(text(),'Add Task')])[1]")));
//					}
//				}			
				
				

				
				Utility.showTooltip("Step 5:-> After adding Task, submitting this weekly Timesheet using Automation Script");
				// Submit timesheet
				try {
					WebElement actionsBtn = Utility.waitForElementToBeClickable(driverR, By.xpath("//button[normalize-space()='Actions']"), 10);
					Utility.scrollIntoView(driverR, js, actionsBtn);
					Utility.showCallout2("Click on actions Button ", actionsBtn);
					Utility.waitForSeconds(1);
					Utility.safeClick(driverR, js, actionsBtn);
					Utility.waitForSeconds(2);
					Log.info("Script click on Action Button");
					
					WebElement submitBtn = Utility.waitForElementToBeClickable(driverR, By.xpath("//a[normalize-space()='Submit Timesheet']"), 10);
					Utility.scrollIntoView(driverR, js, submitBtn);
					Utility.showCallout2("Click on Submit Button ", submitBtn);
					Utility.waitForSeconds(1);
//               	Utility.highlightElement(submitBtn);
					Utility.safeClick(driverR, js, submitBtn);
					Utility.waitForSeconds(2);
					Log.info("Script click on Submit Button");

					WebElement confirmSubmit = Utility.waitForElementToBeClickable(driverR, By.xpath("//button[normalize-space()='Submit']"), 10);
					Utility.scrollIntoView(driverR, js, confirmSubmit);
					Utility.showCallout("Click on Confirm Button Using Automation Script", confirmSubmit);
					Utility.highlightElement(confirmSubmit);
					Utility.safeClick(driverR, js, confirmSubmit);
					Utility.waitForSeconds(2);
					System.out.println("✅ Timesheet submitted for week " + 2);

					WebElement confirmMsg = driverR.findElement(By.xpath("//*[contains(text(),'Timesheet submitted successfully!')]"));
					Utility.ExplicitWait(confirmMsg);
					Utility.highlightElement(confirmMsg);
					String ActualTimesheetSuccesful = confirmMsg.getText();
					System.out.println("Timesheet submission Succesful Message :-> " + ActualTimesheetSuccesful);
					String ExpectTimesheetSuccesful ="Timesheet submitted successfully!";
					sf.assertEquals(ActualTimesheetSuccesful, ExpectTimesheetSuccesful);
					Log.info("Timesheet Submitted for this Respective week Successfully");
					// Check final status after submission
					driverR.navigate().refresh();
					Utility.waitForSeconds(2);

					WebElement finalWeek = Utility.waitForElementToBeClickable(driverR, By.xpath(weekXPath), 10);
					Utility.scrollIntoView(driverR, js, finalWeek);
					Utility.safeClick(driverR, js, finalWeek);
					WebElement finalStatus = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class,'bg-warning rounded text-black')]")));
					String newStatus = finalStatus.getText().replace("\u00A0", " ").trim();
					System.out.println("🟢 Post-Submission Status: '" + newStatus + "'");

					if (newStatus.equalsIgnoreCase("SUBMITTED")) {
						System.out.println("✅ Submission confirmed. Stopping further processing.");
						break; // STOP the main loop
					}

				} catch (Exception e) {
					System.out.println("⚠️ Error during final submission: " + e.getMessage());

				}

			}
			
		}
		sf.assertAll();
	}
	
			
			
	@Test(priority=3)
	public void ValidateApproveTimesheetReportFunctionality() throws InterruptedException, IOException
	{
		sf2 = new SoftAssert();
		Utility.showTooltip("Executing Automation Script to Validate Timesheet Approval Functionality by Line Manager.");

		Utility.waitForSeconds(5); 

		launchLocalUrl();

		Utility.waitForSeconds(2);

		Utility.showTooltip("Step 6:->Again Opening Timesheet Application,Now Login as Line Manager to Approve Sent Timesheet Report ");				

		lp.SendAdminUserName2();		
		Log.info("entering Line-manager Credential");

		lp.SendAdminPassword();
		Log.info("entering Line-manager Password");
		
		lp.ClickonLoginBtn();
		Log.info("Line Manager Login ");
		Utility.waitForSeconds(2);

		String ActualProfileName=lp.GetProfileName();
		System.out.println("ActualProfileName :->"+ActualProfileName);
		String ExpectedProfileName ="Welcome, LNM TestuserLINE MANAGER";

		sf2.assertEquals(ActualProfileName, ExpectedProfileName);
		Log.info("Profile Name verified ");
		tsp.ClickonTimesheetApproval();

		List<WebElement> allWeeks = driverR.findElements(By.xpath("(//div[contains(@class,'p-1 shadow mb-2 border-secondary bg-gradient week-item card')])"));
		System.out.println("Total week blocks found: " + allWeeks.size());
		totalWeeks = allWeeks.size();
		System.out.println("Total week size so that required for Loop"+ totalWeeks);
		// API Login

		Utility.showTooltip("Step 7:->After Selecting Respective Week,then select same Timesheet Report Sent by The User using Automation Script ");
		Utility.waitForSeconds(3);
		
		outerloop:
		for (int index = 2; index <= totalWeeks; index++) {
			String weekXPath = "(//div[contains(@class,'p-1 shadow mb-2 border-secondary bg-gradient week')])[" + index + "]";
			WebElement weekElement = driverR.findElement(By.xpath(weekXPath));
			js = (JavascriptExecutor) driverR;
			js.executeScript("arguments[0].scrollIntoView(true);", weekElement);
			Utility.safeClick(driverR, js, weekElement);

			List<WebElement> UserNames = driverR.findElements(
				    By.xpath("//*[@class=\"m-1 p-1 row\"]//div"));
				

				for (int i = 0; i < UserNames.size(); i++) {   // ✅ start at 0, size()-1
				    try {
				        // Re-fetch each time with explicit wait
				        WebDriverWait wait = new WebDriverWait(driverR, Duration.ofSeconds(10));
				        WebElement UserNameElement = wait.until(ExpectedConditions
				            .visibilityOfElementLocated(By.xpath(
				                "//*[@class=\"m-1 p-1 row\"]//div[" + (i + 1) + "]" )));
				        String UserName = UserNameElement.getText();
				        System.out.println("UserName: " + UserName);
				        System.out.println("UserName in Timesheet Page: " + UserName);
				        System.out.println("UserName in Profile Page : " + ActualUserName);

				        if (UserName.equalsIgnoreCase(ActualUserName)) {
				            // ✅ Found matching user → perform actions
				            Utility.showTooltip("Step 8:->After selecting same Timesheet Report, approve the timesheet report to validate approval functionality is working fine.");
				            Utility.safeClick(driverR, js, UserNameElement);

				            WebElement actionsBtn = Utility.waitForElementToBeClickable(driverR, By.xpath("//button[normalize-space()='Actions']"), 10);
				            Utility.scrollIntoView(driverR, js, actionsBtn);
				            Utility.showCallout2("Click on Action Button using Automation Script", actionsBtn);
				            Utility.waitForSeconds(1);
				            Utility.safeClick(driverR, js, actionsBtn);
				            Utility.waitForSeconds(2);
				         
				            
				            WebElement submitBtn = Utility.waitForElementToBeClickable(driverR, By.xpath("//a[normalize-space()='Approve Timesheet']"), 10);
				            Utility.scrollIntoView(driverR, js, submitBtn);
				            Utility.showCallout2("Click on Approve Timesheet using Automation Script", submitBtn);
				            Utility.waitForSeconds(1);
				            Utility.safeClick(driverR, js, submitBtn);
				            Utility.waitForSeconds(2);

				            WebElement comment = Utility.waitForElementToBeClickable(driverR, By.xpath("//textarea[@placeholder='Enter comment']"), 10);
				            Utility.scrollIntoView(driverR, js, comment);
				            Utility.highlightElement(comment);
				            Utility.showCallout("Sending Comment using Automation Script", comment);
				            comment.sendKeys("Approve This Timesheet for Testing");
				            Utility.waitForSeconds(2);

				            WebElement confirmSubmit = Utility.waitForElementToBeClickable(driverR, By.xpath("//button[normalize-space()='Submit']"), 10);
				            Utility.scrollIntoView(driverR, js, confirmSubmit);
				            Utility.showCallout("Clicking on confirm Button using Automation Script", confirmSubmit);
				            Utility.highlightElement(confirmSubmit);
				            Utility.safeClick(driverR, js, confirmSubmit);
				            Utility.waitForSeconds(2);

				            System.out.println("✅ Timesheet submitted for week " + (i + 1));

				            WebElement confirmMsg = driverR.findElement(By.xpath("//*[contains(text(),'Approved successfully!')]"));
				            Utility.highlightElement(confirmMsg);
				            String ActualTimesheetApproveMsg = confirmMsg.getText();
				            System.out.println("Timesheet submission Successf2ul Message :-> " + ActualTimesheetApproveMsg);

				            ExpectTimesheetApproveMsg = "Approved successfully!";
				            sf2.assertEquals(ActualTimesheetApproveMsg, ExpectTimesheetApproveMsg);
				            Log.info("User Timesheet Approved successf2ully by Line Manager");

				            break outerloop; // ✅ Exit loop once done
				        } else {
				            System.out.println("❌ No Timesheet Submitted for User: " + UserName);
				        }
				    } catch (StaleElementReferenceException e) {
				        System.out.println("⚠️ Retrying stale element at index: " + i);
				        try {
				            // ✅ Retry with explicit wait (not just get(i))
				            WebDriverWait wait = new WebDriverWait(driverR, Duration.ofSeconds(5));
				            WebElement retryElement = wait.until(ExpectedConditions
				                .visibilityOfElementLocated(By.xpath(
				                    "(//div[contains(@class,'px-1 py-2 shadow mb-2 bg-gradient submitted card')]//div[@class='col-4'])[" + (i + 1) + "]"
				                )));
				            
				            System.out.println("UserName (retried): " + retryElement.getText());
				        } catch (Exception retryEx) {
				            System.out.println("❌ Retry failed for element at index " + i + ": " + retryEx.getMessage());
				        }
				        
				    }
					
				}
	}
		sf2.assertAll();
	}

	//	}


	public String addHours(String baseTime, int hoursToAdd) {
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalTime time = LocalTime.parse(baseTime, timeFormatter);
		return time.plusHours(hoursToAdd).format(timeFormatter);
	}

	public void sendAttendanceData(String token, String timestamp, String endpoint) {
		HashMap<String, String> data = new HashMap<>();
		data.put("test_timestamp", timestamp);

		System.out.println("→ Sending to " + endpoint + ": " + timestamp);

		given()
		.contentType("application/json")
		.header("Authorization", "Token " + token)
		.body(data)
		.when()
		.post("http://192.168.1.10:8085/api/attendance-test/" + endpoint + "/")
		.then()
		.statusCode(201)
		.log().all();
	}
	public void sendAttendanceData2(String token, String timestamp, String endpoint) {
		HashMap<String, String> data = new HashMap<>();
		data.put("test_timestamp", timestamp);

		System.out.println("→ Sending to " + endpoint + ": " + timestamp);

		given()
		.contentType("application/json")
		.header("Authorization", "Token " + token)
		.body(data)
		.when()
		.patch("http://192.168.1.10:8085/api/attendance-test/" + endpoint + "/")
		.then()
		.statusCode(201)
		.log().all();
	}
	
	public void sendAttendanceData3(String token,String datestr, String timestamp, String endpoint) {
		HashMap<String, String> data = new HashMap<>();
		data.put("action", endpoint);
		data.put("action_date", datestr);
		data.put("action_time", timestamp);

		System.out.println("→ Sending to " + endpoint + ": " + timestamp);

		given()
		.contentType("application/json")
		.header("Authorization", "Token " + token)
		.body(data)
		.when()
		.post("http://192.168.1.10:8085/api/clock-entries/action-test/")
		.then()
		.statusCode(200)
		.log().all();
	}

	public void DeleteAutomationTestUserRecords(String token) {
		given()
		.contentType("application/json")
		.header("Authorization", "Token " + token)
//.body(data)
		.when()
		.post("http://192.168.1.10:8085/api/utils/remove-automation-test-data/")
		.then()
		.statusCode(200)
		.log().all();
	}






	//	@AfterMethod
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



