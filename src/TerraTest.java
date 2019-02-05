import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;

public class TerraTest {

    public static final String MAIN_DELIMITER = "->";
    public static final String MAIN_DELIMITER_SPACED = " " + MAIN_DELIMITER + " ";
    public static final String EMAIL_DELIMITER = ",";

    public static void main(String[] args) {
        // Step 1. Preparations.
        File file = new File(args[0]);

        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.out.println("Specified input file not found. Exiting.");
        }

        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(args[1]));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (bufferedReader == null || bufferedWriter == null) {
            return;
        }

        // Step 2. Reading file and process it
        Hashtable<String, String> mailToUsersHashTable = new Hashtable<>();

        try {
            String line = bufferedReader.readLine(); // TODO: not meet "unlimited line" requirement
            while (line != null) {
                if (line.contains(MAIN_DELIMITER)) {
                    String[] lineData = line.split(MAIN_DELIMITER); // TODO: try to find more efficient way than create array of strings...
                    if (lineData.length < 2) {
                        System.out.println("Bad line, skipping");
                        line = bufferedReader.readLine();
                        continue;
                    }
                    String user = lineData[0].trim();
                    String concatedEmails = lineData[1];
                    List<String> emails = Arrays.stream(concatedEmails.split(EMAIL_DELIMITER)) // TODO: same as above
                            .map(email -> email.trim())
                            .collect(Collectors.toList());

                    boolean isUserFound = false;
                    String foundUser = null;
                    for (String email: emails) {
                        if (!isUserFound) {
                            String value = mailToUsersHashTable.putIfAbsent(email, user);
                            if (value != null) {
                                isUserFound = true;
                                foundUser = value;
                            }
                        } else {
                            mailToUsersHashTable.putIfAbsent(email, foundUser);
                        }
                    }
                }
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Step 3. Merging results
        Hashtable<String, String> userToMail = new Hashtable<>();
        //TODO would it be better to create stringBuilder for each line to save some ram?
        mailToUsersHashTable.forEach((mail, user) -> userToMail.merge(user, mail, (t, u) -> t.concat(", ").concat(u)));

        // Step 4. Writing to output file
        BufferedWriter finalBufferedWriter = bufferedWriter;
        userToMail.forEach((user, emails) -> {
            try {
                finalBufferedWriter.write(user);
                finalBufferedWriter.write(MAIN_DELIMITER_SPACED);
                finalBufferedWriter.write(emails);
                finalBufferedWriter.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        try {
            finalBufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
