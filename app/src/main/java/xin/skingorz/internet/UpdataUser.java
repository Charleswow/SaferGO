/**
 * 更新用户信息
 */
package xin.skingorz.internet;

import org.json.JSONObject;

import xin.skingorz.utils.GlobalVariable;
import xin.skingorz.utils.okhttpUtil;

public class UpdataUser {
    private GlobalVariable globalVariable = new GlobalVariable();
    private xin.skingorz.utils.okhttpUtil okhttpUtil = new okhttpUtil();


    public JSONObject username(String email, String username) throws InterruptedException {
        String url = globalVariable.httpip+ "/updataUser/username?email=" + email + "&username=" + username;
        return okhttpUtil.httpGet(url);
    }
    public JSONObject age(String email, String age) throws InterruptedException {
        String url = globalVariable.httpip+ "/updataUser/age?email=" + email + "&age=" + age;
        return okhttpUtil.httpGet(url);
    }
    public JSONObject sex(String email, String sex) throws InterruptedException {
        String url = globalVariable.httpip+ "/updataUser/sex?email=" + email + "&sex=" + sex;
        return okhttpUtil.httpGet(url);
    }
    public JSONObject mobilePhone(String email, String mobilePhone) throws InterruptedException {
        String url = globalVariable.httpip+ "/updataUser/mobilePhone?email=" + email + "&mobilePhone=" + mobilePhone;
        return okhttpUtil.httpGet(url);
    }
    public JSONObject individuality(String email, String individuality) throws InterruptedException {
        String url = globalVariable.httpip+ "/updataUser/individuality?email=" + email + "&individuality=" + individuality;
        return okhttpUtil.httpGet(url);
    }
    public JSONObject birthplace(String email, String birthplace) throws InterruptedException {
        String url = globalVariable.httpip+ "/updataUser/birthplace?email=" + email + "&birthplace=" + birthplace;
        return okhttpUtil.httpGet(url);
    }
    public JSONObject livePlace(String email, String livePlace) throws InterruptedException {
        String url = globalVariable.httpip+ "/updataUser/livePlace?email=" + email + "&livePlace=" + livePlace;
        return okhttpUtil.httpGet(url);
    }
    public JSONObject emergencyPhone(String email, String emergencyPhone) throws InterruptedException {
        String url = globalVariable.httpip+ "/updataUser/emergencyPhone?email=" + email + "&emergencyPhone=" + emergencyPhone;
        return okhttpUtil.httpGet(url);
    }

}
