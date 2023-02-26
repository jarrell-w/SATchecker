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
        int variationCounter = 0;
        int maxVariations = calculateMaxVariations(numOfVariables);
        System.out.println(givenAssignments);
        boolean assignmentNotSatisfied;
        ArrayList<Integer> startingBinary = createBinaryOfN(numOfVariables);
        assignmentNotSatisfied = checkClauses(numOfClauses, convertBinaryToTrueOrFalse(startingBinary), givenAssignments, listOfClauseLengths);
        variationCounter++;
        if(!assignmentNotSatisfied) {
            displayClause(convertBinaryToTrueOrFalse(createBinaryOfN(numOfVariables)));
        }


        while(assignmentNotSatisfied) {
            variationCounter++;
            if (variationCounter == maxVariations + 1) {
                System.out.println("Not Satisfiable");
                break;
            }
            System.out.println("This is check:" + variationCounter);
            ArrayList <Integer> newBinaryAssignment = generateBinaryAssignment(startingBinary);
            ArrayList <Boolean> tOrFAssignment = convertBinaryToTrueOrFalse(newBinaryAssignment);
            assignmentNotSatisfied = (checkClauses(numOfClauses,tOrFAssignment,givenAssignments, listOfClauseLengths));
            if (!assignmentNotSatisfied) {
                System.out.println(tOrFAssignment);
                break;
            }
            startingBinary = new ArrayList<>(newBinaryAssignment);
        }
    }
    public static boolean checkClauses(int numOfClauses, ArrayList<Boolean> trueOrFalseArray, ArrayList<Integer> assignments, ArrayList<Integer> clauseLengthList) {
        ArrayList<Boolean> clauses = new ArrayList<>();
        ArrayList<Boolean> individualClause;
        int marker = 0;
        for (int i = 0; i < numOfClauses; i++) {

            individualClause = new ArrayList<>();
            int clauseLength = clauseLengthList.get(i);
            for (int j = marker; j < (marker + clauseLength); j++) {

                int variableSelection = assignments.get(j);
                boolean checkVar;
                if(variableSelection > 0) {

                    checkVar = trueOrFalseArray.get(variableSelection - 1);
                    individualClause.add(checkVar);
                } else {
                    variableSelection *= - 1;

                    checkVar = trueOrFalseArray.get(variableSelection - 1);
                    individualClause.add(!checkVar);
                }

            }
            marker += clauseLengthList.get(i);
            if (individualClause.contains(true)) {
                clauses.add(true);
            } else {
                clauses.add(false);
            }
        }

        return clauses.contains(false);
    }

    public static ArrayList<Integer> createBinaryOfN(int numOfVariables) {
        ArrayList<Integer> startingAssignments = new ArrayList<>();
        for (int i = 0; i < numOfVariables; i++) {
            startingAssignments.add(0);
        }
        return startingAssignments;
    }
    public static ArrayList<Integer> generateBinaryAssignment(ArrayList<Integer> startingAssignment) {
        if(startingAssignment.contains(0)) {
            int lastIndexOfZero = startingAssignment.lastIndexOf(0);
            startingAssignment.set(lastIndexOfZero, 1);
            for(int i = lastIndexOfZero + 1; i < startingAssignment.size(); i++) {
                startingAssignment.set(i, 0);
            }
            return startingAssignment;
        } else {
            return startingAssignment;
        }
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


