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
        while (sc.hasNextLine()) {
            System.out.println(sc.nextLine());
        }

    }
    }
