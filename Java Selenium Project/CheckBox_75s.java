
import java.util.List;
import java.util.concurrent.TimeUnit; 
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
public class CheckBox_75s {
public static void main(String[] args) {

System.setProperty("webdriver.chrome.driver","C:\\Users\\annex\\Desktop\\chromedriver\\chromedriver_win32 -new\\chromedriver.exe");
ChromeDriver driver = new ChromeDriver();
 driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
 driver.manage().window().maximize();
 driver.get("C:\\New.html");
 List<org.openqa.selenium.WebElement>CheckBoxes =driver.findElements(By.xpath("//input[@type='checkbox']"));
 System.out.println("Number of Check boxes : "+Integer.toString(CheckBoxes.size()));
for(int i=0; i<CheckBoxes.size(); i=i+4)
{
CheckBoxes.get(i).click();
}
int checkedCount=0, uncheckedCount=0;
 for(int i=0; i<CheckBoxes.size(); i++)
{
System.out.println(i+" checkbox is selected "+CheckBoxes.get(i).isSelected()); 
if(CheckBoxes.get(i).isSelected())
{
checkedCount++;
}
else
{ 
uncheckedCount++;
}
}
System.out.println("number of selected checkbox: "+checkedCount); 
System.out.println("number of unselected checkbox: "+uncheckedCount);
}
}

