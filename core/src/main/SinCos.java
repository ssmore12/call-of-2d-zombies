package main;

public class SinCos {
	private static float[] cosine = new float[360];
	private static float[] sine = new float[360];
	
	/**
	 * adds all possible sine or cosine values to their respective arrays
	 */
	public static void init() {
		for	(int i = 0; i < 360; i++) {
			sine[i] = (float) Math.sin(Math.toRadians(i));
			cosine[i] = (float) Math.cos(Math.toRadians(i));			
		}
	}
	
	/**
	 * @param angle - angle in radians
	 * 
	 * Goes through cosine array and returns cosine of angle
	 * 
	 * @return angle cosine of angle
	 */
	public static float getCosine(int angle) {
		if (angle >= 360)
			return getCosine(angle - 360);
		if (angle < 0)
			return getCosine(angle += 360);
		
		return cosine[angle];
	}
	
	/**
	 * @param angle - angle in radians
	 * 
	 * Goes through sine array and returns sine of angle
	 * 
	 * @return angle sine of angle
	 */
	public static float getSine(int angle) {
		if (angle >= 360)
			return getSine(angle - 360);
		if (angle < 0)
			return getSine(angle += 360);
		
		return sine[angle];
	}
	
}