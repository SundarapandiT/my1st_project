import java.util.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
public class checkBox {
public static void main(String[] args) {

System.setProperty("webdriver.chrome.driver","C:\\Users\\ravip\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
ChromeDriver driver = new ChromeDriver();
 driver.get("C:\\Users\\ravip\\Downloads\\07.html");
 List<WebElement>CheckBoxes =driver.findElements(By.xpath("//input[@type='checkbox']"));
 System.out.println("Number of Check boxes : "+Integer.toString(CheckBoxes.size()));
CheckBoxes.get(0).click();
int checkedCount=0, uncheckedCount=0;
	if(CheckBoxes.get(0).isSelected())
	{
		checkedCount++;
	}
	else
	{ 	
		uncheckedCount++;
	}
System.out.println("number of selected checkbox: "+checkedCount); 
System.out.println("number of unselected checkbox: "+uncheckedCount);
}
}
