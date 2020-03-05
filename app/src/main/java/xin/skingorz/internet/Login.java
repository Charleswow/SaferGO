package xin.skingorz.internet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;

import okhttp3.FormBody;
import xin.skingorz.Bean.User;
import xin.skingorz.utils.Encryption;
import xin.skingorz.utils.GlobalVariable;
import xin.skingorz.utils.okhttpUtil;

public class Login {
    private GlobalVariable globalVariable = new GlobalVariable();
    private xin.skingorz.utils.okhttpUtil okhttpUtil = new okhttpUtil();
    private Encryption encryption = new Encryption();
    private Search search = new Search();
    private xin.skingorz.Dao.UserDao userDao = new xin.skingorz.Dao.UserDao();


    /**
     * 登录
     * @param loginname 用户名
     * @param password  密码
     * @return
     * @throws NoSuchAlgorithmException
     */
    public JSONObject login(String loginname, String password) throws NoSuchAlgorithmException, JSONException, InterruptedException {
        FormBody formBody = new FormBody.Builder()
                .add("loginname", loginname)
                .add("password", encryption.sha1(password)).build();
        String url = globalVariable.httpip + "/login/";

        JSONObject serverMes = okhttpUtil.httpPost(url, formBody);

//        if (serverMes.getInt("status") == 200){
//            //添加个人信息到sqlite
//            JSONObject self = search.user(loginname);
//            User user = new User();
//            user.setAge(self.getString("age"));
//            user.setBirthPlace(self.getString("birthplace"));
//            user.setCreated_at(self.getString("created_at"));
//            user.setEmail(self.getString("email"));
//            user.setEmergencyPhone(self.getString("emergencyPhone"));
//            user.setImageUrl(self.getString("imageUrl"));
//            user.setIndividuality(self.getString("individuality"));
//            user.setSex(self.getString("sex"));
//            user.setMobilePhone(self.getString("mobilePhone"));
//            user.setLivePlace(self.getString("livePlace"));
//            user.setUsername(self.getString("username"));
//            user.setUser(self.getString("email"));
//            userDao.insertUser(user);
//            //添加好友信息到sqlite
//            JSONArray friendsArray = search.all_friend(loginname);
//            for(int i = 0; i < friendsArray.length(); i++){
//                JSONObject friends = friendsArray.getJSONObject(i);
//                JSONObject friendDetail = search.user(friends.getString("friends_email"));
//                User friend = new User();
//                friend.setUser(self.getString("email"));
//                friend.setAge(friendDetail.getString("age"));
//                friend.setBirthPlace(friendDetail.getString("birthplace"));
//                friend.setEmail(friendDetail.getString("email"));
//                friend.setEmergencyPhone(friendDetail.getString("emergencyPhone"));
//                friend.setImageUrl(friendDetail.getString("imageUrl"));
//                friend.setIndividuality(friendDetail.getString("individuality"));
//                friend.setSex(friendDetail.getString("sex"));
//                friend.setMobilePhone(friendDetail.getString("mobilePhone"));
//                friend.setLivePlace(friendDetail.getString("livePlace"));
//                friend.setUsername(friendDetail.getString("username"));
//                friend.setCreated_at(friends.getString("addTime"));
//                userDao.insertUser(friend);
//            }
//        }

        return serverMes;
    }
}
