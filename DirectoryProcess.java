import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Andrew Jarombek
 * @since 2/9/2017
 */
public class DirectoryProcess {

    public static String changeDirectory(List<String> args, String currentPath) {
        if (args.size() > 2) {
            System.out.println("ERROR: Invalid CD Command.  Too Many Arguments");
            return null;
        } else if (args.size() == 1) {
            return System.getProperty("user.dir");
        } else {
            if (validDirectory(args.get(1), currentPath))
                return currentPath + args.get(1);
            else {
                System.out.println("ERROR: Invalid Path Entered");
                return null;
            }
        }
    }

    private static boolean validDirectory(String path, String currentPath) {
        return (Files.isDirectory(Paths.get(currentPath + path)));
    }
}
