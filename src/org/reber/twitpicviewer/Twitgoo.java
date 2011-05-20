package org.reber.twitpicviewer;

/**
 * Twitgoo image support
 * 
 * http://twitgoo.com/docs/TwitgooHelp.htm
 * 
 * @author breber
 */
public class Twitgoo implements ImageHost {

	private String url;
	
	/**
	 * Creates a new Twitgoo image object from the given shortened Twitgoo image URL
	 * 
	 * @param url - the shortened Twitgoo image URL
	 */
	public Twitgoo(String url) {
		this.url = url.trim() + "/img";
	}
	
	/* (non-Javadoc)
	 * @see org.reber.twitpicviewer.ImageHost#getURL()
	 */
	@Override
	public String getURL() {
		return url;
	}

}
