import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Andrew Jarombek
 * @since 2/9/2017
 */
public class DirectoryProcess {

    /**
     * Function to execute the cd command
     * @param args the command line arguments
     * @param currentPath the current directory path
     * @return the new directory path or null if the cd command was invalid
     */
    public static String changeDirectory(List<String> args, String currentPath) {
        if (args.size() > 2) {
            // We need to build the new directory that has spaces
            StringBuilder directory = new StringBuilder();
            for (int i = 1; i < args.size(); i++) {
                directory.append(args.get(i));
                directory.append(" ");
            }
            String directoryString = directory.substring(0, directory.length()-1);

            return validDirectory(directoryString, currentPath);
        } else if (args.size() == 1) {
            // The cd command alone goes to the home directory
            return System.getProperty("user.dir");
        } else {
            return validDirectory(args.get(1), currentPath);
        }
    }

    /**
     * Helper function to check for directory validity
     * @param path the directory change
     * @param currentPath the current path
     * @return the new directory or null if it is invalid
     */
    private static String validDirectory(String path, String currentPath) {
        boolean isValid = (Files.isDirectory(Paths.get(currentPath + path)));
        if (!isValid) {
            if (Files.isDirectory(Paths.get(currentPath + "\\" + path))) {
                return currentPath + "\\" + path;
            }
        } else {
            return currentPath + path;
        }
        return null;
    }

    /**
     * Function that executes the cd.. command
     * @param path the current directory path
     * @return the new directory path or null if you cant go back further
     */
    public static String backDirectory(String path) {
        for (int i = path.length()-1; i >= 0; i--) {
            if (path.charAt(i) == '\\') {
                return path.substring(0, i);
            }
        }
        return null;
    }
}
