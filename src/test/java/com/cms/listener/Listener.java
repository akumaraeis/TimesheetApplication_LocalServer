package com.cms.listener;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.cms.utility.Log;
import com.cms.utility.Utility;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Listener implements ITestListener {

/*
	ExtentSparkReporter sparkReporter;
	ExtentReports extent;
	ExtentTest test;
	WebDriver driverR; // Assume driver is initialized in your Base class or Test class

	// Initialize the report
	@Override
	public void onStart(ITestContext context) {
		String timestamp2 = new SimpleDateFormat("yyyy.MM.dd.HH.mm").format(new Date());
		String repName = "ExtentReport-"+timestamp2+".html";
		String reportPath = System.getProperty("user.dir") + "/ExtentReport/"+repName;
		sparkReporter = new ExtentSparkReporter(reportPath);
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Machine1", "Testpc1");
		extent.setSystemInfo("Operating system", "Windows 11 pro");
		extent.setSystemInfo("Browser", "Chrome");
		extent.setSystemInfo("Environment", "QA Environment");
		extent.setSystemInfo("Host", "QA");
		extent.setSystemInfo("UserName", "Amit kumar");

	}

	@Override
	public void onTestStart(ITestResult result) {
		test = extent.createTest(result.getMethod().getMethodName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		test.log(Status.PASS, "Test Passed");
		test.addScreenCaptureFromPath(Utility.captureScreenshot(result.getMethod().getMethodName(), "PASS"));
	}

	@Override
	public void onTestFailure(ITestResult result) {
		test.log(Status.FAIL, "Test Failed: " + result.getThrowable());
		test.addScreenCaptureFromPath(Utility.captureScreenshot(result.getMethod().getMethodName(), "FAIL"));
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		test.log(Status.SKIP, "Test Skipped: " + result.getThrowable());
		test.addScreenCaptureFromPath(Utility.captureScreenshot(result.getMethod().getMethodName(), "SKIP"));
	}

	@Override
	public void onFinish(ITestContext context) {
		extent.flush(); // Write everything to the report
	}

	// Utility method to capture screenshots

}

	*/
	
	    private ExtentSparkReporter sparkReporter;
	    private ExtentReports extent;
	    private ExtentTest test;

	    @Override
	    public void onStart(ITestContext context) {
	        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
	        String reportName = "ExtentReport-" + timestamp + ".html";
	        String reportPath = System.getProperty("user.dir") + "/ExtentReport/" + reportName;

	        sparkReporter = new ExtentSparkReporter(reportPath);
	        sparkReporter.config().setDocumentTitle("Automation Test Report");
	        sparkReporter.config().setReportName("Regression Suite");
	        sparkReporter.config().setTheme(Theme.STANDARD);

	        extent = new ExtentReports();
	        extent.attachReporter(sparkReporter);

	        extent.setSystemInfo("OS", "Windows 11 Pro");
	        extent.setSystemInfo("Browser", "Chrome");
	        extent.setSystemInfo("Environment", "QA");
	        extent.setSystemInfo("User", "Amit Kumar");
	    }

	    @Override
	    public void onTestStart(ITestResult result) {
	        String fullTestName = result.getTestClass().getName() + " : " + result.getMethod().getMethodName();
	        test = extent.createTest(fullTestName);
	        test.log(Status.INFO, "Test Started: " + fullTestName);
	        Log.info("==== Starting test: " + result.getName() + " ====");
	    }

	    @Override
	    public void onTestSuccess(ITestResult result) {
	        test.log(Status.PASS, "✅ Test Passed");

	        String screenshotPath = Utility.captureScreenshot(result.getMethod().getMethodName(), "PASS");
			if (screenshotPath != null) {
			    test.addScreenCaptureFromPath(screenshotPath);
			    Log.info("==== Test Passed: " + result.getName() + " ====");
			}
	    }

	    @Override
	    public void onTestFailure(ITestResult result) {
	        test.log(Status.FAIL, "❌ Test Failed: " + result.getThrowable());

	        String screenshotPath = Utility.captureScreenshot(result.getMethod().getMethodName(), "FAIL");
			if (screenshotPath != null) {
			    test.addScreenCaptureFromPath(screenshotPath);
			    Log.error("==== Test Failed: " + result.getName() + " ====");
			}
	    }

	    @Override
	    public void onTestSkipped(ITestResult result) {
	        test.log(Status.SKIP, "⚠ Test Skipped: " + result.getThrowable());

	        String screenshotPath = Utility.captureScreenshot(result.getMethod().getMethodName(), "SKIP");
			if (screenshotPath != null) {
			    test.addScreenCaptureFromPath(screenshotPath);
			}
	    }

	    @Override
	    public void onFinish(ITestContext context) {
	        extent.flush();
	    }
	}

	
	
	