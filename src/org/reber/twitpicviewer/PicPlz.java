package org.reber.twitpicviewer;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * PicPlz image support
 * 
 * https://sites.google.com/site/picplzapi/api-methods
 * 
 * @author breber
 */
public class PicPlz implements ImageHost {

	private String url;
	
	/**
	 * @throws IOException 
	 * @throws JSONException 
	 * 
	 */
	public PicPlz(String url) throws IOException, JSONException {
		String temp = "http://api.picplz.com/api/v2/pic.json?shorturl_id=" + url.substring(url.indexOf("com/") + 4);
		
		HttpURLConnection connection = (HttpURLConnection) new URL(temp).openConnection();
		connection.setRequestProperty("User-agent","Mozilla/4.0");
		HttpURLConnection.setFollowRedirects(true);
		connection.connect();
		InputStream input = connection.getInputStream();

		temp = Util.convertStreamToString(input);
		
		JSONObject obj = new JSONObject(temp);
		obj = (JSONObject) obj.getJSONObject("value").getJSONArray("pics").get(0);
		
		this.url = obj.getJSONObject("pic_files").getJSONObject("640r").getString("img_url");
	}
	
	/* (non-Javadoc)
	 * @see org.reber.twitpicviewer.ImageHost#getURL()
	 */
	@Override
	public String getURL() {
		return url;
	}
}
