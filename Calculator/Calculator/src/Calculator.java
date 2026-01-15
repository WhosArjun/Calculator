import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Calculator {
    //create a window
    int boardWidth = 360;
    int boardHeight = 540;

    Color customLightGray = new Color(212,212,210);
    Color customDarkGray = new Color(80,80,80);
    Color customBlack = new Color(28,28,28);
    Color customOrange = new Color(255,149,0);

    //when we press buttons it will iterate through this array
    String[] buttonValues = {
        "AC","+/-","%","/",
        "7","8","9","*",
        "4","5","6","-",
        "1","2","3","+",
        "0",".","="
    }; //button values
    String[] rightSymbols = {"/","*","-","+","="}; //right side operations
    String[] topSymbols = {"AC","+/-","%"}; //clearning, +/-, and percentage
    
    JFrame frame = new JFrame("Calculator");
    JLabel displayLabel = new JLabel();
    JPanel displayPanel = new JPanel();

    JPanel buttonsPanel = new JPanel();
    // A+B, A-B, A/B (Two number we're working with)
    String A = "0";
    String operator = null;
    String B = null;

    Calculator(){ 
        frame.setVisible(true);//so you can see window
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        displayLabel.setBackground(customBlack);
        displayLabel.setForeground(Color.white);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 80));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0"); //default text
        displayLabel.setOpaque(true);

        displayPanel.setLayout(new BorderLayout()); // jump starting the panel
        displayPanel.add(displayLabel); // display label into the panel
        frame.add(displayPanel, BorderLayout.NORTH); // display panel onto the window/frame

        buttonsPanel.setLayout(new GridLayout(5,4));
        buttonsPanel.setBackground(customBlack);
        frame.add(buttonsPanel);

        for(int i = 0; i<buttonValues.length; i++){
            JButton button = new JButton();
            String buttonValue = buttonValues[i];
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            button.setText(buttonValue);
            button.setFocusable(false); //no recatangle around the button text
            button.setBorder(new LineBorder(customBlack));
            if(Arrays.asList(topSymbols).contains(buttonValue)){
                button.setBackground(customLightGray);
                button.setForeground(customBlack);

            } else if(Arrays.asList(rightSymbols).contains(buttonValue)){
                button.setBackground(customOrange);
                button.setForeground(Color.white);

            } else {
                button.setBackground(customDarkGray);
                button.setForeground(Color.white);
            }
            buttonsPanel.add(button);

            button.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    JButton button = (JButton) e.getSource(); //get button
                    String buttonValue = button.getText(); //get text of the button that is clicked
                    if(Arrays.asList(rightSymbols).contains(buttonValue)){
                        if(buttonValue == "="){
                            if(A!=null){
                                B = displayLabel.getText();
                                double numA = Double.parseDouble(A);
                                double numB = Double.parseDouble(B);

                                if(operator == "+"){
                                    displayLabel.setText(removeZeroDecimal(numA+numB));
                                } else if (operator == "-"){
                                    displayLabel.setText(removeZeroDecimal(numA - numB));
                                } else if (operator == "*"){
                                    displayLabel.setText(removeZeroDecimal(numA * numB));
                                } else if (operator == "/"){
                                    displayLabel.setText(removeZeroDecimal(numA / numB));
                                }
                                clearAll();
                            }
                        }
                        else if("+-*/".contains(buttonValue)){
                            if(operator == null){
                                A = displayLabel.getText();
                                displayLabel.setText("0");
                                B = "0";
                            }
                            operator = buttonValue;
                        }
                    } else if(Arrays.asList(topSymbols).contains(buttonValue)) {
                        if(buttonValue == "AC"){
                            clearAll();
                            displayLabel.setText("0");
                        } else if(buttonValue == "+/-"){
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            //get text and convert to double
                            numDisplay *= -1;
                            displayLabel.setText(removeZeroDecimal(numDisplay));
                        } else if(buttonValue == "%"){
                            //% dividing by 100
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            numDisplay /= 100;
                            displayLabel.setText(removeZeroDecimal(numDisplay));
                        }
                    } else {
                        if(buttonValue == "."){
                            if(!displayLabel.getText().contains(buttonValue)){
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                        }
                        else if("0123456789".contains(buttonValue)){
                            if(displayLabel.getText() == "0"){
                                displayLabel.setText(buttonValue);
                            } else {
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                        }

                        
                    }

                }
            });
            

        }
    } 
    void clearAll(){
        A = "0";
        operator = null;
        B = null;
    }

    String removeZeroDecimal(double numDisplay){
        if(numDisplay % 1 == 0){
            return Integer.toString((int) numDisplay);
        }
        return Double.toString(numDisplay);
    }
}
