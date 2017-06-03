package kz.ally.okhttp.decode;

/**
 * 作者：陈磊
 * 日期：16/3/1
 */
public class RC4 {
    /**
     * 字符串加密
     */
    private static final int STATE_LENGTH = 256;

    static private byte[] codes = new byte[256];

    static {
        for (int i = 0; i < 256; i++)
            codes[i] = -1;
        for (int i = 'A'; i <= 'Z'; i++)
            codes[i] = (byte) (i - 'A');
        for (int i = 'a'; i <= 'z'; i++)
            codes[i] = (byte) (26 + i - 'a');
        for (int i = '0'; i <= '9'; i++)
            codes[i] = (byte) (52 + i - '0');
        codes['+'] = 62;
        codes['/'] = 63;
    }

    /**
     * RC4加密算法
     *
     * @param key
     * @param plainData
     * @return
     */
    public static byte[] RC4encrypt(byte[] key, byte[] plainData) {
        //Initialize the statebox and keybox.
        //The keybox will be filled using the key indicating by the parameter.
        int[] intKey = fromByteArray(key);
        int[] stateBox = new int[STATE_LENGTH];
        int[] keyBox = new int[STATE_LENGTH];
        int i = 0, j;
        for (; i < STATE_LENGTH; i++) {
            stateBox[i] = i;
            keyBox[i] = intKey[i % key.length];
        }

        //Permute the statebox using the value of keybox.
        i = 0;
        j = 0;
        int temp;
        for (; i < STATE_LENGTH; i++) {
            j = (j + stateBox[i] + keyBox[i]) % STATE_LENGTH;
            temp = stateBox[i];
            stateBox[i] = stateBox[j];
            stateBox[j] = temp;
        }

        //Generate key stream from the statebox and encrypt data.
        i = 0;
        j = 0;
        int tempKey;
        byte[] cipher = new byte[plainData.length];
        for (int k = 0; k < plainData.length; k++) {
            i = (i + 1) % STATE_LENGTH;
            j = (j + stateBox[i]) % STATE_LENGTH;
            temp = stateBox[i];
            stateBox[i] = stateBox[j];
            stateBox[j] = temp;
            //Get the encrypt key.
            tempKey = stateBox[(stateBox[i] + stateBox[j]) % STATE_LENGTH];
            //encrypt data and store the cipher in the cipher array.
            cipher[k] = (byte) (tempKey ^ plainData[k]);
        }
        return cipher;
    }

    private static int[] fromByteArray(byte[] array) {
        int[] intArray = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            intArray[i] = array[i] >= 0 ? array[i] : (array[i] + 256);
        }
        return intArray;
    }
}
