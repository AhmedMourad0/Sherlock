package inc.ahmedmourad.sherlock.model.pojo;

import android.support.annotation.NonNull;

import org.parceler.Parcel;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import inc.ahmedmourad.sherlock.interfaces.Gender;
import inc.ahmedmourad.sherlock.interfaces.Hair;
import inc.ahmedmourad.sherlock.interfaces.Skin;
import inc.ahmedmourad.sherlock.utils.Criteria;
import inc.ahmedmourad.sherlock.viewmodel.SearchFoundViewModel;

@Parcel(Parcel.Serialization.BEAN)
public class SearchCriteria {

	private String firstName = "";
	private String lastName = "";
	private String location = "";

	private int gender = Gender.GENDER_MALE;
	private int skin = Skin.SKIN_WHITE;
	private int hair = Hair.HAIR_BLONDE;

	private int age = 0;
	private int height = 40;

	@NonNull
	public static SearchCriteria fromViewModel(@NonNull final SearchFoundViewModel viewModel) {

		final SearchCriteria criteria = new SearchCriteria();

		criteria.setFirstName(viewModel.getFirstName().trim());
		criteria.setLastName(viewModel.getLastName().trim());
		criteria.setLocation(viewModel.getFirebaseLocation().trim());
		criteria.setGender(viewModel.getGender());
		criteria.setSkin(viewModel.getSkinColor());
		criteria.setHair(viewModel.getHairColor());
		criteria.setAge(viewModel.getAge());
		criteria.setHeight(viewModel.getHeight());

		return criteria;
	}

	public void filter(@NonNull final List<SearchResult> results) {

		SearchResult result;

		for (Iterator<SearchResult> iterator = results.iterator(); iterator.hasNext(); ) {

			result = iterator.next();

			if (!Criteria.checkFirstName(getFirstName(), result)) {
				iterator.remove();
				continue;
			}

			if (!Criteria.checkLastName(getLastName(), result)) {
				iterator.remove();
				continue;
			}

			if (!Criteria.checkGender(getGender(), result)) {
				iterator.remove();
				continue;
			}

//			if (!Criteria.checkSkin(getSkin(), results, result)) {
//				iterator.remove();
//				continue;
//			}

			if (!Criteria.checkHair(getHair(), result)) {
				iterator.remove();
				continue;
			}

			if (!Criteria.checkLocation(getLocation(), result)) {
				iterator.remove();
				continue;
			}

			if (!Criteria.checkAge(getAge(), result)) {
				iterator.remove();
				continue;
			}

			if (!Criteria.checkHeight(getHeight(), result))
				iterator.remove();
		}

		Collections.sort(results, Collections.reverseOrder((o1, o2) -> o1.rank() - o2.rank()));
	}

	@SuppressWarnings("WeakerAccess")
	@NonNull
	public String getFirstName() {
		return firstName;
	}

	@SuppressWarnings("WeakerAccess")
	public void setFirstName(@NonNull String firstName) {
		this.firstName = firstName;
	}

	@SuppressWarnings("WeakerAccess")
	@NonNull
	public String getLastName() {
		return lastName;
	}

	@SuppressWarnings("WeakerAccess")
	public void setLastName(@NonNull String lastName) {
		this.lastName = lastName;
	}

	@SuppressWarnings("WeakerAccess")
	public int getGender() {
		return gender;
	}

	@SuppressWarnings("WeakerAccess")
	public void setGender(int gender) {
		this.gender = gender;
	}

	@SuppressWarnings("WeakerAccess")
	public int getSkin() {
		return skin;
	}

	@SuppressWarnings("WeakerAccess")
	public void setSkin(int skin) {
		this.skin = skin;
	}

	@SuppressWarnings("WeakerAccess")
	public int getHair() {
		return hair;
	}

	@SuppressWarnings("WeakerAccess")
	public void setHair(int hair) {
		this.hair = hair;
	}

	@NonNull
	public String getLocation() {
		return location;
	}

	@SuppressWarnings("WeakerAccess")
	public void setLocation(@NonNull String location) {
		this.location = location;
	}

	@SuppressWarnings("WeakerAccess")
	public int getAge() {
		return age;
	}

	@SuppressWarnings("WeakerAccess")
	public void setAge(int age) {
		this.age = age;
	}

	@SuppressWarnings("WeakerAccess")
	public int getHeight() {
		return height;
	}

	@SuppressWarnings("WeakerAccess")
	public void setHeight(int height) {
		this.height = height;
	}
}
