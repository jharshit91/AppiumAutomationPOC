package tests;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import ReportConfiguration.ExtentManager;
import pages.BasePageScreen;
import testData.HomeScreenTabs;
import testData.TestConditions;
import utils.DeviceActions;
import infra.DriverInitFactory;
import infra.LocalizedTestBase;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class ProductListTest extends LocalizedTestBase {
	private AppiumDriver<MobileElement> driver;
	DriverInitFactory factory;

	@BeforeSuite
	public void suitSetup() {
		extent = ExtentManager.getReporter(new File("").getAbsolutePath()
				+ File.separator + "reports" + File.separator
				+ "ExtentReport.html");
	}

	@Parameters({ "deviceName", "platformName", "platformVersion", "udid",
			"port" })
	@BeforeClass
	public void configuration(String deviceName, String platformName,
			String platformVersion, String udid, String port) {
		factory = new DriverInitFactory();
		driver = factory.invokeDriver(deviceName, platformName,
				platformVersion, udid, port);
	}

	@Test
	public void validateProductList() {
		testStart();
		logTestMessage("Test Begin");
		DeviceActions action = new DeviceActions(driver);
		BasePageScreen screenHandler = new BasePageScreen(driver, action);

		String TestProductLabel = "PickitemFor"
				+ factory.getCapability("deviceName");
        
		screenHandler.getLoginPage().performLogin();
		screenHandler.getHomeScreen().clickOnSellButton();
		screenHandler
				.getListingScreen()
				.captureProductImage()
				.chooseProductToList()
				.selectProductCategory()
				.fillMandatoryProductInformation(TestProductLabel, "100.75")
				.selectItemConditionDetails(
						TestConditions.fillNonMandatoryField.YES,
						TestConditions.ItemCondtion.USED)
				.fillDescription("Product information -test")
				.ListItemUnderCategory();

		screenHandler.getHomeScreen().navigateToTab(HomeScreenTabs.BROWSE)
				.scrollandSelectCategory();

		screenHandler.getbrowseProductCategoryScreen().selectProductFiler();

		Assert.assertTrue(screenHandler.getbrowseProductCategoryScreen()
				.isProductPresentOnScreen(TestProductLabel));

		action.logScreenCapture("Listing Product",
				"browseProductCategoryScreen");

		endTest();
	}

	@AfterClass
	public void tearDown() {
		factory.quitDriverInstance();
	}

}
