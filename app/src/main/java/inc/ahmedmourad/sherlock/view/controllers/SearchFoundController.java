package inc.ahmedmourad.sherlock.view.controllers;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.bluelinelabs.conductor.RouterTransaction;
import com.google.android.gms.location.places.ui.PlacePicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import inc.ahmedmourad.sherlock.R;
import inc.ahmedmourad.sherlock.model.pojo.SearchCriteria;
import inc.ahmedmourad.sherlock.utils.ErrorUtils;
import inc.ahmedmourad.sherlock.view.controllers.base.BaseFoundController;
import inc.ahmedmourad.sherlock.viewmodel.SearchFoundViewModel;
import inc.ahmedmourad.sherlock.viewmodel.base.BaseFoundViewModel;

import static android.app.Activity.RESULT_OK;

public class SearchFoundController extends BaseFoundController {

	@SuppressWarnings("WeakerAccess")
	@BindView(R.id.toolbar)
	Toolbar toolbar;

	@SuppressWarnings("WeakerAccess")
	@BindView(R.id.search_found_age_number_picker)
	NumberPicker ageNumberPicker;

	@SuppressWarnings("WeakerAccess")
	@BindView(R.id.search_found_height_number_picker)
	NumberPicker heightNumberPicker;

	@SuppressWarnings("WeakerAccess")
	@BindView(R.id.search_found_location_text_view)
	TextView locationTextView;

	@SuppressWarnings("WeakerAccess")
	@BindView(R.id.search_found_location_image_view)
	ImageView locationImageView;

	@SuppressWarnings("WeakerAccess")
	@BindView(R.id.search_found_search_button)
	Button searchButton;

	private Context context;

	private SearchFoundViewModel viewModel;

	private Unbinder unbinder;

	@NonNull
	public static SearchFoundController newInstance() {
		return new SearchFoundController();
	}

	@NonNull
	@Override
	protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {

		final View view = inflater.inflate(R.layout.controller_search_found, container, false);

		unbinder = ButterKnife.bind(this, view);

		context = view.getContext();

		setSupportActionBar(toolbar);

		toolbar.setTitle(context.getString(R.string.search));

		viewModel = viewModelProvider().get(SearchFoundViewModel.class);

		initializeSkinColorViews();
		initializeHairColorViews();
		initializeEditTexts();
		initializeGenderRadioGroup();
		initializeLocationTextView();
		initializeNumberPickers();

		locationImageView.setOnClickListener(this);
		locationTextView.setOnClickListener(this);
		skinWhiteView.setOnClickListener(this);
		skinWheatView.setOnClickListener(this);
		skinDarkView.setOnClickListener(this);
		hairBlondeView.setOnClickListener(this);
		hairBrownView.setOnClickListener(this);
		hairDarkView.setOnClickListener(this);
		searchButton.setOnClickListener(this);

		return view;
	}

	private void initializeNumberPickers() {
		ageNumberPicker.setValue(viewModel.getAge());
		heightNumberPicker.setValue(viewModel.getHeight());
		ageNumberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> viewModel.setAge(newVal));
		heightNumberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> viewModel.setHeight(newVal));
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

	private void search() {
		getRouter().pushController(
				RouterTransaction.with(
						SearchResultsController.newInstance(
								SearchCriteria.fromViewModel(viewModel)
						)
				)
		);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

		setLocationEnabled(true);

		if (resultCode != RESULT_OK)
			return;

		if (data == null) {
			ErrorUtils.general(context, null);
			return;
		}

		switch (requestCode) {

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
	protected void setLocationEnabled(boolean enabled) {
		locationImageView.setEnabled(enabled);
		locationTextView.setEnabled(enabled);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);

		switch (v.getId()) {

			case R.id.search_found_location_image_view:
			case R.id.search_found_location_text_view:
				startPlacePicker();
				break;

			case R.id.search_found_search_button:
				search();
				break;
		}
	}
}
