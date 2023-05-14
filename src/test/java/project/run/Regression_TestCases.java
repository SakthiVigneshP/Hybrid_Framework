package project.run;

import java.util.ResourceBundle;

import org.openqa.selenium.remote.RemoteWebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import project.common.BasePage;
import project.common.DataExtraction;
import project.functions.Common_Functions;
import project.run.TestNGRunARD;

public class Regression_TestCases {
	
	
	ResourceBundle config = ResourceBundle.getBundle("resouces.data.config");
	DataExtraction testDataObj = new DataExtraction();
	String testDataPath = config.getString("DATASHEET_PATH");
	String testDataName = config.getString("DATASHEET_NAME");
	
	public void Test_Case_01(ExtentReports report, String Test_Start_Time) throws Exception{
		ExtentTest logger = null;
		RemoteWebDriver driver = null;
		BasePage basePage = null;
		
		Common_Functions commonObj = null;
		TestNGRunARD runObj = new TestNGRunARD();
	}
	
	
}
