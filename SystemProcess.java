import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrew Jarombek
 * @since 2/8/2017
 */
public class SystemProcess {

    public static String buildProcess(String arg) throws IOException {

        List<String> commands = new ArrayList<>();
        commands.add("Notepad.exe");
        commands.add("/P");
        commands.add("hello");

        ProcessBuilder pb = new ProcessBuilder(commands);
        java.lang.Process process = pb.start();
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String line;
        while ( (line = br.readLine()) != null) {
            System.out.println(line);
        }
        br.close();

        return line;
    }
}
