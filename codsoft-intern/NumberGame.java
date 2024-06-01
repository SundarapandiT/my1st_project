import java.util.Random;
import java.util.Scanner;
public class NumberGame {
    public static void game()
    {
        Scanner sc = new Scanner(System.in);
        Random random=new Random();
        System.out.println("Welcome to Number-Game\nInstruction:\n1.You have to guess the number which will be displayed\n2.you have 5 round\n3.Each round has 10 points\n4.If you scored 50 points-you will get Rs.1000 Cashback\n 40 points-Rs.500 Cashback\n 30 points - voucher card \n else Better luck next time -");

        System.out.print("Press y to continue..:");
        String x=sc.next();
        int score = 0;
        for(int i=1;i<=5;i++)
        {
            int randomNumber=random.nextInt(100);
            System.out.println("Round-"+i+" \n Enter Your guessing number(0-100)");
            int Userinput=sc.nextInt();
            System.out.println("The original Number: "+randomNumber);
            if(randomNumber==Userinput)
            {
                System.out.println("\nYeah!!! You are Correct... ");
                score=+10;
            }
            else {
                System.out.println("Oops!!! You are Wrong...");
            }
        }
        System.out.println("Your Final Score: "+ score);
        if(score==50)
        {
            System.out.println("You won Rs.1000 Cashback!!!");
        }
        else if(score==40)
        {
            System.out.println("You won Rs.500 Cashback!!!");
        }
        else if(score==30)
        {
            System.out.println("You won a Gift Voucher!!!");
        }
        else {
            System.out.println("Better Luck Next Time!!!\n\n Enter 1 to play again...\n Else press 0..");
            int n=sc.nextInt();
            if(n==1)
            {
                game();
            }

        }
    }
    public static void main(String[] args)
    {
        game();
    }
}
