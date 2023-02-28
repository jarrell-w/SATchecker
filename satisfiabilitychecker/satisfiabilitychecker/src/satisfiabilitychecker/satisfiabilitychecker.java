package satisfiabilitychecker;
import java.util.*;
import java.io.IOException;
import java.util.ArrayList;
import java.math.BigInteger;

public class satisfiabilitychecker {
    public static void main (String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the desired file name: ");
        String fileName = scanner.next();
        Specifications getSpecs = new Specifications();
        getSpecs.setParams(fileName);

        int numOfVariables = getSpecs.getNumOfVariables();
        int numOfClauses = getSpecs.getNumOfClauses();
        boolean assignmentNotSatisfied;

        ArrayList<Integer> givenAssignments = getSpecs.getGivenAssignments();
        ArrayList<Integer> listOfClauseLengths = getSpecs.getListOfClauseLengths();
        System.out.println(givenAssignments);

        ArrayList<Integer> startingBinary = createBinaryOfN(numOfVariables);
        assignmentNotSatisfied = checkClauses(numOfClauses, convertBinaryToTrueOrFalse(startingBinary), givenAssignments, listOfClauseLengths);
        if (assignmentNotSatisfied) {
            System.out.println("Unsatisfiable");
        }
    }
    public static boolean checkClauses(int numOfClauses, ArrayList<Boolean> trueOrFalseArray, ArrayList<Integer> assignments, ArrayList<Integer> clauseLengthList) {
        int numOfVariables = trueOrFalseArray.size();
        int maxVariations = calculateMaxVariations(numOfVariables);
        ArrayList<Boolean> currentAssignment = new ArrayList<>(Collections.nCopies(numOfVariables, false)); // initialize to all false
        for (int i = 0; i < maxVariations; i++) {
            int n = i;
            for (int j = 0; j < numOfVariables; j++) {
                currentAssignment.set(j, n % 2 == 1);
                n /= 2;
            }
            boolean allClausesSatisfied = true;
            int marker = 0;
            for (int j = 0; j < numOfClauses; j++) {
                ArrayList<Boolean> individualClause = new ArrayList<>();
                int clauseLength = clauseLengthList.get(j);
                for (int k = marker; k < marker + clauseLength; k++) {
                    int variableSelection = assignments.get(k);
                    if (variableSelection > 0) {
                        individualClause.add(currentAssignment.get(variableSelection - 1));
                    } else {
                        individualClause.add(!currentAssignment.get(-variableSelection - 1));
                    }
                }
                marker += clauseLength;
                boolean clauseSatisfied = false;
                for (boolean clause : individualClause) {
                    if (clause) {
                        clauseSatisfied = true;
                        break;
                    }
                }
                if (!clauseSatisfied) {
                    allClausesSatisfied = false;
                    break;
                }
            }
            if (allClausesSatisfied) {
                displayClause(currentAssignment);
                return false;
            }
        }
        return true;
    }
    public static ArrayList<Integer> createBinaryOfN(int numOfVariables) {
        ArrayList<Integer> startingAssignments = new ArrayList<>();
        for (int i = 0; i < numOfVariables; i++) {
            startingAssignments.add(0);
        }
        return startingAssignments;
    }
    public static int calculateMaxVariations(int numOfVariables) {
        //switched to arbitrary-precision integer arithmetic
        BigInteger maxVariations = BigInteger.ONE;
        for (int i = 1; i <= numOfVariables; i++) {
            maxVariations = maxVariations.multiply(BigInteger.valueOf(2));
        }
        return maxVariations.intValue();
    }

    public static ArrayList<Boolean> convertBinaryToTrueOrFalse(ArrayList<Integer> binaryArray) {
        ArrayList <Boolean> trueOrFalseArray = new ArrayList<>();
        for (Integer integer : binaryArray) {
            if (integer == 0) {
                trueOrFalseArray.add(false);
            } else {
                trueOrFalseArray.add(true);
            }
        }
        return trueOrFalseArray;
    }
    public static void  displayClause (ArrayList<Boolean> clause) {
        System.out.println(clause);
    }
}


