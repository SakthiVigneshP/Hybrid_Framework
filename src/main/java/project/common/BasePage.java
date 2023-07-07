package project.common;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class BasePage {
	
	protected RemoteWebDriver driver;
	String Test_Start_Time;
	HashMap<String, String> testDataCollection;
	ResourceBundle config = ResourceBundle.getBundle("config");
	String testCaseNumber = null;
	
	public BasePage(RemoteWebDriver driver, HashMap<String, String> testDataCollection, String test_Start_Time) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
		this.testDataCollection = testDataCollection;
		this.Test_Start_Time = test_Start_Time;
	}

	public BasePage(RemoteWebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
	}

	public BasePage() {
		// TODO Auto-generated constructor stub
		System.out.println("Non parameterized contructors");
	}

	public void RegisterHub() {
		try {
			System.out.println("Starting: Registering Nodes to Hub");
			
			
		}catch(Exception e) {
			
		}
	}
	
	public RemoteWebDriver navigateURL() throws Exception{
		
		try {
			String appURL = testDataCollection.get("APPLICATION_URL");
			String browserType = testDataCollection.get("BROWSER_TYPE");
			
			System.out.println("URL : "+appURL);
			
			String strURL = "";
			
			switch(appURL)
			{
			case "GOOGLE_URL":
				strURL = config.getString("GOOGLE_URL");
				break;
				
			default:
				System.out.println(appURL + " Invalid URL");
				return null;
			}
			
			driver = launchBrowser(strURL, browserType);
		}catch(Exception e) {
			e.printStackTrace();
			throw new Exception("Browser Exception");
		}
		return driver;
	}
	
	public RemoteWebDriver launchBrowser(String applicationURL, String browserType) throws Exception{
		
		RemoteWebDriver tempDriver = null;
		String port = config.getString("SELENIUM_GRID_PORT");
		String runOption = config.getString("RUN_TYPE");
		
		DesiredCapabilities capabilities = null;
		
		try {
			switch(browserType)
			{
			case "CHROME":
			{
				ChromeOptions options = new ChromeOptions();
				
				testCaseNumber = testDataCollection.get("TESTCASE_NAME");
				String downloadPath = System.getProperty("user.dir")+"\\test-output\\TestResults\\"+Test_Start_Time
						+"\\download\\" + testCaseNumber;
				System.out.println("Download File Path : "+downloadPath);
				
				HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
				
				chromePrefs.put("profile.default_content_settings.popups", 0);
				
				chromePrefs.put("download.default_directory", downloadPath);
				
				options.setExperimentalOption("prefs", chromePrefs);
				
				options.setExperimentalOption("useAutomationExtension", false);
				
				options.addArguments("--start-maximized");
				options.addArguments("--no-sandbox");
				options.addArguments("--incognito");
				options.addArguments("--disable-dev-shm-usage");
				
				
				
				if(config.getString("ChromeDriver_Path").contains("Program")) {
					System.setProperty("webdriver.chrome.driver", config.getString("ChromeDriver_Path"));
				}else {
					System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\"+config.getString("ChromeDriver_Path"));
				}
				
			/*	capabilities.setCapability(ChromeOptions.CAPABILITY, options);
				capabilities.setCapability("browserName", "chrome");
				capabilities.setCapability("ignoreZoomSetting", true);
				capabilities.setCapability("applicationCacheEnabled", false);
				capabilities.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT,true);
				capabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
				//capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, false);
				capabilities.setCapability("applicationCacheEnabled", false);
				options.merge(capabilities);
			*/
				int attemptNo = 1;
				boolean exception = false;
				
				do {
					try {
						tempDriver = openBrowser(tempDriver, options, runOption, port, browserType);
						tempDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						
						tempDriver.get(applicationURL);
						
						this.driver = tempDriver;
						
						tempDriver.manage().window().maximize();
						
						exception = false;
					}catch(Exception e) {
						e.printStackTrace();
						exception = true;
						attemptNo = attemptNo +1;
					}
				}
				while((attemptNo<10) && ((tempDriver == null) || (exception)));
				break;
			}
			case "EDGE":
			{
				EdgeOptions options = new EdgeOptions();
				
				testCaseNumber = testDataCollection.get("TESTCASE_NAME");
				String downloadPath = System.getProperty("user.dir")+"\\test-output\\TestResults\\"+Test_Start_Time
						+"\\download\\" + testCaseNumber;
				System.out.println("Download File Path : "+downloadPath);
				
				HashMap<String, Object> edgePrefs = new HashMap<String, Object>();
				
				edgePrefs.put("profile.default_content_settings.popups", 0);
				
				edgePrefs.put("download.default_directory", downloadPath);
				
				options.setExperimentalOption("prefs", edgePrefs);
				
				options.setExperimentalOption("useAutomationExtension", false);
				
				options.addArguments("--start-maximized");
				options.addArguments("--no-sandbox");
				options.addArguments("--incognito");
				options.addArguments("--disable-dev-shm-usage");
				
				
				
				if(config.getString("EdgeDriver_Path").contains("Program")) {
					System.setProperty("webdriver.edge.driver", config.getString("EdgeDriver_Path"));
				}else {
					System.setProperty("webdriver.edge.driver", System.getProperty("user.dir")+config.getString("EdgeDriver_Path"));
				}
				
				//capabilities.setCapability(EdgeOptions.CAPABILITY, options);
	/*			capabilities.setCapability("browserName", "edge");
				capabilities.setCapability("ignoreZoomSetting", true);
				capabilities.setCapability("applicationCacheEnabled", false);
				capabilities.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT,true);
				capabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
				//capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, false);
				capabilities.setCapability("applicationCacheEnabled", false);
				options.merge(capabilities);
			*/
				
				int attemptNo = 1;
				boolean exception = false;
				
				do {
					try {
						tempDriver = openBrowser(tempDriver, options, runOption, port, browserType);
						tempDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						
						tempDriver.get(applicationURL);
						
						this.driver = tempDriver;
						
						tempDriver.manage().window().maximize();
						
						exception = false;
					}catch(Exception e) {
						e.printStackTrace();
						exception = true;
						attemptNo = attemptNo +1;
					}
				}
				while((attemptNo<10) && ((tempDriver == null) || (exception)));
				break;
			}
			}
			
			
			
			if(driver.findElements(By.partialLinkText("Continue to this website (not recommended).")).size()!=0) {
				driver.findElement(By.partialLinkText("Continue to this website (not recommended).")).click();
			}else
			{
				System.out.println("Continue to this website (not recommended)  not displayed");
			}
	}
		catch(Exception e) {
			e.printStackTrace();
			throw new Exception("BROWSER_EXCEPTION");
			}
		return tempDriver;
	}
	
	public RemoteWebDriver openBrowser(RemoteWebDriver tempDriver, ChromeOptions options, String runOption, String port,String browser_type) {
		
		int attemptNo = 1;
		boolean exception = false;
		try {
			
			do {
				try
				{
					switch(runOption)
					{
					case "Local" :
					{
						switch(browser_type)
						{
						case "CHROME":
							tempDriver = new ChromeDriver(options);
							break;
						case "IE":
							tempDriver = new InternetExplorerDriver();
							break;
						case "EDGE":
							tempDriver = new EdgeDriver();
							break;
						default: System.out.println("Invalid browser option");
						}
						break;
					}
					case "Grid" :
						tempDriver = new RemoteWebDriver(new URL("http://localhost:"+port+"/wd/hub"),options);
						break;
					default: System.out.println("Invalid Run Options");
					break;
				}
					System.out.println("Browser launched in attempt: "+attemptNo);
					exception = false;
			}catch(Exception e) {
				System.out.println("Issues in lauching browser");
				System.out.println("Browser Not launched in attempt: "+attemptNo);
				e.printStackTrace();
				exception = true;
				attemptNo = attemptNo + 1;
			}
		}while((attemptNo <10) && ((tempDriver == null) || (exception)));
	}catch(Exception e) {
		System.out.println("Exception in opening browser");
		e.printStackTrace();
	}
		return tempDriver;
	}
	
