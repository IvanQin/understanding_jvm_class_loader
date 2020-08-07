/**
 * Util class to modify bytes.
 */
public class ByteUtils {

    public static int bytes2Int(byte[] b, int start, int len) {
        int sum = 0;
        for (int i = start; i < len + start; i++) {
            int val = ((int) b[i]) & 0xff;
            sum = (sum << 8) + val;
        }
        return sum;
    }

    public static byte[] int2Bytes(int value, int len) {
        byte[] b = new byte[len];
        for (int i = len - 1; i >= 0; i--) {
            b[i] = (byte) (value & 0xff);
            value >>= 8;
        }
        return b;
    }


    public static String bytes2String(byte[] b, int start, int len) {
        return new String(b, start, len);
    }

    public static byte[] string2Bytes(String str) {
        return str.getBytes();
    }

    public static int byte2Int2(byte[] b, int start, int len) {
        int sum = 0;
        int end = start + len;
        for (int i = start; i < end; i++) {
            int val = ((int) b[i]) & 0xff;
            val <<= (--len) * 8;
            sum += val;
        }
        return sum;
    }

    /**
     * Replace {@param len} bytes beginning at the {@param offset} of original bytes with {@param replaceBytes}
     */
    public static byte[] bytesReplace(byte[] originalBytes, int offset, int len, byte[] replaceBytes) {
        byte[] newBytes = new byte[originalBytes.length + replaceBytes.length - len];
        System.arraycopy(originalBytes, 0, newBytes, 0, offset);
        System.arraycopy(replaceBytes, 0, newBytes, offset, replaceBytes.length);
        System.arraycopy(originalBytes, offset + len, newBytes, offset + replaceBytes.length, originalBytes.length - offset - len);
        return newBytes;
    }

    public static void test(Integer... integers) {
        for (Integer integer : integers) {
            System.out.println(integer);
        }
    }
}
