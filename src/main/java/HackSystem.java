import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * Hack System.out to HackSystem.out.
 */
public class HackSystem {
    public static PrintStream out;

    static {
        try {
            out = new PrintStream(new File("./output/output.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
