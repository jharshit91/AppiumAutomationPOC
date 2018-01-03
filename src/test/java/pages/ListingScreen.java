package pages;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

import testData.TestConditions;
import testData.TestConditions.Direction;
import utils.DeviceActions;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.WithTimeout;

public class ListingScreen {
	AppiumDriver<MobileElement> driver;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"NEW PHOTO\")")
	private MobileElement newPhotoOption;

	@AndroidFindBy(id = "fab_capture")
	private MobileElement captureProductPic;

	@WithTimeout(time = 10, unit = TimeUnit.SECONDS)
	@AndroidFindBy(id = "pic_thumbnail")
	private List<MobileElement> chooseItems;

	@AndroidFindBy(id = "action_done")
	private MobileElement nextButton;

	@WithTimeout(time = 10, unit = TimeUnit.SECONDS)
	@AndroidFindBy(id = "search_field")
	private MobileElement categorySearchTextField;

	@AndroidFindBy(id = "ll_root")
	private MobileElement selectCategory;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"Listing Title\")")
	private MobileElement listingTitle;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"Price\")")
	private MobileElement price;

	@AndroidFindBy(id = "button_submit")
	private MobileElement listItButton;

	@AndroidFindBy(xpath = "//*[@resource-id='com.thecarousell.Carousell:id/radio_group']/android.widget.RadioButton")
	private List<MobileElement> radioGroup;

	@AndroidFindBy(id = "rv_components")
	private MobileElement scrollableView;

	@AndroidFindBy(id = "text_input")
	private MobileElement productDescription;

	@AndroidFindBy(id = "btn_close")
	private MobileElement closeOnSuccessPopup;

	@AndroidFindBy(id = "button_close")
	private MobileElement closeIt;

	DeviceActions action;

	public ListingScreen(AppiumDriver<MobileElement> driver,
			DeviceActions action) {
		this.action = action;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	public ListingScreen captureProductImage() {
		newPhotoOption.click();
		captureProductPic.click();
		action.logScreenCapture("Capture Product Image","ListingScreen1");
		return this;
	}

	public ListingScreen chooseProductToList() {
		action.waitForElement(chooseItems.get(0), 10).click();
		nextButton.click();
		return this;
	}

	public ListingScreen selectProductCategory() {
		action.waitForElement(categorySearchTextField, 10).sendKeys(
				"Everything Else - Others");
		action.hideKeyboard();
		selectCategory.click();
		action.logTestMessage("Selected Product Category");
		return this;
	}

	public ListingScreen fillMandatoryProductInformation(String title,
			String priceCValue) {
		action.waitForElement(listingTitle, 10);
		action.setTextFieldValue(listingTitle, title);
		action.setTextFieldValue(price, priceCValue);
		action.logScreenCapture("filled product Details","ListingScreen2");
		return this;
	}

	public ListingScreen selectItemConditionDetails(
			TestConditions.fillNonMandatoryField decision,
			TestConditions.ItemCondtion condition) {
		if (decision.toString().equalsIgnoreCase("Yes")) {
			switch (condition) {
			case NEW:
				radioGroup.get(0).click();
				break;

			case USED:
				radioGroup.get(1).click();
				break;
			}
			action.logTestMessage("Select Item Condition radio button");
		}
		return this;
	}

	public ListingScreen fillDescription(String productData) {
		action.verticalSwip(scrollableView, Direction.UP);
		productDescription.sendKeys(productData);
		return this;

	}

	public ListingScreen ListItemUnderCategory() {
		listItButton.click();
		action.clickifDisplayed(closeOnSuccessPopup, 10);
		action.logScreenCapture("List item under category", "ListingScreen3");
		action.clickifDisplayed(closeIt, 5);
		
		return this;
	}

}