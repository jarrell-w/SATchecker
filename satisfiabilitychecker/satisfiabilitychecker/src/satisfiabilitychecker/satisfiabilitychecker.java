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
        int count = 0;
        int clauseLength = 0;
        String line;
        //Functionality to store assignments and assignments.
        ArrayList<Integer> givenAssignments = new ArrayList<Integer>();
        ArrayList<Integer> listOfClauseLengths = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                int num = Integer.parseInt(matcher.group());
                if (count == 0) {
                    numOfVariables = num;
                } else if (count == 1) {
                    numOfClauses = num;
                } else {
                    if (num != 0) {
                        clauseLength++;
                        givenAssignments.add(num);
                    } else {
                        listOfClauseLengths.add(clauseLength);
                        clauseLength = 0;
                    }
                }
                count++;
            }
        }
        reader.close();
        int variationCounter = 0;
        int maxVariations = calculateMaxVariations(numOfVariables);
        System.out.println(givenAssignments);
        boolean assignmentNotSatisfied = true;
        ArrayList<Integer> startingBinary = createBinaryOfN(numOfVariables);
        assignmentNotSatisfied = checkClauses(numOfClauses, numOfVariables, convertBinaryToTrueOrFalse(startingBinary), givenAssignments, listOfClauseLengths);
        variationCounter++;
        if(assignmentNotSatisfied == false) {
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
            System.out.println("This is torf: " + tOrFAssignment);
            assignmentNotSatisfied = (checkClauses(numOfClauses,numOfVariables,tOrFAssignment,givenAssignments, listOfClauseLengths));
            if (assignmentNotSatisfied == false) {
                System.out.println(tOrFAssignment);
                break;
            }
            startingBinary = new ArrayList<>(newBinaryAssignment);
        }
    }
    public static boolean checkClauses(int numOfClauses, int numOfVariables, ArrayList<Boolean> trueOrFalseArray, ArrayList<Integer> assignments, ArrayList<Integer> clauseLengthList) {
        ArrayList<Boolean> clauses = new ArrayList<Boolean>();
        ArrayList<Boolean> individualClause = new ArrayList<Boolean>();
        int marker = 0;
        for (int i = 0; i < numOfClauses; i++) {
            individualClause = new ArrayList<Boolean>();
            System.out.println("LengthList is: "+clauseLengthList.get(i)+ " "+ i + " Run of loop marker is: " + marker);
            for (int j = marker; j < marker + clauseLengthList.get(i); j++) {
                if (j >= assignments.size()) break;
                //System.out.println(assignments.get(j));
                //System.out.println(marker);
                int variableSelection = assignments.get(j);
                //System.out.println(variableSelection);
                boolean checkVar = true;
                if(variableSelection >= trueOrFalseArray.size())break;
                if(variableSelection > 0) {
                    //System.out.println("greater than zero: " + trueOrFalseArray.get(variableSelection - 1));
                    checkVar = trueOrFalseArray.get(variableSelection - 1);
                    individualClause.add(checkVar);
                } else {
                    variableSelection *= - 1;
                    if(variableSelection >= trueOrFalseArray.size())break;
                    //System.out.println("less than zero: " + trueOrFalseArray.get(variableSelection - 1));
                    checkVar = trueOrFalseArray.get(variableSelection - 1);
                    individualClause.add(!checkVar);
                }

               // System.out.println(marker);
            }
            //System.out.println(marker);
            marker += clauseLengthList.get(i);
            if (individualClause.contains(true)) {
                clauses.add(true);
            } else {
                clauses.add(false);
            }

        }
        //System.out.println(clauses);
        boolean failedPassedAssignment = clauses.contains(false);
        //System.out.println(failedPassedAssignment);
        return failedPassedAssignment;
    }

    public static ArrayList<Integer> createBinaryOfN(int numOfVariables) {
        //fill array of 0's
        ArrayList<Integer> startingAssignments = new ArrayList<Integer>();
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
        for (int i = 0; i < binaryArray.size(); i++) {
            if (binaryArray.get(i) == 0) {
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
            maxVariations *= 2; // multiply result by 2 each time through the loop
        }
        return maxVariations;
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
    * Note 2: If any clause evaluates to false move to next, because it will always be false. x
    * Note 3: first step create a binary number of n(number of variables).x
    * Note 3: Map each index in the array to a variable. I.e [var1, var2, var3, var4] x
    * Note 4: Convert binary number in the array to true and false (array?)
    * Note 5: Check clause to each number specified in file (if number is less than 1, multiple my -1 then check accordingly.
    * */
}


