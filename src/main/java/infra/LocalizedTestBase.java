package infra;

import java.io.File;
import java.util.UUID;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

import utils.DeviceActions;
import loggers.Log;
import ReportConfiguration.ExtentManager;
import ReportConfiguration.ExtentTestManager;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class LocalizedTestBase extends DriverInitFactory {

	public static ExtentReports extent;
	public ExtentTest test;
	String FilePath;
	boolean flag;

	private static ThreadLocal<ExtentTest> localtest = new ThreadLocal<ExtentTest>();

	public ExtentTest getTest() {
		return localtest.get();
	}

	public void setTest(ExtentTest test) {
		localtest.set(test);

	}

	public void testStart() {
		System.out.println("Inside test start");
		test = (ExtentTestManager.startTest(getCapability("deviceName")));
		setTest(test);

	}

	public void logTestMessage(String message) {
		try {
			getTest().log(LogStatus.INFO,
					message + " : " + getCapability("deviceName"));
		} catch (Exception ex) {
			Log.INFO(ex.getMessage());
		}
	}


	public void logFailedTestMessage(String message) {
		try {
			getTest().log(LogStatus.FAIL,
					message + " : " + getCapability("deviceName"));
		} catch (Exception ex) {
			Log.INFO(ex.getMessage());
		}
	}
	
	
	/**
	 * Tear down the test logging for particular driver instance.
	 */
	public void endTest() {

		ExtentTestManager.endTest();

	}

	public void logScreenCapture(String screenName) {
		try {
			FilePath = new DeviceActions(getDriverInstance()).snapShot(
					screenName, "", false);
			getTest().log(LogStatus.INFO,
					"Screenshot" + getTest().addScreenCapture(FilePath));
		} catch (Exception ex) {
			Log.INFO(ex.getMessage());
		}
	}

	public void logScreenCapture(String message, String screenName) {
		try {
			FilePath = new DeviceActions(getDriverInstance()).snapShot(
					screenName, "", false);
			getTest().log(
					LogStatus.INFO,
					"Screenshot and Desc:" + message
							+ getTest().addScreenCapture(FilePath));
		} catch (Exception ex) {
			Log.INFO(ex.getMessage());
		}
	}

	public void makeScreenshotDirectory(String serial, String screenName) {
		File file = new File(System.getProperty("user.dir") + File.separator
				+ "screenshots" + File.separator + serial + File.separator
				+ screenName);
		if (!file.exists()) {
			if (file.mkdir()) {
				Log.INFO("Dir created for " + serial + " to compare  "
						+ screenName);

			} else {
				Log.INFO("Directory alreadyCreated");
			}
		}
	}

	@AfterMethod
	public void afterTest(ITestResult result) {
		try {

			if (!result.isSuccess()) {
				
				logFailedTestMessage("Test Failed due to" + result.getThrowable());
				logScreenCapture("FailedTestScreenshot");
				UUID uuid = UUID.randomUUID();
				logScreenCapture(uuid.toString());
			}
			ExtentManager.getReporter().endTest(test);
			ExtentManager.getReporter().flush();
		}

		catch (Exception ex) {
			Log.INFO("Exception occured" + ex.getMessage());
		}
	}

	public Boolean returnFilePath(String filepath, String serial,
			String screenName) {
		File file = new File(System.getProperty("user.dir") + File.separator
				+ "screenshots" + File.separator + serial + File.separator
				+ filepath);
		if (!file.exists()) {
			return true;
		} else {
			return false;
		}
	}

}
