package core.contactlistener;

public class SensorSwitch implements TouchListener {

	public int[] detectionID;
	public int touches;
	public SensorData data;
	
	public SensorSwitch(int[] detectionID) {
		this.detectionID = detectionID;
		data = new SensorData(TouchListener.SENSOR_ID, this);
		touches = 0;
	}
	
	public SensorData getSensorData() {
		return data;
	}
	
	@Override
	public void startTouch(SensorData a, SensorData b) {
		for (int i = 0; i < detectionID.length; i++) {
			if (b.value == detectionID[i])
				touches++;
		}
	}

	@Override
	public void endTouch(SensorData a, SensorData b) {
		for (int i = 0; i < detectionID.length; i++) {
			if (b.value == detectionID[i])
				touches--;
		}
	}

}
