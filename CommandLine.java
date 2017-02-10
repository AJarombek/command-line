import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author Andrew Jarombek
 * @since 2/9/2017
 */
public class CommandLine {

    private Map<Integer, Pair<String, List<String>>> history;
    private int count;

    public CommandLine() {
        history = new HashMap<>();
        count = 0;
    }

    public void runCMD() {
        System.out.println("Andy Shell 1.0 \nType 'help' for more information");

        String input;
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.print("AndySh:~$ ");
            try {
                input = console.readLine();
            } catch (IOException e) {
                System.out.println("ERROR: Failed to Read Command");
                continue;
            }

            if (input.equals("")) {
                continue;
            }

            // First get all the commands in list form
            List<String> commands = getCommands(input);

            // Then try to execute the command
            commands = execute(commands);

            // If the command was valid, add it to the history
            if (commands != null) {
                history.put(count++, new Pair<>(input, commands));
            }
        }
    }

    private List<String> execute(List<String> commands) {
        String mainCommand = commands.get(0);

        switch (mainCommand) {
            case "help":
                return helpCommand(commands);
            case "cd":
                return null;
            case "history":
                return displayHistoryCommand(commands);
            case "!!":
                return execute(history.get(count-1).getValue());
            default:
                if (mainCommand.charAt(0) == '!') {
                    return historyCommand(commands);
                } else {
                    try {
                        return SystemProcess.buildProcess(commands);
                    } catch (IOException e) {
                        System.out.println("ERROR: Invalid Command.");
                        return null;
                    }
                }
        }
    }

    private List<String> getCommands(String input) {
        List<String> commands = new ArrayList<>();
        String[] words = input.split(" ");
        commands.addAll(Arrays.asList(words));
        return commands;
    }

    private List<String> displayHistoryCommand(List<String> args) {
        if (args.size() > 1) {
            System.out.println("No Arguments for History Command Allowed");
            return null;
        } else {
            System.out.println();
            for (int i=0; i < count; i++) {
                Pair<String, List<String>> pair = history.get(i);
                System.out.println(i + " " + pair.getKey());
            }
            System.out.println();
            return args;
        }
    }

    private List<String> historyCommand(List<String> args) {
        if (args.size() > 1) {
            System.out.println("No Arguments for !<integer value> Command Allowed");
            return null;
        } else {
            String command = args.get(0);
            int commandNum = Integer.parseInt(command.substring(1));
            Pair<String, List<String>> pair = history.get(commandNum);
            return execute(pair.getValue());
        }
    }

    private List<String> helpCommand(List<String> args) {
        if (args.size() > 1) {
            System.out.println("No Arguments for Help Command Allowed");
            return null;
        } else {
            System.out.println("Help Screen");
            return args;
        }
    }

    public static void main(String[] args) {
        CommandLine commandLine =  new CommandLine();
        commandLine.runCMD();
    }
}
