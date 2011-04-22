package nl.redcode.iphone.sqllite;

/**
 *
 * This class holds the geo data.
 * 
 * @author Roy van Rijn
 * 
 */
public class DataPoint {
	private final String timestamp;
	private final double latitude;
	private final double longitude;
	
	public DataPoint(final String timestamp, final double latitude, final double longitude) {
		this.timestamp= timestamp;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}
}