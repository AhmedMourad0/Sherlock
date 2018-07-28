package inc.ahmedmourad.sherlock.utils;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorRes;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import inc.ahmedmourad.sherlock.R;

public class ColorSelector {
	
	private static final int NO_ID = -1;
	
	private static final int STROKE_WIDTH = 5;
	private static final int STROKE_COLOR = R.color.colorAccent;

	private final Context context;
	private final Item[] items;
	
	private int selectedItemId = NO_ID;

	private OnItemSelectedListener listener;

	public ColorSelector(@NonNull final Context context, @NonNull Item... items) {
		this.context = context;
		this.items = items;
	}

	public void initializeViews(@IntRange(from = NO_ID) final int defaultItemId) {

		if (defaultItemId == NO_ID) {
			select(NO_ID);
			return;
		}
		
		for (Item item : items) {
			
			item.getView().setBackground(item.getDrawable());
			
			if (defaultItemId == item.getId())
				select(defaultItemId);
		}
	}

	public void setOnSelectionChangeListener(@Nullable final OnItemSelectedListener listener) {
		this.listener = listener;
	}

	public void select(@IntRange(from = NO_ID) final int itemId) {

		if (itemId == selectedItemId)
			return;
		
		for (Item item : items) {
			
			if (item.getId() == selectedItemId) {
				item.getDrawable().setStroke(STROKE_WIDTH, ContextCompat.getColor(context, android.R.color.transparent));

				if (itemId == NO_ID)
					break;

				continue;
			}

			if (item.getId() == itemId)
				item.getDrawable().setStroke(STROKE_WIDTH, ContextCompat.getColor(context, STROKE_COLOR));
		}
		
		selectedItemId = itemId;

		if (listener != null)
			listener.onSelectionChange(getSelectedItemId());
	}

	@IntRange(from = NO_ID)
	private int getSelectedItemId() {
		return selectedItemId;
	}

	@NonNull
	public static Item newItem(@IntRange(from = 0) final int id, @NonNull final View view, @ColorRes final int color) {
		return Item.create(id ,view, color);
	}

	@SuppressWarnings("WeakerAccess")
	public static class Item {

		private int id;
		private View view;
		private int color;

		private GradientDrawable drawable;

		@NonNull
		private static Item create(@IntRange(from = 0) final int id, @NonNull final View view, @ColorRes final int color) {
			final Item item = new Item();
			item.setId(id);
			item.setView(view);
			item.setColor(color);
			return item;
		}

		private Item() {

		}

		@IntRange(from = 0)
		int getId() {
			return id;
		}

		void setId(@IntRange(from = 0) final int id) {
			this.id = id;
		}

		@NonNull
		View getView() {
			return view;
		}

		void setView(@NonNull final View view) {
			this.view = view;
		}

		@ColorRes
		int getColor() {
			return color;
		}

		void setColor(@ColorRes final int color) {
			this.color = color;
			drawable = generateColoredCircleDrawable(getColor());
		}

		@NonNull
		private GradientDrawable generateColoredCircleDrawable(@ColorRes final int color) {
			GradientDrawable drawable = new GradientDrawable();
			drawable.setShape(GradientDrawable.OVAL);
			drawable.setColor(ContextCompat.getColor(getView().getContext(), color));
			drawable.setStroke(STROKE_WIDTH, ContextCompat.getColor(getView().getContext(), android.R.color.transparent));
			return drawable;
		}

		@NonNull
		GradientDrawable getDrawable() {
			return drawable;
		}
	}

	@FunctionalInterface
	public interface OnItemSelectedListener {
		void onSelectionChange(final int itemId);
	}
}
