package satisfiabilitychecker;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Specifications {
    private int numOfVariables = 0;
    private int numOfClauses = 0;
    private int clauseLength = 0;
    private final ArrayList<Integer> givenAssignments = new ArrayList<>();
    private final ArrayList<Integer> listOfClauseLengths = new ArrayList<>();
    public void setParams (String fileName) throws IOException {
        File file = new File(System.getProperty("user.dir") + "/" + fileName + ".cnf");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        Pattern pattern = Pattern.compile("-?\\d+");
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("c")) {
                continue;
            } else if (line.startsWith("p")) {
                String[] storeNums = line.split("\\s+");
                this.numOfVariables = Integer.parseInt(storeNums[2]);
                this.numOfClauses = Integer.parseInt(storeNums[3]);
            } else {
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    int num = Integer.parseInt(matcher.group());
                    if (num != 0) {
                        this.clauseLength++;
                        this.givenAssignments.add(num);
                    } else {
                        this.listOfClauseLengths.add(clauseLength);
                        this.clauseLength = 0;
                    }
                }
            }
        }
        reader.close();
    }

    public int getNumOfVariables() {
        return this.numOfVariables;
    }
    public int getNumOfClauses () {
        return this.numOfClauses;
    }

    public ArrayList<Integer> getGivenAssignments() {
        return this.givenAssignments;
    }

    public ArrayList<Integer> getListOfClauseLengths() {
        return this.listOfClauseLengths;
    }
}
