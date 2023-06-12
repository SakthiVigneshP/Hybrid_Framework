package project.functions;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import org.testng.Assert;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import project.common.BasePage;

public class Common_Functions {
	RemoteWebDriver driver;
	BasePage basePage;
	WebDriverWait wait;
	ExtentTest logger;
	String Test_Start_Time;
	public String testCaseFullName;
	HashMap<String,String> testDataCollection = null;
	public static boolean postRequisticFlag = false;
	

	public Common_Functions(RemoteWebDriver driver, ExtentTest logger, String Test_Start_Time, String tcName,
			HashMap<String, String> testDataCollection) {
		// TODO Auto-generated constructor stub
		
		this.driver = driver;
		basePage = new BasePage(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(40));
		this.logger = logger;
		this.Test_Start_Time = Test_Start_Time;
		this.testCaseFullName = tcName;
		this.testDataCollection = testDataCollection;	
		
		
	}
	
	public void reports(WebDriver driver, String description, Status testStatus, String stepDetails) {
		String screenshotFileName = null, methodStatus = null;
		
		try {
			System.out.println("log Status : "+testStatus);
			
			if(testStatus.equals(Status.PASS)) {
				methodStatus = "PASS";
			}else if(testStatus.equals(Status.FAIL)) {
				methodStatus = "FAIL";
			}else if(testStatus.equals(Status.FAIL)) {
				methodStatus = "ERROR";
			}else if(testStatus.equals(Status.INFO)) {
				methodStatus = "INFO";
			}else if(testStatus.equals(Status.WARNING)) {
				methodStatus = "WARNING";
			}else {
				methodStatus = "FATAL";
				
			}
			stepDetails = stepDetails.replace(";", "");
			
			switch(methodStatus) {
			
			case "PASS":{
				screenshotFileName = capture(driver, Test_Start_Time, stepDetails).trim();
				logger.log(Status.PASS, description);
				logger.pass("PASS", MediaEntityBuilder.createScreenCaptureFromPath(screenshotFileName).build());
				break;
			}
			
			case "FAIL":{
				screenshotFileName = capture(driver, Test_Start_Time, stepDetails).trim();
				logger.log(Status.FAIL, description);
				logger.fail("FAIL", MediaEntityBuilder.createScreenCaptureFromPath(screenshotFileName).build());
				if(postRequisticFlag == true) {
					System.out.println("Post Requistic Flag is On");
					post_RequisticMethod();
				}
				postRequisticFlag = false;
				org.testng.Assert.fail(description);
				
				break;
			}
			case "ERROR":{
				screenshotFileName = capture(driver, Test_Start_Time, stepDetails).trim();
				logger.log(Status.FAIL, description);
				logger.fail("ERROR", MediaEntityBuilder.createScreenCaptureFromPath(screenshotFileName).build());
				org.testng.Assert.fail(description);
				
				break;
			}
			case "INFO":{
				
				logger.log(Status.INFO, description+"----"+stepDetails);
				
				break;
			}
			case "WARNING":{
				screenshotFileName = capture(driver, Test_Start_Time, stepDetails).trim();
				logger.log(Status.WARNING, description);
				logger.warning("WARNING", MediaEntityBuilder.createScreenCaptureFromPath(screenshotFileName).build());				
				break;
			}
			case "FATAL":{
				screenshotFileName = capture(driver, Test_Start_Time, stepDetails).trim();
				logger.log(Status.FAIL, description);
				logger.fail("FATAL", MediaEntityBuilder.createScreenCaptureFromPath(screenshotFileName).build());				
				break;
			}
			default:{
				System.out.println("Invalid Log Status");
			}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void post_RequisticMethod() {
		// TODO Auto-generated method stub
		
	}

	public String capture(WebDriver driver, String Test_Start_Time, String screenShotName) throws IOException{
		String fileName = null, path = null, filePath = null, testCaseNumber = null;
		
		try {
			DateFormat dateFormat = new SimpleDateFormat("ddMMyy_HHmmss");
			Date date = new Date();
			
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			fileName = screenShotName+"_"+dateFormat.format(date) + ".png";
			testCaseNumber = testDataCollection.get("TESTCASE_NAME");
			
			filePath = "\\test-output\\TestResults\\"+Test_Start_Time+"\\images\\"+ testCaseNumber +"\\"+fileName;
			path = System.getProperty("user.dir")+filePath;
			
			File destination = new File(path);
			FileUtils.copyFile(source, destination);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "images\\"+testCaseNumber +"\\"+fileName;
		
	}
}
