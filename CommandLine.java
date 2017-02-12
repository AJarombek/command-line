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
    private String directory;

    public CommandLine() {
        history = new HashMap<>();
        count = 0;
        directory = System.getProperty("user.dir");
    }

    /**
     * The main method for starting a command line
     */
    public void runCMD() {
        System.out.println("Andy Shell 1.0 \nType 'help' for more information");

        String input;
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.print("AndySh " + directory + "$ ");
            try {
                input = console.readLine();
            } catch (IOException e) {
                System.out.println("ERROR: Failed to Read Command");
                continue;
            }

            if (input.equals("")) {
                continue;
            }

            if (input.equals("exit")) {
                System.out.println("Goodbye!");
                break;
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

    /**
     * Method for executing a command from the command line
     * @param commands a list of the entered commands
     * @return the commands list if the command was valid, null otherwise
     */
    private List<String> execute(List<String> commands) {
        String mainCommand = commands.get(0);

        switch (mainCommand) {
            case "help":
                return helpCommand(commands);
            case "cd":
                String newPath = DirectoryProcess.changeDirectory(commands, directory);
                if (newPath == null) {
                    System.out.println("ERROR: Invalid Directory Entered");
                    return null;
                } else {
                    directory = newPath;
                    return commands;
                }
            case "cd..":
                if (commands.size() > 1) {
                    System.out.println("ERROR: No Arguments allowed with cd.. command");
                    return null;
                } else {
                    String backPath = DirectoryProcess.backDirectory(directory);

                    if (backPath == null) {
                        System.out.println("ERROR: No Directory to move back to");
                        return null;
                    }

                    directory = backPath;
                    return commands;
                }
            case "history":
                return displayHistoryCommand(commands);
            case "!!":
                if (count >= 1)
                    return execute(history.get(count-1).getValue());
                else {
                    System.out.println("ERROR: The history is empty");
                    return null;
                }
            default:
                if (mainCommand.charAt(0) == '!') {
                    return historyCommand(commands);
                } else {
                    try {
                        return SystemProcess.buildProcess(commands, directory);
                    } catch (IOException e) {
                        System.out.println("ERROR: Invalid Command.");
                        return null;
                    }
                }
        }
    }

    /**
     * Helper method to get the commands in list form
     * @param input the command line input String
     * @return the list of commands
     */
    private List<String> getCommands(String input) {
        List<String> commands = new ArrayList<>();
        String[] words = input.split(" ");
        commands.addAll(Arrays.asList(words));
        return commands;
    }

    /**
     * Method for the history command
     * @param args the command line arguments
     * @return the commands list if the command was valid, null otherwise
     */
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

    /**
     * Method for the !<integer value> command
     * @param args the command line arguments
     * @return the commands list if the command was valid, null otherwise
     */
    private List<String> historyCommand(List<String> args) {
        if (args.size() > 1) {
            System.out.println("No Arguments for !<integer value> Command Allowed");
            return null;
        } else {
            String command = args.get(0);

            // Only try to find the history index if the command is an integer
            if (command.substring(1).matches("\\d+")) {
                int commandNum = Integer.parseInt(command.substring(1));

                // Make sure the index points to a valid history pair
                if (commandNum > count - 1) {
                    System.out.println("Illegal Index !<index> Entered");
                    return null;
                }

                Pair<String, List<String>> pair = history.get(commandNum);
                return execute(pair.getValue());
            } else {
                System.out.println("Illegal String Index !<index> Entered");
                return null;
            }
        }
    }

    /**
     * Method for the help command
     * @param args the command line arguments
     * @return the commands list if the command was valid, null otherwise
     */
    private List<String> helpCommand(List<String> args) {
        if (args.size() > 1) {
            System.out.println("No Arguments for Help Command Allowed");
            return null;
        } else {
            System.out.println("\nCOMMANDS");
            System.out.println("\nhistory - Display the full command history.");
            System.out.println("\n!! - Execute the previous valid command.");
            System.out.println("!<index> - Execute a previous command by its historical index.");
            System.out.println("\ncd {path} - Change directories with a designated path.");
            System.out.println("cd.. - Move to parent directory.");
            System.out.println("\nexit - Leave the command line.");
            System.out.println("Any other command will be executed as a system process.\n");

            return args;
        }
    }

    public static void main(String[] args) {
        CommandLine commandLine =  new CommandLine();
        commandLine.runCMD();
    }
}
