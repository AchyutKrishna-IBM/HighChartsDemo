
package runner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDefinitions {
	static WebDriver driver;
	static WebDriverWait wait;
	static Actions builder;
	static WebElement codinate;

	static String SessionValueBeforeClick;

		@Given("^: Access to browser and url is given$")
		public void access_to_browser_and_url_is_given() throws Throwable {
		    // Write code here that turns the phrase above into concrete actions
		    System.setProperty("webdriver.chrome.driver", "./Drivers/chromedriver.exe");
		    driver=new ChromeDriver();
		    wait=new WebDriverWait(driver,60);
		    driver.manage().window().maximize();
		    driver.get("https://www.highcharts.com/demo/line-ajax");
		    
		}

		@When("^: Mouseover to Jan 5,2018 on the graph$")
	static public void mouseover_to_Jan_on_the_graph() throws Throwable {
			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"CybotCookiebotDialogBodyLevelButtonAccept\"]")));
	    	 WebElement COOKIES=driver.findElement(By.xpath("//*[@id=\"CybotCookiebotDialogBodyLevelButtonAccept\"]"));
	    	 //I choose the thread sleep option at the end
	    	 builder=new Actions(driver);
	    	 Thread.sleep(5000);
	    	 try {
	    		 driver.findElement(By.xpath("//*[@id=\"CybotCookiebotDialogBodyLevelButtonAccept\"]")).click();
	    	 }
	    	 catch (Exception e) {
	    		 builder.moveToElement(COOKIES).click().build().perform();
			}
	    	 //Chart code start
	    	 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@fill,'#434348')]")));
	    	 wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//*[contains(@fill,'#434348')]"),30));
	    	 List<WebElement> graphPath = driver.findElements(By.xpath("//*[contains(@fill,'#434348')]"));
	    	 System.out.println(graphPath.size());
	    	 //date logic
	    	 SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
	    	 String ChartStartDate = "18/12/2017";
	    	 String DateWeAreTesting = "05/01/2018";
	    	 int diffInDays=18;//default for date 05/01/2018
	    	 try {
	    		    Date date1 = myFormat.parse(ChartStartDate);
	    		    Date date2 = myFormat.parse(DateWeAreTesting);
	    		    long diff = date2.getTime() - date1.getTime();
	    		    diffInDays=(int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	    		} catch (ParseException e) {
	    		    e.printStackTrace();
	    		}
		codinate=graphPath.get(diffInDays);
	    	 
	         Action mouseOverHome = builder
	        		 .moveToElement(codinate)
	        		 .build();
	         
	         mouseOverHome.perform();
		}

		@Then("^: Store sessions count to a variable$")
	 public void store_sessions_count_to_a_variable() throws Throwable {
		    // Write code here that turns the phrase above into concrete actions
		 SessionValueBeforeClick=driver.findElement(By.xpath("//*[@dx='0' and contains(@style,'bold')][2]")).getText();
	         SessionValueBeforeClick=SessionValueBeforeClick.trim();
	         System.out.println(SessionValueBeforeClick);
		}

		@Then("^: Click on Jan 5,2018$")
		public void click_on_Jan() throws Throwable {
		    // Write code here that turns the phrase above into concrete actions
			builder.moveToElement(codinate).click().perform();
		}

		@Then("^: Comapare session count between tool tip and highlighted window$")
		public void comapare_session_count_between_tool_tip_and_highlighted_window() throws Throwable {
		    // Write code here that turns the phrase above into concrete actions
			String test=driver.findElement(By.xpath("//div[@class='highslide-maincontent']")).getText();
	         String[] SessionValueAfterClick = test.split(":",2)[1].split(" ");
	         System.out.println(SessionValueAfterClick[0]);
	         //String Validation logic
	         if (SessionValueBeforeClick.equals(SessionValueAfterClick[0].trim())) {
				System.out.println("Value match successfull");
			} else {
				System.out.println("Value matching failed");
			}
	         //Close Highlighted window close logic
	         wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='Close (esc)']")));
	         driver.findElement(By.xpath("//a[@title='Close (esc)']")).click();
	         driver.close();
	         driver.quit();
		}


	}






