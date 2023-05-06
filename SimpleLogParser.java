/* Parse arbitrary logs 
    Collect logs (of any kind) and write a parser 
    which pulls out specific details (domains,  
    executable names, timestamps etc.) 
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser {

    public static void parseLogs(String logFilePath, String regex) {
        try {
            // Open the log file
            BufferedReader bufferedReader = new BufferedReader(new FileReader(logFilePath));

            // Define the regular expression pattern
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

            // Read the log file line by line
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // Use the regular expression pattern to match the line
                Matcher matcher = pattern.matcher(line);
                
                // If the pattern matches the line, process the data
                if (matcher.find()) {
                    System.out.println("Match found: " + matcher.group());
                    // Extract any relevant information from the matched line
                }
            }

            // Close the file reader
            bufferedReader.close();
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
    public static void main(String args[]){

        String filePath = "testfile.log";
        String regex = ".*\\.test_extension$";
        
        LogParser.parseLogs(filePath, regex);
    }
}


