package xin.skingorz.internet;

import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;

import okhttp3.FormBody;
import xin.skingorz.utils.GlobalVariable;
import xin.skingorz.utils.Encryption;
import xin.skingorz.utils.okhttpUtil;

public class Register {
    private GlobalVariable globalVariable = new GlobalVariable();
    private okhttpUtil okhttpUtil = new okhttpUtil();
    private Encryption encryption = new Encryption();

    /**
     * 获取注册验证码
     *
     * @param email 邮箱
     * @return服务器返回结果
     */
    public JSONObject get_code(String email) throws InterruptedException {
        String url = globalVariable.httpip + "/register/get_code?email=" + email;
        return okhttpUtil.httpGet(url);
    }

    /**
     * 注册用户
     * @param email 邮箱
     * @param code 验证码
     * @param username 用户名
     * @param password1 密码
     * @param password2 确认密码
     * @return服务器返回结果
     */
    public JSONObject add_user(String email, String code, String username, String password1, String password2) throws InterruptedException {
        FormBody formBody = null;
        try {
            formBody = new FormBody.Builder().add("email",email).add("code",code).add("username", username).add("password1", encryption.sha1(password1)).add("password2", encryption.sha1(password2)).build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String url = globalVariable.httpip + "/register/add_user/";
        return okhttpUtil.httpPost(url,formBody);
    }
}
