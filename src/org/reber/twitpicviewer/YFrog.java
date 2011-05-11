package org.reber.twitpicviewer;

/**
 * YFrog image support
 * 
 * @author breber
 */
public class YFrog implements ImageHost {

	private String url;
	
	/**
	 * Creates a new YFrog image object from the given shortened YFrog image URL
	 * 
	 * @param url - the shortened YFrog image URL
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
