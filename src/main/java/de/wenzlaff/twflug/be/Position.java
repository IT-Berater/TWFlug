package de.wenzlaff.twflug.be;

/**
 * Eine Geo Position.
 * 
 * @author Thomas Wenzlaff
 * @version 0.1
 * @since 17.01.2015
 */
public class Position {

	private double latitude;
	private double longitude;

	public Position(double lat, double lon) {
		latitude = lat;
		longitude = lon;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Position [latitude=");
		builder.append(latitude);
		builder.append(", longitude=");
		builder.append(longitude);
		builder.append("]");
		return builder.toString();
	}

}
