package core.contactlistener;

public interface TouchListener {
	public static final int SENSOR_ID = -1;
	
	/**
	 * called when a sensor touches something
	 * @param a the sensor object
	 * @param b the other object
	 */
	public void startTouch(SensorData a, SensorData b);
	public void endTouch(SensorData a, SensorData b);
}
