package com.example.mythirdeye;

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

	
}
