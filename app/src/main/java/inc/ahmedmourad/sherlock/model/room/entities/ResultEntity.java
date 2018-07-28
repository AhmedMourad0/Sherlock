package inc.ahmedmourad.sherlock.model.room.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import inc.ahmedmourad.sherlock.model.pojo.SearchResult;

@Entity(tableName = ResultEntity.TABLE_NAME)
public class ResultEntity {

	@Ignore
	public static final String TABLE_NAME = "results";

	@Ignore
	public static final String COLUMN_ID = "id";

	@Ignore
	public static final String COLUMN_DATE = "date";

	@Ignore
	public static final String COLUMN_FIRST_NAME = "first_name";

	@Ignore
	public static final String COLUMN_LAST_NAME = "last_name";

	@Ignore
	public static final String COLUMN_PICTURE_URL = "picture_url";

	@Ignore
	public static final String COLUMN_LOCATION = "location";

	@Ignore
	public static final String COLUMN_NOTES = "notes";

	@Ignore
	public static final String COLUMN_GENDER = "gender";

	@Ignore
	public static final String COLUMN_SKIN = "skin";

	@Ignore
	public static final String COLUMN_HAIR = "hair";

	@Ignore
	public static final String COLUMN_START_AGE = "start_age";

	@Ignore
	public static final String COLUMN_END_AGE = "end_age";

	@Ignore
	public static final String COLUMN_START_HEIGHT = "start_height";

	@Ignore
	public static final String COLUMN_END_HEIGHT = "end_height";

	@SuppressWarnings("WeakerAccess")
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = COLUMN_ID)
	public int id;

	@ColumnInfo(name = COLUMN_DATE)
	public String date;

	@ColumnInfo(name = COLUMN_FIRST_NAME)
	public String firstName;

	@ColumnInfo(name = COLUMN_LAST_NAME)
	public String lastName;

	@ColumnInfo(name = COLUMN_PICTURE_URL)
	public String pictureUrl;

	@ColumnInfo(name = COLUMN_LOCATION)
	public String location;

	@ColumnInfo(name = COLUMN_NOTES)
	public String notes;

	@ColumnInfo(name = COLUMN_GENDER)
	public int gender;

	@ColumnInfo(name = COLUMN_SKIN)
	public int skin;

	@ColumnInfo(name = COLUMN_HAIR)
	public int hair;

	@ColumnInfo(name = COLUMN_START_AGE)
	public int startAge;

	@ColumnInfo(name = COLUMN_END_AGE)
	public int endAge;

	@ColumnInfo(name = COLUMN_START_HEIGHT)
	public int startHeight;

	@ColumnInfo(name = COLUMN_END_HEIGHT)
	public int endHeight;

	@Ignore
	@NonNull
	public static ResultEntity fromSearchResult(@NonNull final SearchResult searchResult) {

		final ResultEntity resultEntity = new ResultEntity();

		resultEntity.date = searchResult.getDate();
		resultEntity.firstName = searchResult.getChild().getFirstName();
		resultEntity.lastName = searchResult.getChild().getLastName();
		resultEntity.pictureUrl = searchResult.getChild().getPictureUrl();
		resultEntity.location = searchResult.getChild().getLocation();
		resultEntity.notes = searchResult.getChild().getNotes();
		resultEntity.gender = searchResult.getChild().getGender();
		resultEntity.skin = searchResult.getChild().getSkin();
		resultEntity.hair = searchResult.getChild().getHair();
		resultEntity.startAge = searchResult.getChild().getStartAge();
		resultEntity.endAge = searchResult.getChild().getEndAge();
		resultEntity.startHeight = searchResult.getChild().getStartHeight();
		resultEntity.endHeight = searchResult.getChild().getEndHeight();

		return resultEntity;
	}

	@Ignore
	private ResultEntity() {

	}

	public ResultEntity(int id, String date, String firstName, String lastName, String pictureUrl,
	                    String location, String notes, int gender, int skin, int hair, int startAge,
	                    int endAge, int startHeight, int endHeight) {
		this.id = id;
		this.date = date;
		this.firstName = firstName;
		this.lastName = lastName;
		this.pictureUrl = pictureUrl;
		this.location = location;
		this.notes = notes;
		this.gender = gender;
		this.skin = skin;
		this.hair = hair;
		this.startAge = startAge;
		this.endAge = endAge;
		this.startHeight = startHeight;
		this.endHeight = endHeight;
	}


}
