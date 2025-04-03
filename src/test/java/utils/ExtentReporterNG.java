package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG 
{
	static ExtentReports extent;
	
	public static ExtentReports getReporterObject()
	{
		String path = System.getProperty("user.dir")+"\\reports\\index.html";
		extent = new ExtentReports();
		
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("Web Automation Results");
		reporter.config().setDocumentTitle("Test Results");
		
		extent.attachReporter(reporter);
		extent.setSystemInfo("Tester", "Mohamed Derbala");
		
		return extent;
	}

}
