import javax.swing.*;
import java.awt.*;

public class GradeCalculatorInterface {
    static JFrame frame = new JFrame();
    static GridBagConstraints gbc = new GridBagConstraints();

    public static void result(JPanel homepanel, int total, double average, String grade) {
        homepanel.removeAll();
        String[] labels = {"Total Marks: " + total, "Average Marks: " + average, "Grade: " + grade};
        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setFont(new Font("Arial", Font.BOLD, 22));
            gbc.gridx = 0;
            gbc.gridy = i;
            homepanel.add(label, gbc);
        }
        updateFrame(homepanel);
    }

    public static void marks(JPanel homepanel, int n) {
        homepanel.removeAll();
        JTextField[] marksFields = new JTextField[n];
        for (int i = 0; i < n; i++) {
            JLabel sub = new JLabel(String.format("Enter Subject %d:", i + 1));
            gbc.gridx = 0;
            gbc.gridy = i;
            homepanel.add(sub, gbc);
            JTextField subjectField = new JTextField(7);
            marksFields[i] = subjectField;
            gbc.gridx = 3;
            homepanel.add(subjectField, gbc);
        }

        JButton find = new JButton("Find");
        gbc.gridx = 0;
        gbc.gridy = n + 1;
        homepanel.add(find, gbc);

        find.addActionListener(e -> calculateAndShowResults(homepanel, marksFields, n));

        updateFrame(homepanel);
    }

    public static void grade() {
        JPanel homepanel = new JPanel(new GridBagLayout());
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel title = new JLabel("Check Your Grade", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        homepanel.add(title, gbc);

        JLabel inp = new JLabel("Enter No of Subjects:");
        gbc.gridy = 1;
        homepanel.add(inp, gbc);

        JTextField numofSub = new JTextField(7);
        gbc.gridy = 2;
        homepanel.add(numofSub, gbc);

        JButton calc = new JButton("Calculate");
        gbc.gridy = 3;
        homepanel.add(calc, gbc);

        calc.addActionListener(e -> {
            int n = Integer.parseInt(numofSub.getText());
            marks(homepanel, n);
        });

        updateFrame(homepanel);
    }

    public static void intro() {
        frame.setSize(500, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel homepanel = new JPanel(new GridBagLayout());
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel title = new JLabel("Grade Calculator", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        homepanel.add(title, gbc);

        JLabel introLabel = new JLabel("<html>Grade-list:<br> 91-100 = S<br> 81-90 = A<br> 71-80 = B<br> 61-70 = C<br> 51-60 = D<br> 35-50 = E<br>less than 35 Fail</html>");
        gbc.gridy = 1;
        homepanel.add(introLabel, gbc);

        JButton check = new JButton("Check Your Grade");
        gbc.gridy = 2;
        homepanel.add(check, gbc);
        check.addActionListener(e -> grade());

        frame.getContentPane().add(homepanel);
        frame.setVisible(true);
    }

    public static void calculateAndShowResults(JPanel homepanel, JTextField[] marksFields, int n) {
        int total = 0;
        for (JTextField marksField : marksFields) {
            total += Integer.parseInt(marksField.getText());
        }
        double average = (double) total / n;
        String grade = determineGrade(average);
        result(homepanel, total, average, grade);
    }

    public static String determineGrade(double average) {
        if (average<=100 && average>90) return "S";
        if (average<=90 && average>80) return "A";
        if (average<=80 && average>70) return "B";
        if (average<=70 && average>60) return "C";
        if (average<=60 && average>50) return "D";
        if (average<=50 && average>=35) return "E";
        return "Fail";
    }

    public static void updateFrame(JPanel panel) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        intro();
    }
}
