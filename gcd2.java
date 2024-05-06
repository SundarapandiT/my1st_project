import java.util.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;


public class gcd {
	public static void main(String[] args) {
		 System.setProperty("webdriver.chrome.driver","C:\\Users\\ravip\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
	        ChromeDriver driver = new ChromeDriver();
		driver.get("C:\\Users\\ravip\\Downloads\\ex11.html");
		List<WebElement> elements = driver.findElements(By.tagName("input"));
		elements.get(0).sendKeys("235");
		elements.get(1).sendKeys("15");
		elements.get(2).click();
		String result = elements.get(3).getAttribute("value");
		System.out.println("The GCD is: "+result);
	}
}
