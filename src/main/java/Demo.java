import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Main entrance of this project.
 */
public class Demo {
    public static void main(String[] args) throws IOException {
        // make sure you customize the .class path to where you generated it
        InputStream inputStream = new FileInputStream("./src/main/resources/ClassToHack.class");
        try {
            byte[] classBytes = new byte[inputStream.available()];
            inputStream.read(classBytes);
            JavaClassExecutor.execute(classBytes, new String[]{"hello", "world"});
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
        }
    }
}
