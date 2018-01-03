package pages;

import java.util.List;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import utils.Credentials;
import utils.DeviceActions;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class LoginScreen extends CommonObjects  {

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
	
	@AndroidFindBy(id ="email_signup_button")
	private MobileElement signUpButton;
	
	@AndroidFindBy(id ="fb_auth_button")
	private MobileElement continueWithFB;
	
	@AndroidFindBy(id ="gplus_signin_button")
	private MobileElement continueWithgplus;
	
	DeviceActions action;
	AppiumDriver<MobileElement> driver;
	public LoginScreen(AppiumDriver<MobileElement> driver,DeviceActions action) {
		super(driver,action);
		this.driver=driver;
		this.action=action;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this); 
	}


	public void performLogin() {
		if(action.isElementOnScreen(searchBar, "searchBar")==false){
		action.logTestMessage("User is not already LoggedIn.");
		verifyElementsOnWelcomeScreen();
		clickOnLoginButton("WelcomeScreen");
		closeGoogleAccountPopupifDisplayed();
		action.setTextFieldValue(username, Credentials.username);
		action.setTextFieldValue(password, Credentials.password);
		action.logTestMessage("Entered User Credentials");
		clickOnLoginButton("LoginScreen");
		action.logTestMessage("Tap on Login Button");
		}
	}

	public void verifyElementsOnWelcomeScreen(){
		action.logTestMessage("Verifying Welcome Screen");
	action.verifyMobileElements("WelcomeScreen", loginButton,signUpButton,continueWithFB,continueWithgplus); 
	}
	
	
	public void clickOnLoginButton(String ScreenName){
		loginButton.click();
		action.logTestMessage("Tapped Login Button on "+ ScreenName);
	}
	
	public void  closeGoogleAccountPopupifDisplayed(){
		action.clickifDisplayed(continueWithNone, 4);
	}
	
}
