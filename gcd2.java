import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Gcd2 {
    private WebDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\\\Users\\\\annex\\\\Desktop\\\\chromedriver\\\\chromedriver_win32 -new\\\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    public void testGcd() {
        driver.get("file:///C:/GCD.html");
        driver.manage().window().maximize();
        driver.findElement(By.name("n1")).sendKeys("5");
        driver.findElement(By.name("n2")).sendKeys("6");
        driver.findElement(By.cssSelector("input[type='button']")).click();
        String result = driver.findElement(By.name("result")).getAttribute("value");
        System.out.println("the gcd is : " + result);
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
