package core.contactlistener;

public class SensorData {
	
	public int value;
	public Object actor;
	
	public SensorData(int value) {
		this(value, null);
	}
	
	public SensorData(int value, Object actor) {
		this.value = value;
		this.actor = actor;
	}
}
