package xin.skingorz.internet;

import android.support.v4.app.INotificationSideChannel;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import xin.skingorz.utils.GlobalVariable;
import xin.skingorz.utils.okhttpUtil;

public class Search {
    private GlobalVariable globalVariable = new GlobalVariable();
    private xin.skingorz.utils.okhttpUtil okhttpUtil = new okhttpUtil();


    /**
     * 搜索某个用户的所有信息
     * @param email_username
     * @return
     * @throws InterruptedException
     */
    public JSONObject user(String email_username) throws InterruptedException {
        String url = globalVariable.httpip + "/search/user?email_username=" + email_username;
        return okhttpUtil.httpGet(url);
    }

    /**
     * 从网络获取所有的好友数据
     * @param email 邮箱地址
     * @return状态码
     * @throws JSONException
     * @throws InterruptedException
     */
    public JSONArray all_friend(String email) throws JSONException, InterruptedException {
        String url = globalVariable.httpip + "/search/all_friend?email=" + email;
        Log.w("getStringfriends",okhttpUtil.httpGet(url).getString("friends"));
        return okhttpUtil.httpGet(url).getJSONArray("friends");
    }
}
