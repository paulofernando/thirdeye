package br.net.paulofernando.thirdeye;

public class RouteUtilities {

	public static float smallestDifferenceDegrees(float current, float point) {
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
