public class MyClassModifier {

    // offset in constant pool
    private static final int CONSTANT_POOL_COUNT_INDEX = 8;

    // tag for CONSTANT_UTF8_INFO
    private static final int CONSTANT_UTF8_INFO_TAG = 1;

    // besides CONSTANT_Utf8_info, other constant has fixed length
    private static final int[] CONSTANT_ITEM_LENGTH = {-1, -1, -1, 5, 5, 9, 9, 3, 3, 5, 5, 5, 5};

    private static final int u1 = 1;

    private static final int u2 = 2;

    private byte[] mClassBytes;

    public MyClassModifier(byte[] classBytes) {
        mClassBytes = classBytes;
    }

    /**
     * Replace {@param oldStr} in {@link MyClassModifier#mClassBytes} with {@param newStr}.
     * <p>
     * To debug {@link MyClassModifier#mClassBytes}, you may use linux command {@code od -t x1 <your-class-name>.class}
     * to dump the .class file to dex.
     *
     * The format of CONSTANT_Utf8_info is like:
     * | tag   | length N | content |
     * |1 byte | 2 bytes  | N bytes |
     *
     * @param oldStr old string needs to be replaced
     * @param newStr new string which is used to replace old string
     * @return new byte array after replacement
     */
    public byte[] modifyUTF8Constant(String oldStr, String newStr) {
        int constantPoolCount = getConstantPoolCount();
        int offset = CONSTANT_POOL_COUNT_INDEX + u2;
        for (int i = 0; i < constantPoolCount; i++) {
            int tag = ByteUtils.bytes2Int(mClassBytes, offset, u1);
            if (tag == CONSTANT_UTF8_INFO_TAG) {
                // the type of this constant is CONSTANT_Utf8_info
                int len = ByteUtils.bytes2Int(mClassBytes, offset + u1, u2);
                String s = ByteUtils.bytes2String(mClassBytes, offset + u1 + u2, len);
                if (s.equalsIgnoreCase(oldStr)) {
                    byte[] newStrInBytes = ByteUtils.string2Bytes(newStr);
                    // it's also applicable for replace newStrInBytes.length with newStr.length()
                    // because every character in String takes exact 1 byte space
                    byte[] newStrLengthInBytes = ByteUtils.int2Bytes(newStrInBytes.length, u2);
                    // replace length info
                    mClassBytes = ByteUtils.bytesReplace(mClassBytes, offset + u1, u2, newStrLengthInBytes);
                    // replace content info
                    mClassBytes = ByteUtils.bytesReplace(mClassBytes, offset + u1 + u2, len, newStrInBytes);
                    return mClassBytes;
                } else {
                    offset += u1 + u2 + len;
                }
            } else {
                offset += CONSTANT_ITEM_LENGTH[tag];
            }
        }
        return mClassBytes;
    }

    private int getConstantPoolCount() {
        return ByteUtils.bytes2Int(mClassBytes, CONSTANT_POOL_COUNT_INDEX, u2);
    }
}
