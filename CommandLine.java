import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Andrew Jarombek
 * @since 2/9/2017
 */
public class CommandLine {

    public static void runCMD() throws IOException {
        System.out.println("Andy Shell 1.0 \nType 'help' for more information");

        String input;
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.print(":>");
            input = console.readLine();

            if (input.equals("")) {
                continue;
            }

            List<String> commands = getCommands(input);

        }
    }

    private static List<String> getCommands(String input) {
        List<String> commands = new ArrayList<>();
        String[] words = input.split(" ");
        commands.addAll(Arrays.asList(words));
        return commands;
    }

    public static void main(String[] args) {
        runCMD();
    }
}
