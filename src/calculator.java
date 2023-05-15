import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class calculator
{

    static String[] buttonLabels = {
            "<-", "CE", "C", "+",
            "7", "8", "9", "-",
            "4", "5", "6", "*",
            "1", "2", "3", "/",
            "+/-", "0", ".","="
    };
    static TextField text =new TextField();         //文本
    private static boolean hasResult;
    private static String expression;
    public static void main(String[] args)
    {
        Frame frame =new Frame("简易计算器");

        frame.setLocation(500,250);
        frame.setSize(300,400);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                System.out.println("退出成功!");//打印消息
                System.exit(0);//返回为 0 的退出.
            }
        });
        frame.setLayout(new BorderLayout());

        text.setBackground(Color.PINK);
        text.setSize(475,250);
        frame.add(text,BorderLayout.NORTH);
        JPanel buttonPanel = new JPanel(new GridLayout(5, 4));

        for (String label : buttonLabels)
        {
            JButton button = new JButton(label);
            button.setBackground(Color.WHITE);
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.setVisible(true);

    }
    private static class ButtonClickListener implements ActionListener
    {


        @Override
        public void actionPerformed(ActionEvent e)
        {
            String buttonText = e.getActionCommand();
            String currentText = text.getText();
            String newText = currentText + buttonText;
            text.setText(newText);
            if (buttonText.equals("="))
            {
                calculateResult();
            } else if (buttonText.equals("CE"))
            {
                clearEntry();
            } else if (buttonText.equals("C"))
            {
                clearAll();
            } else if (buttonText.equals("<-"))
            {
                backspace();
            } else
            {
                appendText(buttonText);
            }

        }

        private void calculateResult()
        {
            if (!hasResult) {
                try {
                    double result = evaluateExpression(expression);
                    text.setText(Double.toString(result));
                    expression = Double.toString(result);
                    hasResult = true;
                } catch (Exception e) {
                    text.setText("Error");
                    expression = "";
                    hasResult = false;
                }
            }
        }

        private void clearEntry() {
            if (expression.length() > 0) {
                expression = expression.substring(0, expression.length() - 1);
                text.setText(expression);
            }
        }

        private void clearAll() {
            expression = "";
            text.setText("");
            hasResult = false;
        }

        private void backspace() {
            if (expression.length() > 0) {
                expression = expression.substring(0, expression.length() - 1);
                text.setText(expression);
            } else {
                text.setText("0");
            }
        }

        private void appendText(String texts) {
            if (hasResult) {
                expression = texts;
                text.setText(expression);
                hasResult = false;
            } else {
                if (expression != null) {
                    expression += texts;
                } else {
                    expression = texts;
                }
                text.setText(expression);
            }
        }

        private double evaluateExpression(String expression) {
            try {
                String[] tokens = expression.split("(?<=\\D)|(?=\\D)");
                double result = Double.parseDouble(tokens[0]);

                for (int i = 1; i < tokens.length; i += 2) {
                    String operator = tokens[i];
                    String operand = tokens[i + 1];

                    switch (operator) {
                        case "+":
                            result += Double.parseDouble(operand);
                            break;
                        case "-":
                            result -= Double.parseDouble(operand);
                            break;
                        case "*":
                            result *= Double.parseDouble(operand);
                            break;
                        case "/":
                            result /= Double.parseDouble(operand);
                            break;
                        default:
                            throw new RuntimeException("Invalid expression");
                    }
                }

                return result;
            } catch (Exception e) {
                throw new RuntimeException("Invalid expression");
            }
        }
    }

}