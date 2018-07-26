package inc.ahmedmourad.sherlock.model.pojo;

import android.support.annotation.NonNull;

public class Coordinates {

	private double latitude = 0.0;
	private double longitude = 0.0;

	@NonNull
	public static Coordinates create(final String latitude, final String longitude) {
		final Coordinates coordinates = new Coordinates();
		coordinates.setLatitude(Double.valueOf(latitude));
		coordinates.setLongitude(Double.valueOf(longitude));
		return coordinates;
	}

	public double getLatitude() {
		return latitude;
	}

	private void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	private void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}
