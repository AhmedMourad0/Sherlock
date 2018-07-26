package inc.ahmedmourad.sherlock.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.NumberPicker;

import inc.ahmedmourad.sherlock.R;

public class ProperNumberPicker extends NumberPicker {

	public ProperNumberPicker(Context context) {
		super(context);
	}

	public ProperNumberPicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		processXmlValues(context, attrs);
	}

	public ProperNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		processXmlValues(context, attrs, defStyleAttr);
	}

	private void processXmlValues(final Context context, final AttributeSet attrs) {
		processXmlValues(context, attrs, 0);
	}

	private void processXmlValues(final Context context, final AttributeSet attrs, final int defStyleAttr) {

		final TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.ProperNumberPicker,
				defStyleAttr,
				0
		);

		try {

			setMinValue(attributes.getInt(R.styleable.ProperNumberPicker_minValue, 0));
			setMaxValue(attributes.getInt(R.styleable.ProperNumberPicker_maxValue, 100));
			setValue(attributes.getInt(R.styleable.ProperNumberPicker_value, 50));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			attributes.recycle();
		}
	}

	@Override
	public int getValue() {
		clearFocus();
		return super.getValue();
	}
}
