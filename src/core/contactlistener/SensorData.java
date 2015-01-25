package core.contactlistener;

public class SensorData {
	
	public int value;
	public Object actor;
	public int extra;
	
	public SensorData(int value) {
		this(value, null);
	}
	
	public SensorData(int value, Object actor) {
		this(value, actor, 0);
	}
	
	public SensorData(int value, Object actor, int extra) {
		this.value = value;
		this.actor = actor;
		this.extra = extra;
	}
}
