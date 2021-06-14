import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class GpaCalculatorView extends Component implements ActionListener {
    private JFrame gpaWindow;
    private JFrame gpaCalculatedWindow;
    private JTextField grade;
    private JTextField unit;
    private static double[] grades;
    private static double[] units;
    private JLabel unWeightedGpa;
    private JLabel weightedGpa;
    private JLabel gradeTitle;
    private JLabel unitTitle;
    private JButton nextButton; // next page
    private JButton backButton; // back one page
    private JButton resetButton;
    private static final int amountOfClasses = 1;
    private static int classCount = 0;

    public static void main(String [] args){
        GpaCalculatorView view = new GpaCalculatorView();
    }


    public GpaCalculatorView(){
        grades = new double[amountOfClasses];
        units = new double[amountOfClasses];
        initializeGpaCalculatorWindow();
        initializeCalculatedWindow();
    }

    private void initializeCalculatedWindow() {
        gpaCalculatedWindow = new JFrame("Gpa Calculator");
        gpaCalculatedWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gpaCalculatedWindow.setSize(350,400);
        gpaCalculatedWindow.setLayout(new GridLayout(3,2));
        JLabel calculatedUnWeightedGpaTitle = new JLabel("UnWeighted Gpa:");
        JLabel calculatedWeightedGpaTitle = new JLabel("Weighted Gpa:");
        unWeightedGpa = new JLabel("0.0");
        weightedGpa = new JLabel("0.0");
        resetButton = new JButton("reset");
        resetButton.addActionListener(this);
        gpaCalculatedWindow.add(calculatedUnWeightedGpaTitle);
        gpaCalculatedWindow.add(calculatedWeightedGpaTitle);
        gpaCalculatedWindow.add(unWeightedGpa);
        gpaCalculatedWindow.add(weightedGpa);
        gpaCalculatedWindow.add(resetButton);
        gpaCalculatedWindow.setVisible(false);
    }

    private void initializeGpaCalculatorWindow() {
        gpaWindow = new JFrame("Gpa Calculator");
        gpaWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gpaWindow.setSize(350,400);
        gpaWindow.setLayout(new GridLayout(3,2));
        gradeTitle = new JLabel("Enter your grade below");
        unitTitle = new JLabel("Enter units below");
        grade = new JTextField();
        unit = new JTextField();
        nextButton = new JButton("next");
        nextButton.addActionListener(this);
        backButton = new JButton("back");
        backButton.addActionListener(this);
        gpaWindow.add(gradeTitle);
        gpaWindow.add(unitTitle);
        gpaWindow.add(grade);
        gpaWindow.add(unit);
        gpaWindow.add(backButton);
        gpaWindow.add(nextButton);
        gpaWindow.setVisible(true);
    }


    public void updateGpaCalculatedWindow(double unWeightedGpa,
                                          double weightedGpa){
        this.unWeightedGpa.setText(String.valueOf(unWeightedGpa));
        this.weightedGpa.setText(String.valueOf(weightedGpa));
    }


    @Override
    public void actionPerformed(ActionEvent buttonClicked) {
        if(buttonClicked.getSource() == nextButton){
            clickedNextButton();
        }
        else if(buttonClicked.getSource() == backButton){
            clickedBackButton();
        }
        else if(buttonClicked.getSource() == resetButton){
            clickedResetButton();
        }
    }

    private void clickedResetButton() {
        clearGpaWindow();
        Arrays.fill(grades, 0.0); // 0.0 is the reset value
        Arrays.fill(units, 0.0);
        classCount = 0;
        gpaWindow.setVisible(true);
        gpaCalculatedWindow.setVisible(false);
    }

    private void clickedBackButton() {
        classCount--;
        grade.setText(String.valueOf(grades[classCount]));
        unit.setText(String.valueOf(units[classCount]));
    }

    private void clickedNextButton() {
        checkData();
        if(amountOfClasses == classCount){
            double unWeightedGpa = calculateUnWeightedGpa();
            double weightedGpa = calculateWeightedGpa();
            gpaWindow.setVisible(false);
            updateGpaCalculatedWindow(unWeightedGpa,weightedGpa);
            gpaCalculatedWindow.setVisible(true);
        }
        clearGpaWindow();
    }

    private void clearGpaWindow() {
        grade.setText("");
        unit.setText("");
    }

    private void checkData() {
        try {
            grades[classCount] = Double.parseDouble(grade.getText());
            units[classCount] = Double.parseDouble(grade.getText());
            classCount++;
        }catch (NumberFormatException exception){
            JDialog dialog = new JDialog(SwingUtilities.windowForComponent(this));
            dialog.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.gridwidth = GridBagConstraints.REMAINDER;
            panel.add(new JLabel("Error detected. \n" +
                    "Please enter a number into both boxes."), constraints);
            JButton okay = new JButton("Okay");
            okay.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SwingUtilities.windowForComponent(okay).dispose();
                }
            });
            panel.add(okay, constraints);
            dialog.add(panel);
            dialog.pack();
            dialog.setLocationRelativeTo(gpaWindow);
            dialog.setVisible(true);
        }
    }

    public static double calculateUnWeightedGpa(){
        double gradePointSum = 0.0;
        for(double gradePoint: grades)
            gradePointSum += gradePoint;
        return (double) (Math.round((gradePointSum / amountOfClasses) * 100)
                / 100);
    }

    public static double calculateWeightedGpa(){
        double unWeightedGpa = calculateUnWeightedGpa();
        double amountOfCredits = 0.0;
        for(double course: units)
            amountOfCredits += course;
        double unWeightedTimesCredits = unWeightedGpa * amountOfCredits;
        return (double) (Math.round(unWeightedTimesCredits / amountOfCredits *
                              100)/100);
    }
}
