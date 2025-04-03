package utils;

import java.io.IOException;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import TestBases.CashCollections_TestBase;
import TestBases.Remittances_TestBase;

public class Remittances_Listeners extends Remittances_TestBase implements ITestListener
{
	ExtentTest test;
    ExtentReports extent = ExtentReporterNG.getReporterObject();
    ChromeDriver driver;

    // Default constructor required by TestNG
    public Remittances_Listeners() {
        super();
    }

	@Override
	public void onTestStart(ITestResult result)
	{
		test = extent.createTest(result.getMethod().getMethodName());
	}

	@Override
	public void onTestFailure(ITestResult result) 
	{
		test.fail(result.getThrowable());
		
		try {
			driver = (ChromeDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} 
		catch (Exception e1) 
		{
			e1.printStackTrace();
		}
		
		try {
			test.addScreenCaptureFromPath(getScreenShotPath(result.getMethod().getMethodName(),driver), result.getMethod().getMethodName());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSuccess(ITestResult result)
	{
		test.log(Status.PASS, "Test Passed");
	}

	@Override
	public void onFinish(ITestContext context) 
	{
		extent.flush();
	}
}
