import java.util.Arrays;
import java.util.Scanner;
public class GradeCalculator {
    public static void main(String[] args)
    {
        Scanner sc= new Scanner(System.in);
        System.out.println("Grade Calculator\nGrade-list:\n 91-100 = S\n 81-90 = A\n 71-80 = B\n 61-70 = C\n 51-60 = D\n 35-50 = E\n less than 35 Fail");
        System.out.println("Enter your Number of Subjects: ");
        int n=sc.nextInt();
        int[] marks=new int[n];
        for(int i=0;i<n;i++)
        {
            System.out.println("Enter your Mark"+(i+1)+":");
            marks[i]=sc.nextInt();
        }
        int total=Arrays.stream(marks).sum();
        float avg=total/n;
        System.out.println("Your total marks: "+total);
        System.out.println("Your marks average: "+avg);
        if(avg<=100 && avg>90)
        {
            System.out.println("Your Grade: S");
        }
        else if(avg<=90 && avg>80)
        {
            System.out.println("Your Grade: A");
        }
        else if(avg<=80 && avg>70)
        {
            System.out.println("Your Grade: B");
        }
        else if(avg<=70 && avg>60)
        {
            System.out.println("Your Grade: C");
        }
        else if(avg<=60 && avg>50)
        {
            System.out.println("Your Grade: D");
        }
        else if(avg<=50 && avg>=35)
        {
            System.out.println("Your Grade: E");
        }
        if(avg<35)
        {
            System.out.println("Your Grade: F");
        }


    }
}
