package inc.ahmedmourad.sherlock.view.controllers;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluelinelabs.conductor.RouterTransaction;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import inc.ahmedmourad.sherlock.R;
import inc.ahmedmourad.sherlock.bus.RxBus;
import inc.ahmedmourad.sherlock.model.contract.FirebaseContract;
import inc.ahmedmourad.sherlock.model.pojo.Child;
import inc.ahmedmourad.sherlock.services.FirebaseIntentService;
import inc.ahmedmourad.sherlock.utils.ErrorUtils;
import inc.ahmedmourad.sherlock.view.controllers.base.BaseFoundController;
import inc.ahmedmourad.sherlock.viewmodel.AddFoundViewModel;
import inc.ahmedmourad.sherlock.viewmodel.base.BaseFoundViewModel;

import static android.app.Activity.RESULT_OK;

public class AddFoundController extends BaseFoundController {

	private static final int PLACE_PICKER_REQUEST = 1;
	private static final int IMAGE_PICKER_REQUEST = 2;

	@BindView(R.id.toolbar)
	Toolbar toolbar;

	@BindView(R.id.add_found_age_seek_bar)
	RangeSeekBar ageSeekBar;

	@BindView(R.id.add_found_height_seek_bar)
	RangeSeekBar heightSeekBar;

	@BindView(R.id.add_found_location_text_view)
	TextView locationTextView;

	@BindView(R.id.add_found_location_image_view)
	ImageView locationImageView;

	@BindView(R.id.add_found_picture_image_view)
	CircleImageView pictureImageView;

	@BindView(R.id.add_found_picture_text_view)
	TextView pictureTextView;

	@BindView(R.id.add_found_notes_edit_text)
	TextInputEditText notesEditText;

	@BindView(R.id.add_found_publish_button)
	Button publishButton;

	private Context context;

	private AddFoundViewModel viewModel;

	private Unbinder unbinder;

	@NonNull
	public static AddFoundController newInstance() {
		return new AddFoundController();
	}

	@NonNull
	@Override
	protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {

		final View view = inflater.inflate(R.layout.controller_add_found, container, false);

		unbinder = ButterKnife.bind(this, view);

		context = view.getContext();

		setSupportActionBar(toolbar);

		toolbar.setTitle(context.getString(R.string.found_a_child));

		viewModel = viewModelProvider().get(AddFoundViewModel.class);

		initializeSeekBars();
		initializeSkinColorViews();
		initializeHairColorViews();
		initializeEditTexts();
		initializeGenderRadioGroup();
		initializePictureImageView();
		initializeLocationTextView();

		locationImageView.setOnClickListener(this);
		locationTextView.setOnClickListener(this);
		pictureImageView.setOnClickListener(this);
		publishButton.setOnClickListener(this);
		skinWhiteView.setOnClickListener(this);
		skinWheatView.setOnClickListener(this);
		skinDarkView.setOnClickListener(this);
		hairBlondeView.setOnClickListener(this);
		hairBrownView.setOnClickListener(this);
		hairDarkView.setOnClickListener(this);
		pictureTextView.setOnClickListener(this);
		pictureTextView.setOnClickListener(this);

		return view;
	}

