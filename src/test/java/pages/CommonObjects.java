package pages;

import org.openqa.selenium.support.PageFactory;

import utils.DeviceActions;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class CommonObjects {
/*Common Screen for Navigation Controls ,Login and HomeScreen */
	DeviceActions action;
	AppiumDriver<MobileElement> driver;	
	
	@AndroidFindBy(id ="header_page_search_text_field")
	protected MobileElement searchBar;
	
	public CommonObjects(AppiumDriver<MobileElement> driver, DeviceActions action) {
		this.driver = driver;
		this.action = action;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

}
