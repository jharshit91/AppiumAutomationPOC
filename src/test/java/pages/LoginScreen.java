package pages;

import java.util.List;

import org.openqa.selenium.support.PageFactory;

import utils.Credentials;
import utils.DeviceActions;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class LoginScreen  {

	@AndroidFindBy(id ="login_button")
	private MobileElement loginButton; 
	
	@AndroidFindBy(id ="com.google.android.gms:id/credential_picker_layout")
	private MobileElement credentialPicker;
	
	@AndroidFindBy(id ="com.google.android.gms:id/cancel")
	private MobileElement continueWithNone;
	
	@AndroidFindBy(id ="layout_username")
	private MobileElement username;
	
	@AndroidFindBy(className ="android.widget.EditText")
	private List<MobileElement> editText;
	
	@AndroidFindBy(id ="layout_password")
	private MobileElement password;
	
	
	DeviceActions action;
	AppiumDriver<MobileElement> driver;
	public LoginScreen(AppiumDriver<MobileElement> driver,DeviceActions action) {
		this.driver=driver;
		this.action=action;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this); 
	}


	public void performLogin() {
		loginButton.click();
		action.logScreenCapture("Navigate to login Screen", "LoginPage");
		action.clickifDisplayed(continueWithNone, 4);
		action.setTextFieldValue(username, Credentials.username);
		action.setTextFieldValue(password, Credentials.password);
		action.logTestMessage("Entered User Credentials");
		loginButton.click();
		action.logTestMessage("Tap on Login Button");
	

		
	}

}
