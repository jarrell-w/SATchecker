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
        //Functionality to store assignments and assignments.
        ArrayList<Integer> givenAssignments = new ArrayList<Integer>();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.charAt(0) == 'p') {
                numOfVariables = Character.getNumericValue(line.charAt(6));
                numOfClauses = Character.getNumericValue(line.charAt(8));
                while(sc.hasNext()) {
                    try
                    {
                        int num = Integer.parseInt(sc.next());
                        if (num != 0) {
                            givenAssignments.add(num);
                        }
                    } catch (NumberFormatException ignored) {}
        //End Functionality to store assignments and assignments.
                }
            }
        }
        System.out.println(givenAssignments);

    }
    public int setClauses(int numOfClauses, int numOfVariables) {
        int[] clauses = new int[numOfClauses];
        //done
        return 0;
    }

    public ArrayList<Integer> createBinary(int numOfVariables) {
        //fill array of 0's
        ArrayList<Integer> startingAssignments = new ArrayList<Integer>();
        for (int i = 0; i < numOfVariables; i++) {
            startingAssignments.add(0);
        }
        return startingAssignments;
    }
    public ArrayList<Integer> generateBinaryAssignment(ArrayList<Integer> startingAssignment) {
        int lastIndexOfZero = startingAssignment.lastIndexOf(0);
        startingAssignment.set(lastIndexOfZero, 1);
        int lastIndexOfOne = startingAssignment.lastIndexOf(1);
        for(int i = lastIndexOfOne; i < startingAssignment.size(); i++) {
            startingAssignment.set(i, 0);
        }
        return startingAssignment;
    }

    public ArrayList<Boolean> convertBinaryToTrueOrFalse(ArrayList<Integer> binaryArray) {
        ArrayList <Boolean> trueOrFalseArray = new ArrayList<>();
        for (int i = 0; i < binaryArray.size(); i++) {
            if (binaryArray.get(i) == 0) {
                trueOrFalseArray.add(false);
            } else {
                trueOrFalseArray.add(true);
            }
        }
        return trueOrFalseArray;
    }
    /*in order to generate possible combinations, considering using binary addition.
    * 0000 -> false false false false
    * 0001 -> false false false true
    * 0011 -> false false true true
    * 0111 -> flase true true true.
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
    * Note 1: As long as there is one true in a clause it will always evaluate to true.
    * Note 2: If any clause evaluates to false move to next, because it will always be false.
    * Note 3: first step create a binary number of n(number of variables).x
    * Note 3: Map each index in the array to a variable. I.e [var1, var2, var3, var4] x
    * Note 4: Convert binary number in the array to true and false (array?)
    * Note 5: Check clause to each number specified in file (if number is less than 1, multiple my -1 then check accordingly.
    * */
}


