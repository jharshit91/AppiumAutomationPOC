package ReportConfiguration;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.NetworkMode;

public class ExtentManager {
    private static ExtentReports extent;
    
    public synchronized static ExtentReports getReporter(String filePath) {
        if (extent == null) {
            extent = new ExtentReports(filePath, true, NetworkMode.OFFLINE);
            
            extent
                .addSystemInfo("Host Name", "Harshit Jhureley")
                .addSystemInfo("Environment", "QA");
        }
        
        return extent;
    }
    
    public synchronized static ExtentReports getReporter() {
        return extent;
    }

}