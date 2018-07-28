package inc.ahmedmourad.sherlock.view.controllers;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import inc.ahmedmourad.sherlock.R;
import inc.ahmedmourad.sherlock.model.contract.FirebaseContract;
import inc.ahmedmourad.sherlock.model.pojo.Child;
import inc.ahmedmourad.sherlock.view.controllers.base.BaseController;

public class DisplayFoundController extends BaseController {

	private static final String ARG_IMAGE_BYTES = "df_ib";
	private static final String ARG_FOUND = "df_f";

	@SuppressWarnings("WeakerAccess")
	@BindView(R.id.display_found_toolbar)
	Toolbar toolbar;

	@SuppressWarnings("WeakerAccess")
	@BindView(R.id.display_found_picture)
	ImageView pictureImageView;

	@SuppressWarnings("WeakerAccess")
	@BindView(R.id.display_name)
	TextView nameTextView;

	@SuppressWarnings("WeakerAccess")
	@BindView(R.id.display_age)
	TextView ageTextView;

	@SuppressWarnings("WeakerAccess")
	@BindView(R.id.display_gender)
	TextView genderTextView;

	@SuppressWarnings("WeakerAccess")
	@BindView(R.id.display_height)
	TextView heightTextView;

	@SuppressWarnings("WeakerAccess")
	@BindView(R.id.display_skin)
	TextView skinTextView;

	@SuppressWarnings("WeakerAccess")
	@BindView(R.id.display_hair)
	TextView hairTextView;

	@SuppressWarnings("WeakerAccess")
	@BindView(R.id.display_location)
	TextView locationTextView;

	@SuppressWarnings("WeakerAccess")
	@BindView(R.id.display_notes)
	TextView notesTextView;

	private Context context;

	private Bitmap picture;
	private Child child;

	private Unbinder unbinder;

	@NonNull
	public static DisplayFoundController newInstance(Child child) {

		final Bundle bundle = new Bundle(1);

		bundle.putParcelable(ARG_FOUND, Parcels.wrap(child));

		return new DisplayFoundController(bundle);
	}

	@NonNull
	public static DisplayFoundController newInstance(Child child, byte[] imageBytes) {

		final Bundle bundle = new Bundle(2);

		bundle.putByteArray(ARG_IMAGE_BYTES, imageBytes);
		bundle.putParcelable(ARG_FOUND, Parcels.wrap(child));

		return new DisplayFoundController(bundle);
	}

	@SuppressWarnings("WeakerAccess")
	public DisplayFoundController(@Nullable Bundle args) {
		super(args);
	}

	@NonNull
	@Override
	protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {

		final View view = inflater.inflate(R.layout.controller_display_found, container, false);

		unbinder = ButterKnife.bind(this, view);

		context = view.getContext();

		setSupportActionBar(toolbar);

		child = Parcels.unwrap(getArgs().getParcelable(ARG_FOUND));
		picture = FirebaseContract.Storage.getImageBitmap(getArgs().getByteArray(ARG_IMAGE_BYTES));

		populateUi();

		return view;
	}

	private void populateUi() {

		if (picture != null)
			pictureImageView.setImageBitmap(picture);
		else
			Picasso.get().load(child.getPictureUrl()).into(pictureImageView);

		final String name = FirebaseContract.Database.getFullName(context, child.getFirstName(), child.getLastName());
		toolbar.setTitle(name);
		nameTextView.setText(name);

		ageTextView.setText(FirebaseContract.Database.getAge(context, child.getStartAge(), child.getEndAge()));

		genderTextView.setText(FirebaseContract.Database.getGender(context, child.getGender()));

		heightTextView.setText(FirebaseContract.Database.getHeight(context, child.getStartHeight(), child.getEndHeight()));

		skinTextView.setText(FirebaseContract.Database.getSkin(context, child.getSkin()));

		hairTextView.setText(FirebaseContract.Database.getHair(context, child.getHair()));

		if (!TextUtils.isEmpty(child.getLocation()))
			locationTextView.setText(FirebaseContract.Database.getLocation(context, child.getLocation()));

		if (!TextUtils.isEmpty(child.getNotes()))
			notesTextView.setText(child.getNotes());
	}

	@Override
	protected void onDestroy() {
		unbinder.unbind();
		super.onDestroy();
	}
}
