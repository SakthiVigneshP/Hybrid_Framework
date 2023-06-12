package project.functions;

import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;

import project.common.BasePage;

public class Banker_Functions {
	
	RemoteWebDriver driver;
	BasePage basePage = null;
	Common_Functions commonObj;
	Buyer_Functions buyerObj;
	WebDriverWait wait;
	ExtentTest logger;
	String Test_Start_Time;
	public String testCaseFullName;
	HashMap<String,String> testDataCollection = null;
	

	public Banker_Functions(RemoteWebDriver driver, ExtentTest logger, String test_Start_Time, String testCaseFullName,
			HashMap<String, String> testDataCollection) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
		basePage = new BasePage(driver);
		commonObj = new Common_Functions(driver, logger, Test_Start_Time,test_Start_Time, testDataCollection);
		wait = new WebDriverWait(driver, Duration.ofSeconds(40));
		this.logger = logger;
		this.Test_Start_Time = Test_Start_Time;
		testCaseFullName = testCaseFullName;
		this.testDataCollection = testDataCollection;
		
	}

}
