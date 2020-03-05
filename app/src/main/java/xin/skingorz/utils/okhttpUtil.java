package xin.skingorz.utils;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class okhttpUtil {

    public static final int SUCCESS = 200;
    public static final int ERROR = 400;
    public static final int PENDING = 300;


    private OkHttpClient client = new OkHttpClient();
    private JSONObject jsonObject1 = new JSONObject();
    private JSONObject jsonObject2 = new JSONObject();

    public JSONObject httpGet(String url) throws InterruptedException {
        final Request request = new Request.Builder().get().url(url).build();
        Call call = client.newCall(request);

        final int[] status = {PENDING};

        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Log.w("failuer", "onFailure");
                status[0] = ERROR;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                Log.w("response body", res);
                try {
                    jsonObject1 = new JSONObject(res);
                    status[0] = SUCCESS;
                } catch (JSONException e) {
                    status[0] = ERROR;
                    e.printStackTrace();
                }

            }
        });

        while (status[0] == PENDING ){
            Thread.sleep(1);
        }
        return jsonObject1;
    }


    public JSONObject httpPost(String url, FormBody formBody) throws InterruptedException {
        final Request request = new Request.Builder().url(url).post(formBody).build();
        Call call = client.newCall(request);
        final int[] status = {PENDING};

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("error", "onFailure");
                status[0] = ERROR;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String res = response.body().string();
                Log.w("response body", res);
                try {
                    jsonObject2 = new JSONObject(res);
                    Log.w("status", jsonObject2.getString("status"));
                    status[0] = SUCCESS;
                } catch (JSONException e) {
                    status[0] = ERROR;
                    e.printStackTrace();
                }
            }
        });

        while (status[0] == PENDING){
            Thread.sleep(1);
        }

        return jsonObject2;
    }

}
