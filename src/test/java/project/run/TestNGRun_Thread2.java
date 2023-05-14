package project.run;

import org.testng.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.ArrayList;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import project.common.BasePage;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Recordset;


public class TestNGRun_Thread2 {
	DateFormat dateFormat = new SimpleDateFormat("ddMMyy_HHmmss");
	Date date;
	
	private ExtentSparkReporter reportHTML;
	ExtentReports report = new ExtentReports();
	String testCaseName = null, testDataPath = null, testDataName=null,Test_Start_Time = null;
	ResourceBundle config = ResourceBundle.getBundle("resources.data.config");
	BasePage basePageObj = new BasePage();
	
	

  @Test
  public void executeTest() {
	  try {
	  ArrayList<String> testDetails = new ArrayList<String>();
	  ArrayList<String> failTestDetails = new ArrayList<String>();
	  String strQuery = null;
	  
	  strQuery = "Select TESTCASE_NO from EXECUTION where RUN_TEST='Y'";
	  testDetails = executeQuery(strQuery);
	  
	  
	  System.out.println("====================First Run Test Case Details=================");
	  invokeTestCaseMethods(testDetails);
	  
	  int requeueLimit = Integer.parseInt(config.getString("RE_RUN_LIMIT"));
	  String autoReRunCases = config.getString("AUTO_RE_RUN_FAILED_CASES");
	  
	  if(autoReRunCases.equalsIgnoreCase("YES")) {
		  System.out.println("===================Re-Run Test Case Details===================");
		  
		  for(int i=1;i<=requeueLimit;i++) {
			  failTestDetails = new ArrayList<String>();
			  strQuery = "Select TESTCASE_NO from EXECUTION where RUN_STATUS='fail' and RUN_TEST='Y' and REQUEUE_STATUS='YES'";
			  failTestDetails = executeQuery(strQuery);
			  invokeTestCaseMethods(failTestDetails);
		  }
	  }
	  
	  }catch(Exception e){
		  e.printStackTrace();
		  
	  }
	  
	  
	  
  }
  @BeforeMethod
  public void beforeMethod(Method method) {
	  testCaseName = method.getName();
	  System.out.println("Test Case : "+testCaseName);
  }

  @AfterMethod
  public void afterMethod() {
	  try {
		  System.out.println("in after method task kill drivers/browsers:Thread 2");
		  
		  Runtime.getRuntime().exec("taskkill /F /IM Iexplore.exe");
		  Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
		  Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
		  Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
	  }catch(Exception e) {
		  System.out.println("Exception in after method");
		  e.printStackTrace();
	  }
  }

  @BeforeSuite
  public void beforeSuite() {
	  
	  try {
		  //Creating HTML Report file
		  date = new Date();
		  Test_Start_Time = dateFormat.format(date);
		  
		  reportHTML = new ExtentSparkReporter(System.getProperty("user.dir")+ "\\test-output\\TestResults"+Test_Start_Time+".html");
		  //reportHTML.config().setResourceCDN();
		  report.attachReporter(reportHTML);
		  
		  String runOption = config.getString("RUN_TYPE");
		  if(runOption.equalsIgnoreCase("GRID")) {
			  System.out.println("Executing in Selenium GRID");
			  basePageObj.RegisterHub();
		  }else{
			  System.out.println("Executing in Local");
			  
		  }
		  
	  }catch(Exception e) {
		  e.printStackTrace();
		  
	  }
  }

  @AfterSuite
  public void afterSuite() {
  }
  
  public ArrayList<String> executeQuery(String strQuery){
	  
	  testDataPath = config.getString("DATASHEET_PATH");
	  testDataName = config.getString("DATASHEET_NAME_THREAD2");
	  ArrayList<String> testDetails = new ArrayList<String>();
	  
	  try {
		  System.out.println("Data Sheet : "+testDataPath+testDataName);
		  Fillo fillo = new Fillo();
		  Connection connection = fillo.getConnection(testDataPath+testDataName);
		  Recordset recordSet = connection.executeQuery(strQuery);
		  
		  if(recordSet.getCount()>0) {
			  while(recordSet.next()) {
				  testDetails.add(recordSet.getField("TESTCASE_NO").toString());
			  }
		  }else {
			  System.out.println("No records found for query : "+strQuery);
		  }
		  recordSet.close();
		  connection.close();
	  }catch(Exception e) {
		  e.printStackTrace();
	  }
	  return testDetails;
	  
  }
  
  public void invokeTestCaseMethods(ArrayList<String> testCaseDetails) {
	  
	  for(String tc : testCaseDetails) {
		  String methodName = tc;
		  Class<?> cls = null;
		  Object instance = null;
		  Method method = null;
		  
		  try {
			  String caseOption = config.getString("CASE_TYPE");
			  if(caseOption.equalsIgnoreCase("ERT")) {
				  cls = Class.forName("project.run.ERT_TestCases");
			  }else {
				  cls = Class.forName("project.run.Regression_TestCases");
			  }
			  
			  instance = cls.newInstance();
			  method = cls.getDeclaredMethod(methodName, ExtentReports.class, String.class);
			  System.out.println(method.getName());
			  method.invoke(instance, report, Test_Start_Time);
			  
			  
		  }catch(ClassNotFoundException e) {
			  e.printStackTrace();
		  } catch(InstantiationException e) {
			  e.printStackTrace();
		  } catch(IllegalAccessException e) {
			  e.printStackTrace();
		  } catch(NoSuchMethodException e) {
			  e.printStackTrace();
		  } catch(SecurityException e) {
			  e.printStackTrace();
		  } catch(IllegalArgumentException e) {
			  e.printStackTrace();
		  } catch(InvocationTargetException e) {
			  e.printStackTrace();
		  }
	  }
  }

}
