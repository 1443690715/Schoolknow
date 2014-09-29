package com.pw.schoolknow.push;

import org.json.JSONException;
import org.json.JSONObject;

public class PushUtils {
	
	//��ȡ�ͻ���userId
	public static String getUserid(String content){
		
		JSONObject jsonContent = null;
		JSONObject params=null;
		try {
			jsonContent = new JSONObject(content);
			params = jsonContent
					.getJSONObject("response_params");
			//String appid = params.getString("appid");
			//String channelid = params.getString("channel_id");
			String userid = params.getString("user_id");
			
			//���豸ʶ����洢��PushSharedPreferences��
			return userid;
			
		} catch (JSONException e) {
			return "";
		}
	}

}
