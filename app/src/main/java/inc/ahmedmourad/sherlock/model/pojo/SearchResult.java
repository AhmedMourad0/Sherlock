package inc.ahmedmourad.sherlock.model.pojo;

import android.support.annotation.NonNull;

import java.sql.Date;
import java.text.DateFormat;

import inc.ahmedmourad.sherlock.model.room.entities.ResultEntity;
import inc.ahmedmourad.sherlock.utils.Criteria;

public class SearchResult {

	private Child child;
	private String date = "";

	private int firstNameRatio = 0;
	private int lastNameRatio = 0;
	private float distance = Criteria.BASE_DISTANCE + 1f;

	@NonNull
	public static SearchResult fromResultEntity(@NonNull final ResultEntity result) {
		return create(result.date, Child.fromResultEntity(result));
	}

	@NonNull
	private static SearchResult create(@NonNull final String date, @NonNull final Child child) {
		final SearchResult result = new SearchResult();
		result.setDate(date);
		result.setChild(child);
		return result;
	}

	@NonNull
	public static SearchResult create(final long dateMillis, @NonNull final Child child) {
		final SearchResult result = new SearchResult();
		result.setDate(dateMillis);
		result.setChild(child);
		return result;
	}

	private SearchResult() {

	}

	@NonNull
	public Child getChild() {
		return child;
	}

	public void setChild(@NonNull Child child) {
		this.child = child;
	}

	@NonNull
	public String getDate() {
		return date;
	}

	public void setDate(long dateMillis) {
		this.date = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(new Date(dateMillis));
	}

	private void setDate(@NonNull String date) {
		this.date = date;
	}

	private int getFirstNameRatio() {
		return firstNameRatio;
	}

	public void setFirstNameRatio(int firstNameRatio) {
		this.firstNameRatio = firstNameRatio;
	}

	private int getLastNameRatio() {
		return lastNameRatio;
	}

	public void setLastNameRatio(int lastNameRatio) {
		this.lastNameRatio = lastNameRatio;
	}

	private float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public int rank() {
		return getFirstNameRatio() + getLastNameRatio() + getDistanceRanking();
	}

	private int getDistanceRanking() {
		return (int) ((Criteria.BASE_DISTANCE - getDistance()) / Criteria.BASE_DISTANCE * 100);
	}

	@Override
	public String toString() {
		return "SearchResult{" +
				"child=" + child +
				", date='" + date + '\'' +
				", firstNameRatio=" + firstNameRatio +
				", lastNameRatio=" + lastNameRatio +
				", distance=" + distance +
				'}';
	}
}
