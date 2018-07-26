package inc.ahmedmourad.sherlock.utils;

import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import inc.ahmedmourad.sherlock.model.pojo.Coordinates;
import inc.ahmedmourad.sherlock.model.pojo.SearchResult;
import me.xdrop.fuzzywuzzy.FuzzySearch;

public final class Criteria {

	public static final float BASE_DISTANCE = 5000f;

	public static boolean checkFirstName(@NonNull final String firstName, @NonNull final SearchResult result) {

		if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(result.getChild().getFirstName()))
			return true;

		int similarity = FuzzySearch.ratio(firstName, result.getChild().getFirstName());

		if (similarity > 40)
			result.setFirstNameRatio(similarity);
		else
			return false;

		return true;
	}

	public static boolean checkLastName(@NonNull final String lastName, @NonNull final SearchResult result) {

		if (TextUtils.isEmpty(lastName) || TextUtils.isEmpty(result.getChild().getLastName()))
			return true;

		int similarity = FuzzySearch.ratio(lastName, result.getChild().getLastName());

		if (similarity > 40)
			result.setLastNameRatio(similarity);
		else
			return false;

		return true;
	}

	public static boolean checkGender(final int gender, @NonNull final SearchResult result) {
		return gender == result.getChild().getGender();
	}

	public static boolean checkSkin(final int skin, @NonNull final SearchResult result) {
		return skin == result.getChild().getSkin();
	}

	public static boolean checkHair(final int hair, @NonNull final SearchResult result) {
		return hair == result.getChild().getHair();
	}

	public static boolean checkAge(final int age, @NonNull final SearchResult result) {
		// the two-years padding is applied to account for user error when estimating age
		return age > result.getChild().getStartAge() - 2 && age < result.getChild().getEndAge() + 2;
	}

	public static boolean checkHeight(final int height, @NonNull final SearchResult result) {
		// the 15 cm padding is applied to account for user error when estimating height
		return height > result.getChild().getStartHeight() - 15 && height < result.getChild().getEndHeight() + 15;
	}

	public static boolean checkLocation(@NonNull final String location, @NonNull final SearchResult result) {

		final Coordinates criteriaCoordinates = extractCoordinates(location);

		if (criteriaCoordinates == null)
			return true;

		final Coordinates resultCoordinates = extractCoordinates(result.getChild().getLocation());

		if (resultCoordinates == null)
			return true;

		final float[] distance = new float[1];

		Location.distanceBetween(criteriaCoordinates.getLatitude(),
				criteriaCoordinates.getLongitude(),
				resultCoordinates.getLatitude(),
				resultCoordinates.getLongitude(),
				distance
		);

		if (distance[0] < BASE_DISTANCE)
			result.setDistance(distance[0]);
		else
			return false;

		return true;
	}

	@Nullable
	private static Coordinates extractCoordinates(@NonNull final String location) {

		if (TextUtils.isEmpty(location))
			return null;

		final String[] details = location.split("\\|\\|");

		if (details.length < 4)
			return null;

		if (!details[3].contains(","))
			return null;

		final String[] coords = details[3].split(",");

		return Coordinates.create(coords[0], coords[1]);
	}
}
