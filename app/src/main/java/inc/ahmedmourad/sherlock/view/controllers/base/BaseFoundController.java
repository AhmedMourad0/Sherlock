package inc.ahmedmourad.sherlock.view.controllers.base;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RadioGroup;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;

import butterknife.BindView;
import inc.ahmedmourad.sherlock.R;
import inc.ahmedmourad.sherlock.interfaces.Hair;
import inc.ahmedmourad.sherlock.interfaces.Skin;
import inc.ahmedmourad.sherlock.utils.ColorSelector;
import inc.ahmedmourad.sherlock.utils.ErrorUtils;
import inc.ahmedmourad.sherlock.viewmodel.base.BaseFoundViewModel;

public abstract class BaseFoundController extends BaseLifecycleController implements View.OnClickListener {

	protected static final int PLACE_PICKER_REQUEST = 1;

	@BindView(R.id.found_skin_white)
	protected View skinWhiteView;

	@BindView(R.id.found_skin_wheat)
	protected View skinWheatView;

	@BindView(R.id.found_skin_dark)
	protected View skinDarkView;

	@BindView(R.id.found_hair_blonde)
	protected View hairBlondeView;

	@BindView(R.id.found_hair_brown)
	protected View hairBrownView;

	@BindView(R.id.found_hair_dark)
	protected View hairDarkView;

	@SuppressWarnings("WeakerAccess")
	@BindView(R.id.found_first_name_edit_text)
	protected TextInputEditText firstNameEditText;

	@SuppressWarnings("WeakerAccess")
	@BindView(R.id.found_last_name_edit_text)
	protected TextInputEditText lastNameEditText;

	@SuppressWarnings("WeakerAccess")
	@BindView(R.id.found_gender_radio_group)
	protected RadioGroup genderRadioGroup;

	private ColorSelector skinColorSelector;
	private ColorSelector hairColorSelector;

	@NonNull
	protected abstract BaseFoundViewModel getViewModel();

	@NonNull
	protected abstract Context getContext();

	protected abstract void setLocationEnabled(final boolean enabled);

	protected void initializeSkinColorViews() {

		skinColorSelector = new ColorSelector(getContext(),
				ColorSelector.newItem(Skin.SKIN_WHITE, skinWhiteView, R.color.colorSkinWhite),
				ColorSelector.newItem(Skin.SKIN_WHEAT, skinWheatView, R.color.colorSkinWheat),
				ColorSelector.newItem(Skin.SKIN_DARK, skinDarkView, R.color.colorSkinDark)
		);

		skinColorSelector.initializeViews(getViewModel().getSkinColor());

		skinColorSelector.setOnSelectionChangeListener(getViewModel()::setSkinColor);
	}

	protected void initializeHairColorViews() {

		hairColorSelector = new ColorSelector(getContext(),
				ColorSelector.newItem(Hair.HAIR_BLONDE, hairBlondeView, R.color.colorHairBlonde),
				ColorSelector.newItem(Hair.HAIR_BROWN, hairBrownView, R.color.colorHairBrown),
				ColorSelector.newItem(Hair.HAIR_DARK, hairDarkView, R.color.colorHairDark)
		);

		hairColorSelector.initializeViews(getViewModel().getHairColor());

		hairColorSelector.setOnSelectionChangeListener(getViewModel()::setHairColor);
	}

	protected void initializeEditTexts() {

		firstNameEditText.setText(getViewModel().getFirstName());
		lastNameEditText.setText(getViewModel().getLastName());

		firstNameEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				getViewModel().setFirstName(s.toString());
			}
		});

		lastNameEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				getViewModel().setLastName(s.toString());
			}
		});
	}

	protected void initializeGenderRadioGroup() {
		genderRadioGroup.check(getViewModel().getGenderResId());
		genderRadioGroup.setOnCheckedChangeListener((group, checkedId) -> getViewModel().setGenderResId(checkedId));
	}

	protected void startPlacePicker() {

		if (getActivity() == null) {
			ErrorUtils.general(getContext(), null);
			return;
		}

		try {

			setLocationEnabled(false);

			final Intent builder = new PlacePicker.IntentBuilder().build(getActivity());

			startActivityForResult(builder, PLACE_PICKER_REQUEST);

		} catch (final GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
			setLocationEnabled(true);
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

			case R.id.found_skin_white:
				skinColorSelector.select(Skin.SKIN_WHITE);
				break;

			case R.id.found_skin_wheat:
				skinColorSelector.select(Skin.SKIN_WHEAT);
				break;

			case R.id.found_skin_dark:
				skinColorSelector.select(Skin.SKIN_DARK);
				break;

			case R.id.found_hair_blonde:
				hairColorSelector.select(Hair.HAIR_BLONDE);
				break;

			case R.id.found_hair_brown:
				hairColorSelector.select(Hair.HAIR_BROWN);
				break;

			case R.id.found_hair_dark:
				hairColorSelector.select(Hair.HAIR_DARK);
				break;
		}
	}
}
