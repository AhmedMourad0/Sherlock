package inc.ahmedmourad.sherlock.model.pojo;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;

import org.parceler.Parcel;

import java.util.HashMap;
import java.util.Map;

import inc.ahmedmourad.sherlock.interfaces.Gender;
import inc.ahmedmourad.sherlock.interfaces.Hair;
import inc.ahmedmourad.sherlock.interfaces.Skin;
import inc.ahmedmourad.sherlock.model.contract.FirebaseContract;
import inc.ahmedmourad.sherlock.model.room.entities.ResultEntity;
import inc.ahmedmourad.sherlock.viewmodel.AddFoundViewModel;

@Parcel(Parcel.Serialization.BEAN)
public class Child {

	private String firstName = "";
	private String lastName = "";
	private String pictureUrl = "";
	private String location = "";
	private String notes = "";

	private int gender = Gender.GENDER_MALE;
	private int skin = Skin.SKIN_WHITE;
	private int hair = Hair.HAIR_BLONDE;

	private int startAge = 0;
	private int endAge = 30;
	private int startHeight = 40;
	private int endHeight = 200;

	@NonNull
	public static Child fromResultEntity(@NonNull final ResultEntity result) {

		// This's where babies come from
		final Child child = new Child();

		child.setFirstName(result.firstName);
		child.setLastName(result.lastName);
		child.setLocation(result.location);
		child.setStartAge(result.startAge);
		child.setEndAge(result.endAge);
		child.setStartHeight(result.startHeight);
		child.setEndHeight(result.endHeight);
		child.setGender(result.gender);
		child.setSkin(result.skin);
		child.setHair(result.hair);
		child.setPictureUrl(result.pictureUrl);
		child.setNotes(result.notes);

		return child;
	}

	@NonNull
	public static Child fromViewModel(@NonNull final AddFoundViewModel viewModel) {

		final Child child = new Child();

		child.setFirstName(viewModel.getFirstName().trim());
		child.setLastName(viewModel.getLastName().trim());
		child.setLocation(viewModel.getFirebaseLocation().trim());
		child.setStartAge(viewModel.getStartAge());
		child.setEndAge(viewModel.getEndAge());
		child.setStartHeight(viewModel.getStartHeight());
		child.setEndHeight(viewModel.getEndHeight());
		child.setGender(viewModel.getGender());
		child.setSkin(viewModel.getSkinColor());
		child.setHair(viewModel.getHairColor());
		child.setNotes(viewModel.getNotes().trim());

		return child;
	}

	@NonNull
	public static Child fromDataSnapshot(@NonNull final DataSnapshot snapshot) {

		final Child child = new Child();

		child.setFirstName(String.valueOf(snapshot.child(FirebaseContract.Database.CHILDREN_FIRST_NAME).getValue()));
		child.setLastName(String.valueOf(snapshot.child(FirebaseContract.Database.CHILDREN_LAST_NAME).getValue()));
		child.setLocation(String.valueOf(snapshot.child(FirebaseContract.Database.CHILDREN_LOCATION).getValue()));
		child.setStartAge((int) ((long) snapshot.child(FirebaseContract.Database.CHILDREN_START_AGE).getValue()));
		child.setEndAge((int) ((long) snapshot.child(FirebaseContract.Database.CHILDREN_END_AGE).getValue()));
		child.setStartHeight((int) ((long) snapshot.child(FirebaseContract.Database.CHILDREN_START_HEIGHT).getValue()));
		child.setEndHeight((int) ((long) snapshot.child(FirebaseContract.Database.CHILDREN_END_HEIGHT).getValue()));
		child.setGender((int) ((long) snapshot.child(FirebaseContract.Database.CHILDREN_GENDER).getValue()));
		child.setSkin((int) ((long) snapshot.child(FirebaseContract.Database.CHILDREN_SKIN).getValue()));
		child.setHair((int) ((long) snapshot.child(FirebaseContract.Database.CHILDREN_HAIR).getValue()));
		child.setPictureUrl(String.valueOf(snapshot.child(FirebaseContract.Database.CHILDREN_PICTURE_URL).getValue()));
		child.setNotes(String.valueOf(snapshot.child(FirebaseContract.Database.CHILDREN_NOTES).getValue()));

		return child;
	}

	@NonNull
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(@NonNull String firstName) {
		this.firstName = firstName;
	}

	@NonNull
	public String getLastName() {
		return lastName;
	}

	public void setLastName(@NonNull String lastName) {
		this.lastName = lastName;
	}

	@NonNull
	public String getLocation() {
		return location;
	}

	public void setLocation(@NonNull String location) {
		this.location = location;
	}

	public int getStartAge() {
		return startAge;
	}

	@SuppressWarnings("WeakerAccess")
	public void setStartAge(int startAge) {
		this.startAge = startAge;
	}

	public int getEndAge() {
		return endAge;
	}

	@SuppressWarnings("WeakerAccess")
	public void setEndAge(int endAge) {
		this.endAge = endAge;
	}

	public int getStartHeight() {
		return startHeight;
	}

	@SuppressWarnings("WeakerAccess")
	public void setStartHeight(int startHeight) {
		this.startHeight = startHeight;
	}

	public int getEndHeight() {
		return endHeight;
	}

	@SuppressWarnings("WeakerAccess")
	public void setEndHeight(int endHeight) {
		this.endHeight = endHeight;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getSkin() {
		return skin;
	}

	public void setSkin(int skin) {
		this.skin = skin;
	}

	public int getHair() {
		return hair;
	}

	public void setHair(int hair) {
		this.hair = hair;
	}

	@NonNull
	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(@NonNull String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	@NonNull
	public String getNotes() {
		return notes;
	}

	public void setNotes(@NonNull String notes) {
		this.notes = notes;
	}

	@NonNull
	public Map<String, Object> toMap() {
		final Map<String, Object> map = new HashMap<>(12);
		map.put(FirebaseContract.Database.CHILDREN_FIRST_NAME, getFirstName());
		map.put(FirebaseContract.Database.CHILDREN_LAST_NAME, getLastName());
		map.put(FirebaseContract.Database.CHILDREN_LOCATION, getLocation());
		map.put(FirebaseContract.Database.CHILDREN_START_AGE, getStartAge());
		map.put(FirebaseContract.Database.CHILDREN_END_AGE, getEndAge());
		map.put(FirebaseContract.Database.CHILDREN_START_HEIGHT, getStartHeight());
		map.put(FirebaseContract.Database.CHILDREN_END_HEIGHT, getEndHeight());
		map.put(FirebaseContract.Database.CHILDREN_GENDER, getGender());
		map.put(FirebaseContract.Database.CHILDREN_SKIN, getSkin());
		map.put(FirebaseContract.Database.CHILDREN_HAIR, getHair());
		map.put(FirebaseContract.Database.CHILDREN_PICTURE_URL, getPictureUrl());
		map.put(FirebaseContract.Database.CHILDREN_NOTES, getNotes());
		return map;
	}
}
