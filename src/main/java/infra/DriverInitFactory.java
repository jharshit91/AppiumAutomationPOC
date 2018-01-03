package infra;

import java.net.MalformedURLException;
import java.net.URL;

import loggers.Log;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

public class DriverInitFactory {

	private static ThreadLocal<AppiumDriver<MobileElement>> localizedDriver = new ThreadLocal<AppiumDriver<MobileElement>>();
	private AppiumDriver<MobileElement> driver = null;
	private DesiredCapabilities caps = new DesiredCapabilities();

	public AppiumDriver<MobileElement> invokeDriver(String deviceName,
			String platformName, String platformVersion, String udid,String port) {
		if (getDriverInstance() == null) {
			try {
				caps.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
				caps.setCapability(MobileCapabilityType.PLATFORM_NAME,
						platformName);
				caps.setCapability(MobileCapabilityType.PLATFORM_VERSION,
						platformVersion);
				caps.setCapability(MobileCapabilityType.NO_RESET, true);
				caps.setCapability(MobileCapabilityType.UDID, udid);
				caps.setCapability(MobileCapabilityType.APP, Constants.appPath);
				caps.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,Constants.appActivity);
				caps.setCapability(AndroidMobileCapabilityType.APP_PACKAGE,Constants.appPackage);
				caps.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
				caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, Constants.automationName);
				caps.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT, port);
				driver = new AndroidDriver<MobileElement>(new URL(
						Constants.serverAddress), caps);
				setAppiumDriver(driver);
				return driver;
			} catch (MalformedURLException ex) {
				Log.ERROR(ex.getMessage());
			}
		}
		return getDriverInstance();
	}

	public void setAppiumDriver(AppiumDriver<MobileElement> driver) {
		localizedDriver.set(driver);
	}

	public AppiumDriver<MobileElement> getDriverInstance() {
		return localizedDriver.get();
	}

	public String getCapability(String capabilityName){
		return getDriverInstance().getCapabilities().getCapability(capabilityName).toString();
	}
	
	public void quitDriverInstance() {
		if (getDriverInstance() != null) {
			getDriverInstance().quit();
		}
	}

}