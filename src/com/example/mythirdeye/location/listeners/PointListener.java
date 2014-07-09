package com.example.mythirdeye.location.listeners;

public interface PointListener {
	
	/** When the mobile device reaches a specific point	 */
	public void pointReached();
	
	/** When the mobile device exits a specific point */
	public void pointExited();
}
