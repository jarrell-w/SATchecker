package satisfiabilitychecker;
import java.util.*;
import java.io.*;

public class satisfiabilitychecker {
    public static void main (String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the desired file name: ");
        String fileName = scanner.next();
        File file = new File(System.getProperty("user.dir") + "/" + fileName + ".cnf");
        Scanner sc = new Scanner(file);
        int numOfVariables = 0;
        int numOfClauses = 0;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.charAt(0) != 'c') {
                System.out.println(line);
                if (line.charAt(0) == 'p') {
                    numOfVariables = Character.getNumericValue(line.charAt(6));
                    numOfClauses = Character.getNumericValue(line.charAt(8));
                }
            }

        }
        System.out.println(numOfClauses + numOfVariables);

    }
    public int setClauses(int numOfClauses, int numOfVariables) {
        int[] clauses = new int[numOfClauses];

        return 0;
    }
}


