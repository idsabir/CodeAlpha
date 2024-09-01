import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class Students_gradeCalculate {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Double> grades = new ArrayList<>();
        System.out.println("Enter grades (type 'complete' to finish):");

        while (true) {
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("complete")) {
                break;
            }
            try {
                double grade = Double.parseDouble(input);
                grades.add(grade);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.! Please enter a valid number or 'complete' to finish.");
            }
        }

        if (grades.isEmpty()) {
            System.out.println("No grades were entered.");
            return;
        }

        double sum = 0;
        double highest = Collections.max(grades);
        double lowest = Collections.min(grades);

        for (double grade : grades) {
            sum += grade;
        }

        double average = sum / grades.size();
        System.out.println("Average Score: "+average+"\nHighest score: "+highest+"\nLowest score: "+lowest );
    }
}

