package org.reber.twitpicviewer;

/**
 * An image host for Twitter images
 *  
 * @author breber
 */
public interface ImageHost {

	/**
	 * Based on the information in the image class, give back the
	 * URL of the image to show
	 * 
	 * @return the URL to the image to display
	 */
	abstract String getURL();
}
