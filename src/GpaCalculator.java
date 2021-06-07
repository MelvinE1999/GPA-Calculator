import java.util.InputMismatchException;
import java.util.Scanner;

public class GpaCalculator {
    private static Scanner userInput = new Scanner(System.in);
    private static int amountOfClasses;

    public static void main(String[] args){
        run();
    }

    public static void run(){
        try {
            System.out.print("Enter the amount of classes you took: ");
            amountOfClasses = userInput.nextInt();
            System.out.println();
            double[] classGrades = new double[amountOfClasses];
            double[] classUnits = new double[amountOfClasses];
            fillInClassGrades(classGrades);
            fillInClassUnits(classUnits);
            double unWeightedGpa = calculateUnWeightedGpa(classGrades);
            double weightedGpa = calculateWeightedGpa(classGrades, classUnits);
            System.out.println("UnWeighted GPA: " + unWeightedGpa);
            System.out.println("Weighted GPA: " + weightedGpa);
        } catch (InputMismatchException invalidTypeEntered) {
            System.out.println("Non-Letter input recognized. " +
                    "Please only type in numbers. Goodbye.\n");
        }
    }

    public static double[] fillInClassGrades(double[] classGrades){
        printGradeChart();
        System.out.println("\nUsing the above chart please fill out " +
                           "your grades\n");
        for(int classNumber = 1; classNumber <= amountOfClasses; classNumber++){
            System.out.print("What grade did you get in class# " +
                            classNumber + ": ");
            classGrades[classNumber - 1] = userInput.nextDouble();
        }
        System.out.println();
        return classGrades;
    }

    public static void printGradeChart(){
        System.out.println("Here is the grade point breakdown based " +
                           "off of letter grades:");
        System.out.println("A+ = 4.0");
        System.out.println("A  = 4.0");
        System.out.println("A- = 3.7");
        System.out.println("B+ = 3.3");
        System.out.println("B  = 3.0");
        System.out.println("B- = 2.7");
        System.out.println("C+ = 2.3");
        System.out.println("C  = 2.0");
        System.out.println("C- = 1.7");
        System.out.println("D+ = 1.3");
        System.out.println("D  = 1.0");
        System.out.println("D- = 0.7");
        System.out.println("F  = 0.0");
    }

    public static double[] fillInClassUnits(double[] classUnits){
        for(int classNumber = 1; classNumber <= amountOfClasses; classNumber++){
            System.out.print("How many units is class# " + classNumber + ": ");
            classUnits[classNumber - 1] = userInput.nextDouble();
        }
        System.out.println();
        return classUnits;
    }

    public static double calculateUnWeightedGpa(double[] classGrades){
        double gradePointSum = 0.0;
        for(double gradePoint: classGrades)
            gradePointSum += gradePoint;
        return gradePointSum/amountOfClasses;
    }

    public static double calculateWeightedGpa(double[] classGrades,
                                              double[] classUnits){
        double unWeightedGpa = calculateUnWeightedGpa(classGrades);
        double amountOfCredits = 0.0;
        for(double course: classUnits)
            amountOfCredits += course;
        double unWeightedTimesCredits = unWeightedGpa * amountOfCredits;
        return unWeightedTimesCredits / amountOfCredits;
    }
}
