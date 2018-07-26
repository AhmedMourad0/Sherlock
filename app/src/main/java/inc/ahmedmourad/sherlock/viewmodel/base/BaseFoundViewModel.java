package inc.ahmedmourad.sherlock.viewmodel.base;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.location.places.Place;

import inc.ahmedmourad.sherlock.R;
import inc.ahmedmourad.sherlock.interfaces.Gender;
import inc.ahmedmourad.sherlock.interfaces.Hair;
import inc.ahmedmourad.sherlock.interfaces.Skin;

public class BaseFoundViewModel extends ViewModel {

	private String firstName = "";
	private String lastName = "";

	private int skinColorId = Skin.SKIN_WHITE;
	private int hairColorId = Hair.HAIR_BLONDE;

	private int genderResId = R.id.found_male_radio_button;

	private Place place;

	public int getGender() {
		if (getGenderResId() == R.id.found_male_radio_button)
			return Gender.GENDER_MALE;
		else
			return Gender.GENDER_FEMALE;
	}

	@NonNull
	public String getFirebaseLocation() {
		return getPlace() == null ? "" : getPlace().getId() +  "||" + getPlace().getName() + "||" +
				getPlace().getAddress() + "||" + getPlace().getLatLng().latitude + "," +
				getPlace().getLatLng().longitude;
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

	public int getSkinColor() {
		return skinColorId;
	}

	public void setSkinColor(int skinColorId) {
		this.skinColorId = skinColorId;
	}

	public int getHairColor() {
		return hairColorId;
	}

	public void setHairColor(int hairColorId) {
		this.hairColorId = hairColorId;
	}

	public int getGenderResId() {
		return genderResId;
	}

	public void setGenderResId(@IdRes int genderResId) {
		this.genderResId = genderResId;
	}

	@Nullable
	public Place getPlace() {
		return place;
	}

	public void setPlace(@NonNull Place place) {
		this.place = place;
	}
}
