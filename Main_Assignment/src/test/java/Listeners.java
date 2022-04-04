import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class Listeners  extends TestListenerAdapter {

    public static ExtentHtmlReporter HtmlReporter; //Responsible for template
    //How the template looks like and what kind of information we need to specify in to the template

    public static ExtentReports Extent; //By using this we will send/ save ou logging messages
    public static ExtentTest logger;   //This class is used to specify the status of the test(Pass or Fail)

    @Override
    //onSTART METHOD IS USED HERE
    //BECAUSE WE WANT TO GENERATE The Report for all the test cases as soon as the test get started
    //THAT is For All the Test Methods It will Generate only One Report
    public void onStart(ITestContext iTestContext) {
        //Below line is representing where we need to store our report
        HtmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"/ExtentReports/myReport.html");

        //We need To load the xml file In order to generate report
        //Basically  It allows us to configure HTML Reports
        HtmlReporter.loadXMLConfig(System.getProperty("user.dir")+"/extent-config.xml");

        Extent=new ExtentReports();
        Extent.attachReporter(HtmlReporter);


        //These, Below statements will be used To send some information to the report

        HtmlReporter.config().setDocumentTitle("Automation report"); //Title of the report
        HtmlReporter.config().setReportName("REPORT ON TODO API"); //Name of the report
        HtmlReporter.config().setTestViewChartLocation(ChartLocation.TOP); // Chart Location
        HtmlReporter.config().setTheme(Theme.DARK); //Theme of the page
    }

    @Override   //ITestResult is a result-type
    //It is a predefined interface which is always there, it will represent our result
    //tr is variable which will store our result
    public void onTestSuccess(ITestResult tr) {

        //WE USE THE LOGGER TO SEND THE STATUS OF OUR TEST CASE

        logger = Extent.createTest(tr.getName());  //THIS WILL CREATE A NEW ENTRY IN THE REPORT
        logger.log(Status.PASS, MarkupHelper.createLabel(tr.getName(), ExtentColor.GREEN));
    }                        //MarkupHelper is used here for generating colors
    //Green on Test Case Pass
    @Override
    public void onTestFailure(ITestResult tr) {
        logger = Extent.createTest(tr.getName());
        logger.log(Status.FAIL, MarkupHelper.createLabel(tr.getName(), ExtentColor.RED));
    }                           //Red If Test Case Fails


    @Override
    public void onFinish(ITestContext iTestContext) {
        Extent.flush();  //It will Clear everything and Generates the report
        //Everytime when we execute our test cases
        //This will erase  previous data present on the report and create a new report.
    }



}
