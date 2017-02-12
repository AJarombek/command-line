import java.io.*;
import java.util.List;

/**
 * @author Andrew Jarombek
 * @since 2/8/2017
 */
public class SystemProcess {

    /**
     * Function for the default build system process command
     * @param args the command line arguments
     * @param directory the current directory path
     * @return the command line arguments
     * @throws IOException
     */
    public static List<String> buildProcess(List<String> args, String directory) throws IOException {

        ProcessBuilder pb = new ProcessBuilder(args);
        pb.directory(new File(directory));
        Process process = pb.start();
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String line;
        while ( (line = br.readLine()) != null) {
            System.out.println(line);
        }
        br.close();

        return args;
    }
}
