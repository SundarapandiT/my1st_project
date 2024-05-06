import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
public class login {
	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver","C:\\Users\\ravip\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        // Initialize ChromeDriver
        ChromeDriver driver = new ChromeDriver();

        driver.get("https://www.linkedin.com/login");

       
        WebElement emailField = driver.findElement(By.id("username"));
        emailField.sendKeys("Example7@gmail.com");

        
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("your password");
  
        WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));
        loginButton.click();
String expectedurl=driver.getCurrentUrl();
String actualurl="https://www.linkedin.com/feed/";
if(actualurl.equalsIgnoreCase(expectedurl))
{
	System.out.println("test-passed");
}
else
{
	System.out.println("test-failed");
}
        
         driver.quit();
    }

}
