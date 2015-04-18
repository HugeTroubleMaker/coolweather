package com.coolweather.app;


import java.text.SimpleDateFormat;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;


public class MainActivity extends Activity {

	public static final int SHOW_RESULT=0;
	private TextView test;
	private Handler myHandler=new Handler(){

		@Override
		public void handleMessage(Message msg) 
		{
			super.handleMessage(msg);
			switch (msg.what) 
			{
			case SHOW_RESULT:
				String contents=(String) msg.obj;
				test.setText(contents);
				break;

			default:
				break;
			}
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		test=(TextView) findViewById(R.id.test);
		sendRequestWithHttpUrlConnection();
	}
	
	void sendRequestWithHttpUrlConnection()
	{
		new Thread(new Runnable() 
		{
			
			@Override
			public void run() 
			{
				try 
				{
					Log.i("我在这里~~", "新线程启动了");
					SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMddHHmm");
					String Date=formatter.format(new java.util.Date());
					String publicKey=new String("http://open.weather.com.cn/data/?"
							+ "areaid=101010100&type=forecast_v&date="
							+ Date
							+ "&appid=a76c2e7f7c109fe0");
					String publicKey2=new String("http://open.weather.com.cn/data/?"
							+ "areaid=101010100&type=forecast_v&date="
							+ Date
							+ "&appid=a76c2e");
					String privateKey=new String("0c33b2_SmartWeatherAPI_8bc23aa");
					String privateKeyCode=javademo.standardURLEncoder(publicKey, privateKey);
					String url=publicKey2+"&key="+privateKeyCode;
					Log.i("我在这里~~", url);
					HttpClient httpClient=new DefaultHttpClient();
					HttpGet httpGet=new HttpGet(url);
					HttpResponse httpResponse=httpClient.execute(httpGet);
					Log.i("我在这里~~", httpResponse.getStatusLine().getStatusCode()+"");
					if(httpResponse.getStatusLine().getStatusCode()==200)
					{
						HttpEntity entity=httpResponse.getEntity();
						String response=EntityUtils.toString(entity,"utf-8");
						Log.i("我在这里~~", response);
						Message msg=new Message();
						msg.obj=response;
						msg.what=SHOW_RESULT;
						myHandler.sendMessage(msg);
					}
				
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		}).start();
	}
	private void parJSONWithJSONObject(String jsonData){
		try {
			JSONArray jsonArray=new JSONArray(jsonData);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
}
