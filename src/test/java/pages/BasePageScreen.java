package pages;

import java.lang.reflect.InvocationTargetException;

import loggers.Log;
import utils.DeviceActions;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class BasePageScreen {

	private AppiumDriver<MobileElement> driver;
	private DeviceActions action;

	public BasePageScreen(AppiumDriver<MobileElement> driver,
			DeviceActions action) {
		this.driver = driver;
		this.action = action;
	}

	private LoginScreen loginScreen;
	private HomeScreen homeScreen;
	private ListingScreen listScreen;
	private BrowseProductCategoryScreen browseProductCategoryScreen;

	public LoginScreen getLoginPage() {
		return (LoginScreen) ifNull(loginScreen, "pages.LoginScreen");
	}

	public HomeScreen getHomeScreen() {
		return (HomeScreen) ifNull(homeScreen, "pages.HomeScreen");
	}

	public ListingScreen getListingScreen() {
		return (ListingScreen) ifNull(listScreen, "pages.ListingScreen");
	}

	public BrowseProductCategoryScreen getbrowseProductCategoryScreen() {
		return (BrowseProductCategoryScreen) ifNull(browseProductCategoryScreen, "pages.BrowseProductCategoryScreen");
	}

	
	public Object ifNull(Object object, String className) {
		if (object == null) {
			try {
				return (Object) Class.forName(className)
						.getConstructor(AppiumDriver.class, DeviceActions.class)
						.newInstance(driver, action);
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException
					| ClassNotFoundException e) {
				Log.INFO("Exception occured in creating Objects"+e.getMessage());
			}
		}
		return object;

	}

}
