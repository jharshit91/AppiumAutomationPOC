package pages;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

import testData.HomeScreenTabs;
import testData.TestConditions.Direction;
import utils.DeviceActions;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindAll;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.WithTimeout;

public class HomeScreen {

	@AndroidFindBy(id = "button_sell")
	private MobileElement sellButton;

	@AndroidFindBy(id = "text_tab")
	private List<MobileElement> screenTabs;

	@WithTimeout(time = 5, unit = TimeUnit.SECONDS)
	@AndroidFindBy(className = "android.support.v7.widget.RecyclerView")
	private MobileElement scrollview;
	
	@AndroidFindAll(value ={ @AndroidBy(uiAutomator = "new UiSelector().text(\"EVERYTHING ELSE\")"), @AndroidBy(uiAutomator = "new UiSelector().text(\"Everything Else\")") })
	private MobileElement EveryThingElse;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"SPORTS\")")
	private MobileElement sports;

	AppiumDriver<MobileElement> driver;
	DeviceActions action;

	public HomeScreen(AppiumDriver<MobileElement> driver, DeviceActions action) {
		this.driver = driver;
		this.action = action;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	public void clickOnSellButton() {
		action.waitForElement(sellButton, 20);
		sellButton.click();
		action.logTestMessage("Clicked on Sell Button");
	}

	public HomeScreen navigateToTab(HomeScreenTabs tabScreen) {
		for (MobileElement screen : screenTabs) {

			if (screen.getText().equalsIgnoreCase(tabScreen.toString())) {
				screen.click();
				action.logTestMessage("Navigate to "+tabScreen.toString());
				break;
			}
		}
		return this;
	}

	public void scrollandSelectCategory() {
		action.scrollAndClick(EveryThingElse, scrollview, Direction.UP, 15);
		action.logTestMessage("Navigate to  Everything Else");
	}

}
