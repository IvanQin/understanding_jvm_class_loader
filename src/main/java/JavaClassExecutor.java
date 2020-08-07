import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class JavaClassExecutor {

    /**
     * Invoke the main method of the class using java reflection.
     *
     * @param classBytes .class file of the class to be invoked
     * @param args       args to the main method of the class to be invoked
     */
    public static void execute(byte[] classBytes, String[] args) {
        HotSwapClassLoader hotSwapClassLoader = new HotSwapClassLoader();
        MyClassModifier classModifier = new MyClassModifier(classBytes);
        Class myClass = hotSwapClassLoader
                .loadByte(classModifier.modifyUTF8Constant("java/lang/System", "HackSystem"));
        try {
            Method mainMethod = myClass.getMethod("main", args.getClass());
            mainMethod.invoke(null, new Object[]{args});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
