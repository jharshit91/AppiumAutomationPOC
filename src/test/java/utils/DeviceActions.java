package utils;

import java.io.File;
import java.util.List;

import javax.swing.Action;

import loggers.Log;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import testData.TestConditions.Direction;
import infra.LocalizedTestBase;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;

public class DeviceActions extends LocalizedTestBase{
	AppiumDriver<MobileElement> driver;

	public DeviceActions(AppiumDriver<MobileElement> driver) {
		this.driver = driver;
	}

	public void navigateBack() {
		driver.navigate().back();
	}

	public void setTextFieldValue(MobileElement element, String value) {
		element.click();
		element.setValue(value);
		hideKeyboard();
	}

	public void waitTime(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException ex) {
			Log.INFO(ex.getMessage());
		}
	}

	public MobileElement waitForElement(MobileElement element, int timeout) {

		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.visibilityOf(element));
		return element;
	}

	public Boolean clickifDisplayed(MobileElement element, int timeout) {
		try {
			Boolean flag = waitForElement(element, timeout).isDisplayed();
			System.out.println("Element" + element.toString() + " visibility :"
					+ flag);
			if (flag) {
				element.click();
				return true;
			}
		} catch (TimeoutException ex) {
			Log.INFO("Element " + element.toString()
					+ " is not availble on the screen");
		}
		return false;
	}

	public void hideKeyboard() {
		try {
			driver.hideKeyboard();
		} catch (Exception ex) {
		}
	}

	public void verticalSwip(MobileElement scrollableWindowFrame,
			Direction direction) {
		TouchAction action = new TouchAction(driver);
		int yCoordinatetop = (int) (scrollableWindowFrame.getSize().getHeight() * (0.20));
		int yCoordinateBottom = (int) (scrollableWindowFrame.getSize()
				.getHeight() * (0.70));
		int xCoordinate = scrollableWindowFrame.getSize().getWidth() / 2;

		Log.INFO("x:" + xCoordinate + "  y1:" + yCoordinatetop + "  y2:"
				+ yCoordinateBottom);

		if (direction.equals(Direction.UP)) {
			action.longPress(xCoordinate, yCoordinateBottom)
					.moveTo(xCoordinate, yCoordinatetop).release().perform();
		} else if (direction.equals(Direction.DOWN)) {
			action.longPress(xCoordinate, yCoordinatetop)
					.moveTo(xCoordinate, yCoordinateBottom).release().perform();
		}

	}

	public Boolean scrollAndClick(MobileElement element,
			MobileElement scrollableWindowFrame, Direction direction,
			int maxscroll) {
		while (maxscroll > 1) {
			verticalSwip(scrollableWindowFrame, direction);
			Log.INFO("maxScroll count :" + maxscroll);
			if ((clickifDisplayed(element, 3) == true)) {
				return true;
			}
			maxscroll--;
		}
		return false;
	}

	public String snapShot(String Screen, String ScreenType, Boolean uiTest) {

		String filePath=null;
		String deviceSerial = driver.getCapabilities().getCapability("udid")
				.toString();
		Log.INFO("Taking Spanshot of " + deviceSerial);
		File srcFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		try {

			filePath = System.getProperty("user.dir") + File.separator
					+ "screenshots" + File.separator + deviceSerial
					+ File.separator + Screen + ".png";

			Log.INFO(filePath);
			FileUtils.copyFile(srcFile, new File(filePath));
		} catch (Exception ex) {
			Log.INFO("Unable to capture snapshot");
			Log.INFO(ex.getMessage());
		}

		return filePath;
	}

	public Boolean isElementAvailableOnScreen(List<MobileElement> elementList,
			MobileElement scrollview, String text, int maxswipeOnSccreen,
			Direction direction) {

		for (int i = 0; i < maxswipeOnSccreen; i++) {
			for (MobileElement element : elementList) {
				Log.INFO(element.getText().trim()+" is comparing with"+text.trim());
				if (element.getText().trim().equals(text.trim())) {
					logTestMessage("Verified the "+ text+" is available on the screen");
					
					return true;
				}
			}
			verticalSwip(scrollview, direction);
		}
		return false;
	}
}
