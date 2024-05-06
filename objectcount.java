import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class objectcount {
  
  public static void main(String[] args) {
     System.setProperty("webdriver.chrome.driver", "C:\\Users\\annex\\Desktop\\chromedriver\\chromedriver_win32 -new\\chromedriver.exe");
     WebDriver driver = new ChromeDriver(); 
     driver.get("http://www.google.com");
     driver.manage().window().maximize(); 
     // Find the <p> element on the page
     WebElement paragraph = driver.findElement(By.tagName("p"));
     System.out.println("The number of <p> elements present are: " + driver.findElements(By.tagName("p")).size());
     //System.out.println("Found paragraph: " + paragraph.getText()); // To verify that the correct paragraph was found
     
     driver.quit();
  }
}
  