public RemoteWebDriver openBrowser(RemoteWebDriver tempDriver, EdgeOptions options, String runOption, String port,String browser_type) {
		
		int attemptNo = 1;
		boolean exception = false;
		try {
			
			do {
				try
				{
					switch(runOption)
					{
					case "Local" :
					{
						switch(browser_type)
						{
						case "CHROME":
							//tempDriver = new ChromeDriver(options);
							break;
						case "IE":
							tempDriver = new InternetExplorerDriver();
							break;
						case "EDGE":
							tempDriver = new EdgeDriver(options);
							break;
						default: System.out.println("Invalid browser option");
						}
						break;
					}
					case "Grid" :
						tempDriver = new RemoteWebDriver(new URL("http://localhost:"+port+"/wd/hub"),options);
						break;
					default: System.out.println("Invalid Run Options");
					break;
				}
					System.out.println("Browser launched in attempt: "+attemptNo);
					exception = false;
			}catch(Exception e) {
				System.out.println("Issues in lauching browser");
				System.out.println("Browser Not launched in attempt: "+attemptNo);
				e.printStackTrace();
				exception = true;
				attemptNo = attemptNo + 1;
			}
		}while((attemptNo <10) && ((tempDriver == null) || (exception)));
	}catch(Exception e) {
		System.out.println("Exception in opening browser");
		e.printStackTrace();
	}
		return tempDriver;
	}

}
