package xin.skingorz.internet;

import org.json.JSONObject;

import xin.skingorz.utils.GlobalVariable;
import xin.skingorz.utils.okhttpUtil;

public class Friend {

    private GlobalVariable globalVariable = new GlobalVariable();
    private xin.skingorz.utils.okhttpUtil okhttpUtil = new okhttpUtil();

    public JSONObject add_user(String selfemail, String friendemail) throws InterruptedException {
        String url = globalVariable.httpip + "/friend/add_user?selfemail=" + selfemail + "&friendemail=" + friendemail;
        return okhttpUtil.httpGet(url);
    }
}
