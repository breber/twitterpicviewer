package org.reber.twitpicviewer;

/**
 * @author breber
 *
 */
public class YFrog implements ImageHost {

	private String url;
	
	/**
	 * 
	 */
	public YFrog(String url) {
		this.url = url.trim();
	}
	
	/* (non-Javadoc)
	 * @see org.reber.twitpicviewer.ImageHost#getURL()
	 */
	@Override
	public String getURL() {
		return url.concat(":iphone");
	}

}
