package satisfiabilitychecker;
import java.util.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class satisfiabilitychecker {
    public static void main (String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the desired file name: ");
        String fileName = scanner.next();
        File file = new File(System.getProperty("user.dir") + "/" + fileName + ".cnf");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        Pattern pattern = Pattern.compile("-?\\d+"); // regular expression to match integers
        int numOfVariables = 0;
        int numOfClauses = 0;
        int clauseLength = 0;
        String line;
        //Functionality to store assignments and assignments.
        ArrayList<Integer> givenAssignments = new ArrayList<>();
        ArrayList<Integer> listOfClauseLengths = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("c")) {
                continue;
            } else if (line.startsWith("p")) {
                String[] storeNums = line.split("\\s+");
                numOfVariables = Integer.parseInt(storeNums[2]);
                numOfClauses = Integer.parseInt(storeNums[3]);
            } else {
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    int num = Integer.parseInt(matcher.group());
                    if (num != 0) {
                        clauseLength++;
                        givenAssignments.add(num);
                    } else {
                        listOfClauseLengths.add(clauseLength);
                        clauseLength = 0;
                    }
                }
            }
        }
        reader.close();

        System.out.println(givenAssignments);
        boolean assignmentNotSatisfied;
        ArrayList<Integer> startingBinary = createBinaryOfN(numOfVariables);
        assignmentNotSatisfied = checkClauses(numOfClauses, convertBinaryToTrueOrFalse(startingBinary), givenAssignments, listOfClauseLengths);
        if (assignmentNotSatisfied) {
            System.out.println("Unsatisfiable");
        }
    }
    public static boolean checkClauses(int numOfClauses, ArrayList<Boolean> trueOrFalseArray, ArrayList<Integer> assignments, ArrayList<Integer> clauseLengthList) {
        int numOfVariables = trueOrFalseArray.size();
        int maxVariations = calculateMaxVariations(numOfVariables);
        for (int i = 0; i < maxVariations; i++) {
            ArrayList<Boolean> currentAssignment = new ArrayList<>();
            int n = i;
            for (int j = 0; j < numOfVariables; j++) {
                currentAssignment.add(n % 2 == 1);
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
    public static int calculateMaxVariations(int numOfVariables) {
        int maxVariations = 1;
        for (int i = 1; i <= numOfVariables; i++) {
            maxVariations *= 2;
        }
        return maxVariations;
    }
}


