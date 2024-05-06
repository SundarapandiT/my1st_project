import java.io.*;
import jxl.*;
import jxl.read.biff.BiffException;

public class student_mark {
	public static void main(String[] args) throws IOException{
		File inputWorkbook = new File("C:\\Users\\ravip\\Downloads\\student.xls");
		int count=0;
		try {
			Workbook wb = Workbook.getWorkbook(inputWorkbook);
			Sheet sheet = wb.getSheet(0);
			for(int j=0;j < sheet.getRows(); j++) {
				for(int i=0; i < sheet.getColumns(); i++) {
					Cell cell = sheet.getCell(i, j);
					if (cell.getType() == CellType.NUMBER) {
						if(Integer.parseInt(cell.getContents())>60) {
							count++;
							break;
						}
					}
				}
			}
			System.out.println("Total number of students who scored \nmore than 60 in one or more subjects is: "
        +count);
		}catch(BiffException e) {
			e.printStackTrace();
		}
	}
}