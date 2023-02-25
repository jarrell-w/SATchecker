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
    /*in order to generate possible combinations, considering using binary addition.
    * 0000 -> false false false false
    * 0001 -> false false false true
    * 0011 -> false false true true
    * 0111 -> flase true true.
    * Start with all zeros and stop when there's all 1's in the amount of variables
    * We are adding 1 at a time.
    * When adding a 1, flip the right most zero to a 1, and everything after flip to a zero
    * Now assignment can be stored in integer.
    * 000
    * 001
    * 010
    * 011
    * 100
    * 101
    * 110
    * 111
    *
    * */
}


