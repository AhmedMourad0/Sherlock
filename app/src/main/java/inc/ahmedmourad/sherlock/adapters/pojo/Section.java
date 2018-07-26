package inc.ahmedmourad.sherlock.adapters.pojo;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bluelinelabs.conductor.Controller;

public class Section {

	private String name;
	private int imageDrawable;
	private Controller controller;

	@NonNull
	public static Section create(@NonNull final String name, @DrawableRes final int imageDrawable, @Nullable final Controller controller) {
		final Section section = new Section();
		section.setName(name);
		section.setImageDrawable(imageDrawable);
		section.setController(controller);
		return section;
	}

	@NonNull
	public String getName() {
		return name;
	}

	public void setName(@NonNull final String name) {
		this.name = name;
	}

	@DrawableRes
	public int getImageDrawable() {
		return imageDrawable;
	}

	public void setImageDrawable(@DrawableRes final int imageDrawable) {
		this.imageDrawable = imageDrawable;
	}

	@Nullable
	public Controller getController() {
		return controller;
	}

	public void setController(@Nullable final Controller controller) {
		this.controller = controller;
	}
}
