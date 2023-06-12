package project.functions;

import java.util.HashMap;

import org.openqa.selenium.remote.RemoteWebDriver;

import com.aventstack.extentreports.ExtentTest;

import project.common.BasePage;

public class Buyer_Functions {
	RemoteWebDriver driver;	
	BasePage basePage;
	Common_Functions commonObj;

	public Buyer_Functions(RemoteWebDriver driver, ExtentTest logger, String Test_Start_Time, String TCName,
			HashMap<String, String> testDataCollection) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
		basePage = new BasePage(driver);
		commonObj = new Common_Functions(driver,logger,Test_Start_Time, TCName, testDataCollection);
		
		
		
	}

}
