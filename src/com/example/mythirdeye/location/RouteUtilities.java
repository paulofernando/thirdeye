package com.example.mythirdeye.location;

public class RouteUtilities {

	public static double smallestDifferenceDegrees(double current, double point) {
		if(current >= point) {
			if((current - point) <= 180) {
				return current - point; 
			} else {
				return 360 - (current - point);
			}
		} else {
			if((point - current) <= 180) {
				return point - current;
			} else {
				return 360 - (point - current);
			}
		}
	}
	
	public static double radToDeg(double radians) {  
        return radians * (180 / Math.PI);  
    }  

    public static double degToRad(double degrees) {  
        return degrees * (Math.PI / 180);  
    }  

    public static double bearing(double lat1, double long1, double lat2, double long2) {  
        //Convert input values to radians  
        lat1 = degToRad(lat1);  
        long1 = degToRad(long1);  
        lat2 = degToRad(lat2);  
        long2 = degToRad(long2);  

        double deltaLong = long2 - long1;  

        double y = Math.sin(deltaLong) * Math.cos(lat2);  
        double x = Math.cos(lat1) * Math.sin(lat2) -  
                Math.sin(lat1) * Math.cos(lat2) * Math.cos(deltaLong);  
        double bearing = Math.atan2(y, x);  
        return convertToBearing(radToDeg(bearing));  
    }  

    public static double convertToBearing(double deg) {  
        return (deg + 360) % 360;  
    }  

	
}
