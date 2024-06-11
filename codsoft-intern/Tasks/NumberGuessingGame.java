import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
public class NumberGuessingGame {
    static int score;
    static Random random = new Random();
    static int i = 1;
    static GridBagConstraints gbc = new GridBagConstraints();
    static JFrame frame = new JFrame();
    public static JLabel finalresult(String text) {
        JLabel prize = new JLabel(text, JLabel.CENTER);
        prize.setFont(new Font("Arial", Font.BOLD, 22));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        return prize;
    }
    public static void game() {
        JPanel homepanel = new JPanel();
        homepanel.setLayout(new GridBagLayout());
        gbc.insets = new Insets(10, 10, 10, 10);

        if (i <= 5) {
            JLabel title = new JLabel(String.format("Round %d", i), JLabel.CENTER);
            title.setFont(new Font("Arial", Font.BOLD, 22));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            homepanel.add(title, gbc);

            int randomNumber = random.nextInt(100);
            gbc.gridwidth = 1;
            JLabel Scorevalue = new JLabel(String.format("Your Score: %d", score));
            gbc.gridx = 1;
            gbc.gridy = 0;
            homepanel.add(Scorevalue, gbc);

            JLabel inp1 = new JLabel("Enter Your guessing number (0-100)");
            gbc.gridx = 0;
            gbc.gridy = 1;
            homepanel.add(inp1, gbc);

            JTextField number = new JTextField(7);
            gbc.gridx = 0;
            gbc.gridy = 2;
            homepanel.add(number, gbc);

            JButton submit = new JButton("Submit");
            gbc.gridx = 1;
            gbc.gridy = 2;
            homepanel.add(submit, gbc);

            JLabel result = new JLabel();
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 2;
            homepanel.add(result, gbc);

            JButton next = new JButton("Next");
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.gridwidth = 2;
            homepanel.add(next, gbc);
            next.setVisible(false);
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    i += 1;
                    int num = Integer.parseInt(number.getText());
                    if (randomNumber == num) {
                        result.setText(String.format("<html>The original Number: %d <br>Yeah!!! You are Correct...</html>", randomNumber));
                        score += 10;
                    } else {
                        result.setText(String.format("<html>The original Number: %d <br>Oops!!! You are Wrong...</html>", randomNumber));
                    }
                    next.setVisible(true);
                }
            });
            next.addActionListener(e -> game());
            frame.getContentPane().removeAll();
            frame.getContentPane().add(homepanel);
            frame.revalidate();
            frame.repaint();
            frame.setVisible(true);
        } else {
            showFinalResult(homepanel);
        }
    }
    public static void showFinalResult(JPanel homepanel) {
        if (score == 50) {
            String text = "You Won Rs.1000 Cashback!!!";
            JLabel prize = finalresult(text);
            homepanel.add(prize, gbc);
        } else if (score == 40) {
            String text = "You Won Rs.500 Cashback!!!";
            JLabel prize = finalresult(text);
            homepanel.add(prize, gbc);
        } else if (score == 30) {
            String text = "You Won a Gift Voucher!!!";
            JLabel prize = finalresult(text);
            homepanel.add(prize, gbc);
        } else {
            String text = "Better Luck Next Time!!!";
            JLabel prize = finalresult(text);
            homepanel.add(prize, gbc);
        }
        frame.getContentPane().removeAll();
        frame.getContentPane().add(homepanel);
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }
    public static void intro() {
        frame.setSize(500, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel homepanel = new JPanel();
        homepanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel title = new JLabel("Welcome to Number-Game", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        homepanel.add(title, gbc);

        gbc.gridwidth = 1;
        JLabel Intro = new JLabel("<html>INSTRUCTION:<br>1. You have to guess the number which will be displayed<br>2. You have 5 rounds<br>3. Each round has 10 points<br>4. If you scored 50 points - you will get Rs.1000 Cashback<br>If scored 40 points - Rs.500 Cashback<br>If scored 30 points - voucher card<br>Else Better luck next time</html>");
        gbc.gridx = 0;
        gbc.gridy = 1;
        homepanel.add(Intro, gbc);

        JButton cntn = new JButton("Continue");
        gbc.gridx = 1;
        gbc.gridy = 2;
        homepanel.add(cntn, gbc);
        cntn.addActionListener(e -> game());
        frame.getContentPane().add(homepanel);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        intro();
    }
}
