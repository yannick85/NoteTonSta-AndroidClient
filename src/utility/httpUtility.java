package utility;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;


public class httpUtility {
	public static String baseUri = "http://10.0.2.2:8080/NoteTonStaBetouWebSer";
	
	static httpUtility instance = null;
	
	public static httpUtility getInstance(){
		if(instance == null){
			instance = new httpUtility();
		}
		return instance;
	}
	
	public String get(String uri){
		//Communication webservice
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse response = null;
		try {
			HttpGet request = new HttpGet(baseUri+uri);
			request.addHeader(new BasicHeader("Accept", "application/json"));
			response = httpClient.execute(request);
		} catch (ClientProtocolException e1) {
			return null;
		} catch (IOException e1) {
			return null;
		}
		
		//Traitement résultat
		HttpEntity respEntity = response.getEntity();
		String result = null;
		try {
			result = EntityUtils.toString(respEntity);
		} catch (ParseException e1) {
			return null;
		} catch (IOException e1) {
			return null;
		}
		
		return result;
	}
	
	public boolean post(String uri,String content){
		//Communication webservice
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse response = null;
		try {
			HttpPost request = new HttpPost(baseUri+uri);
			StringEntity entity = new StringEntity(content);
			request.setEntity(entity);
			request.addHeader(new BasicHeader("Accept", "application/json"));
			response = httpClient.execute(request);
		} catch (ClientProtocolException e1) {
			return false;
		} catch (IOException e1) {
			return false;
		}
		if(response.getStatusLine().getStatusCode() == 200){
			return true;
		}else{
			return false;
		}
	}
	
}
