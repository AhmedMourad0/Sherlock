package inc.ahmedmourad.sherlock.viewmodel;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import inc.ahmedmourad.sherlock.viewmodel.base.BaseFoundViewModel;

public class AddFoundViewModel extends BaseFoundViewModel {

	private int startAge = 0;
	private int endAge = 30;
	private int startHeight = 40;
	private int endHeight = 200;

	private String notes = "";

	private Bitmap picture;

	public int getStartAge() {
		return startAge;
	}

	public void setStartAge(int startAge) {
		this.startAge = startAge;
	}

	public int getEndAge() {
		return endAge;
	}

	public void setEndAge(int endAge) {
		this.endAge = endAge;
	}

	public int getStartHeight() {
		return startHeight;
	}

	public void setStartHeight(int startHeight) {
		this.startHeight = startHeight;
	}

	public int getEndHeight() {
		return endHeight;
	}

	public void setEndHeight(int endHeight) {
		this.endHeight = endHeight;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Nullable
	public Bitmap getPicture() {
		return picture;
	}

	public void setPicture(@Nullable Bitmap picture) {
		this.picture = picture;
	}
}
