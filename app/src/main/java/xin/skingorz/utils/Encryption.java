/**
 * 将字符串转换成哈希散列
 */
package xin.skingorz.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {

    /**
     * 将字符串转换为哈希散列
     *
     * @param data 带转换的字符串
     * @return 字符串的哈希散列
     * @throws NoSuchAlgorithmException
     */
    public static String sha1(String data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA1");
        md.update(data.getBytes());
        StringBuffer buf = new StringBuffer();
        byte[] bits = md.digest();
        for (int i = 0; i < bits.length; i++) {
            int a = bits[i];
            if (a < 0) a += 256;
            if (a < 16) buf.append("0");
            buf.append(Integer.toHexString(a));
        }
        System.out.println(buf.toString());
        return buf.toString();
    }
}
