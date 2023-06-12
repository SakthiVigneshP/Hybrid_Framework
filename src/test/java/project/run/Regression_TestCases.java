package project.run;

import java.util.HashMap;

import java.util.ResourceBundle;

import org.openqa.selenium.remote.RemoteWebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import project.common.BasePage;
import project.common.DataExtraction;
import project.functions.Common_Functions;
import project.run.TestNGRunARD;
import project.functions.Banker_Functions;
import project.functions.Buyer_Functions;
import project.functions.Seller_Functions;
import project.tests.Regression_TestCases_Steps;

public class Regression_TestCases {
	
	
	ResourceBundle config = ResourceBundle.getBundle("config");
	DataExtraction testDataObj = new DataExtraction();
	String testDataPath = System.getProperty("user.dir")+"\\"+config.getString("DATASHEET_PATH");
	String testDataName = config.getString("DATASHEET_NAME");
	
	public void Test_Case_01(ExtentReports report, String Test_Start_Time) throws Exception{
		ExtentTest logger = null;
		RemoteWebDriver driver = null;
		BasePage basePage = null;
		
		Common_Functions commonObj = null;
		TestNGRunARD runObj = new TestNGRunARD();
		HashMap<String, String> testDataCollection = null;
		
		try {
			testDataCollection = new HashMap<String,String>();
			String testCaseName = new Object(){
			}.getClass().getEnclosingMethod().getName();
			System.out.println("Method Name : "+testCaseName);
			
			//testDataCollection = testDataObj.fecthTestData(testDataPath+testDataName, testCaseName);
			testDataCollection.put("FUNCTIONAL_CASE_NAME","Test_Case_01");
			testDataCollection.put("TESTCASE_NAME","Test_Case_01");
			testDataCollection.put("APPLICATION_URL","GOOGLE_URL");
			testDataCollection.put("BROWSER_TYPE","EDGE");
			
			
			logger = report
					.createTest(testDataCollection.get("FUNCTIONAL_CASE_NAME"), testDataCollection.get("TESTCASE_NAME"))
					.assignCategory("Regression");
			
			String testCaseFullName = testDataCollection.get("FUNCTIONAL_CASE_NAME");
			
			basePage = new BasePage(driver,testDataCollection, Test_Start_Time);
			driver = basePage.navigateURL();
			
			Buyer_Functions buyerObj = new Buyer_Functions(driver, logger, Test_Start_Time, testCaseFullName,
					testDataCollection);
			
			Seller_Functions sellerObj = new Seller_Functions(driver, logger, Test_Start_Time, testCaseFullName,
					testDataCollection);
			commonObj = new Common_Functions(driver,logger,Test_Start_Time, testCaseFullName, testDataCollection);
			
			Banker_Functions bankerObj = new Banker_Functions(driver, logger,Test_Start_Time,testCaseFullName,testDataCollection);
			
			Regression_TestCases_Steps regTestObj = new Regression_TestCases_Steps();
			
			regTestObj.Test_Case_01(driver, testDataCollection, bankerObj, sellerObj, buyerObj, commonObj, basePage);
			
			
		}catch(Exception e) {
			e.printStackTrace();
			if(e.getMessage().equalsIgnoreCase("BROWSER_EXCEPTION")) {
				logger.log(Status.FAIL, "Exception in Launch Browser - Browser is not lauched");
			}else {
				commonObj.reports(driver, "Test_Case_01 Unsuccessfull", Status.FAIL, "Test_Case_01 Unsuccessfull");
				
			}
		}finally {
			report.flush();
			runObj.updateQuery(testDataCollection.get("TESTCASE_NAME"), logger.getStatus().toString());
			driver.close();
		
		}
		
	}
	
	
}
