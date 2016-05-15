package com.mylife.tools;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.mylife.model.PictureData;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by whx on 2016/2/3.
 */
public class GetDataUtil {

    private static ArrayList<PictureData> list;

    public static JSONObject ParseJson(final String path, final String encode) {
        // TODO Auto-generated method stub
        HttpClient httpClient = new DefaultHttpClient();
        HttpParams httpParams = httpClient.getParams();
        // HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
        // HttpConnectionParams.setSoTimeout(httpParams, 10000);
        HttpPost httpPost = new HttpPost(path);
        try {
            HttpResponse httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                String result = EntityUtils.toString(httpResponse.getEntity(),
                        encode);
                JSONObject jsonObject = new JSONObject(result);
                return jsonObject;
            }

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            return null;

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } finally {
            if (httpClient != null)
                httpClient.getConnectionManager().shutdown();
        }
        return null;

    }

    public static ArrayList<PictureData> getPictureData(String url){

        JSONObject jsonObject;

        jsonObject = ParseJson(url,"utf-8");

        if(jsonObject == null){
            return null;
        }else{
            list = new ArrayList<>();
            Log.d("json response is--------",jsonObject+"");

            PictureData pictureData = new PictureData();

            pictureData.setText(jsonObject.optString("text"));
            //Log.d("-------------", jsonObject.optString("text"));

            pictureData.setPicture(jsonObject.optString("image"));
            //Log.d("-------------", jsonObject.optString("image"));

            list.add(pictureData);

            return list;
        }

    }
}
