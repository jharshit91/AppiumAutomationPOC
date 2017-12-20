package pages;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.WithTimeout;

import org.openqa.selenium.support.PageFactory;

import testData.TestConditions.Direction;
import utils.DeviceActions;

public class BrowseProductCategoryScreen {
	@AndroidFindBy(id = "bar_filter_title")
	private MobileElement productfilter;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"Recent\")")
	private MobileElement recentOption;
	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"OK, Got it!\")")
	private MobileElement confirmBrowsingLocation;

	@AndroidFindBy(className = "android.support.v7.widget.RecyclerView")
	private MobileElement scrollview;

	@AndroidFindBy(id = "btn_filter")
	private MobileElement filterResult;

	@WithTimeout(time = 5, unit = TimeUnit.SECONDS)
	@AndroidFindBy(id = "text_attribute_1")
	private List<MobileElement> ListTitleLabel;

	DeviceActions action;
	AppiumDriver<MobileElement> driver;

	public BrowseProductCategoryScreen(AppiumDriver<MobileElement> driver,
			DeviceActions action) {
		this.driver = driver;
		this.action = action;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	public BrowseProductCategoryScreen selectProductFiler() {
		action.clickifDisplayed(confirmBrowsingLocation, 5);
		action.clickifDisplayed(productfilter, 10);
		action.clickifDisplayed(recentOption, 10);
		action.logScreenCapture("Navigate to Product Filter","Product filter");
		filterResult.click();
		return this;
	}

	public Boolean isProductPresentOnScreen(String productName) {

		return action.isElementAvailableOnScreen(ListTitleLabel, scrollview,
				productName, 6, Direction.UP);

	}

}
