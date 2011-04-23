package org.reber.twitpicviewer;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 
 * http://support.lockerz.com/entries/375090-photo
 * 
 * 
 * @author breber
 */
public class Plixi implements ImageHost {

	private String url;
	
	/**
	 * @throws IOException 
	 * 
	 */
	public Plixi(String url) throws IOException {
		String temp = "http://api.plixi.com/api/tpapi.svc/photos/" + url.substring(url.indexOf("/p/") + 3);
		
		HttpURLConnection connection = (HttpURLConnection) new URL(temp).openConnection();
		connection.setRequestProperty("User-agent","Mozilla/4.0");
		HttpURLConnection.setFollowRedirects(true);
		connection.connect();
		InputStream input = connection.getInputStream();
		
		temp = Util.convertStreamToString(input);

		this.url = temp.substring(temp.indexOf("<MobileImageUrl>") + 16, temp.indexOf("</MobileImageUrl>"));
	}
	
	/* (non-Javadoc)
	 * @see org.reber.twitpicviewer.ImageHost#getURL()
	 */
	@Override
	public String getURL() {
		return url;
	}
}