	private void initializeSeekBars() {

		ageSeekBar.setIndicatorTextDecimalFormat("##");
		ageSeekBar.setValue(viewModel.getStartAge(), viewModel.getEndAge());
		ageSeekBar.setOnRangeChangedListener(new OnRangeChangedListener() {
			@Override
			public void onRangeChanged(RangeSeekBar view, float min, float max, boolean isFromUser) {
				viewModel.setStartAge(Math.round(min));
				viewModel.setEndAge(Math.round(max));
			}

			@Override
			public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

			}

			@Override
			public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

			}
		});

		heightSeekBar.setIndicatorTextDecimalFormat("###");
		heightSeekBar.setValue(viewModel.getStartHeight(), viewModel.getEndHeight());
		heightSeekBar.setOnRangeChangedListener(new OnRangeChangedListener() {
			@Override
			public void onRangeChanged(RangeSeekBar view, float min, float max, boolean isFromUser) {
				viewModel.setStartHeight(Math.round(min));
				viewModel.setEndHeight(Math.round(max));
			}

			@Override
			public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

			}

			@Override
			public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

			}
		});
	}

	@Override
	protected void initializeEditTexts() {
		super.initializeEditTexts();

		notesEditText.setText(viewModel.getNotes());

		notesEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				viewModel.setNotes(s.toString());
			}
		});
	}

	private void initializePictureImageView() {
		if (viewModel.getPicture() != null)
			pictureImageView.setImageBitmap(viewModel.getPicture());
	}

	private void initializeLocationTextView() {

		if (viewModel.getPlace() != null) {

			final CharSequence place = viewModel.getPlace().getName();

			if (TextUtils.isEmpty(place))
				locationTextView.setText(R.string.no_internet_connection);
			else
				locationTextView.setText(place);
		}
	}

	private void publish() {

		RxBus.getInstance().setPublishingState(RxBus.STATE_PUBLISHING_ONGOING);

		publishButton.setEnabled(false);

		final Child child = Child.fromViewModel(viewModel);

		final byte[] imageBytes = FirebaseContract.Storage.getImageBytes(pictureImageView);

		FirebaseIntentService.startActionPublishFound(context.getApplicationContext(), child, imageBytes);

		getRouter().popToRoot();
		getRouter().pushController(RouterTransaction.with(DisplayFoundController.newInstance(child, imageBytes)));
	}

	private void startImagePicker() {

		if (getActivity() == null) {
			ErrorUtils.general(context, null);
			return;
		}

		setPictureEnabled(false);

		startActivityForResult(ImagePicker.create(getActivity())
				.returnMode(ReturnMode.ALL)
				.folderMode(true)
				.toolbarFolderTitle(context.getString(R.string.pick_a_folder))
				.toolbarImageTitle(context.getString(R.string.tap_to_select))
				.single()
				.limit(1)
				.showCamera(true)
				.imageDirectory(context.getString(R.string.camera))
				.theme(R.style.ImagePickerTheme)
				.enableLog(true)
				.getIntent(context), IMAGE_PICKER_REQUEST
		);
	}

	private void setPictureEnabled(final boolean enabled) {
		pictureImageView.setEnabled(enabled);
		pictureTextView.setEnabled(enabled);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

		setLocationEnabled(true);
		setPictureEnabled(true);

		if (resultCode != RESULT_OK)
			return;

		if (data == null) {
			ErrorUtils.general(context, null);
			return;
		}

		switch (requestCode) {

			case IMAGE_PICKER_REQUEST:
				final Image image = ImagePicker.getFirstImageOrNull(data);
				if (image != null)
					viewModel.setPicture(BitmapFactory.decodeFile(image.getPath()));
				if (viewModel.getPicture() != null)
					pictureImageView.setImageBitmap(viewModel.getPicture());
				break;

			case PLACE_PICKER_REQUEST:
				viewModel.setPlace(PlacePicker.getPlace(context, data));
				initializeLocationTextView();
				break;
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onDestroy() {
		unbinder.unbind();
		super.onDestroy();
	}

	@NonNull
	@Override
	protected BaseFoundViewModel getViewModel() {
		return viewModel;
	}

	@NonNull
	@Override
	protected Context getContext() {
		return context;
	}

	@Override
	protected void setLocationEnabled(final boolean enabled) {
		locationImageView.setEnabled(enabled);
		locationTextView.setEnabled(enabled);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);

		switch (v.getId()) {

			case R.id.add_found_location_image_view:
			case R.id.add_found_location_text_view:
				startPlacePicker();
				break;

			case R.id.add_found_picture_image_view:
			case R.id.add_found_picture_text_view:
				startImagePicker();
				break;

			case R.id.add_found_publish_button:
				publish();
				break;
		}
	}
}
