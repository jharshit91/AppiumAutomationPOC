package infra;

import java.io.File;

public class Constants{

public static final String serverAddress="http://127.0.0.1:4723/wd/hub"; 
public static final String appPath=System.getProperty("user.dir")+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"app"+File.separator+"Carousell_2.44.130.apk";	
public static final String appActivity="com.thecarousell.Carousell.activities.EntryActivity";
public static final String appPackage="com.thecarousell.Carousell";
public static final String automationName="uiAutomator2";

}
