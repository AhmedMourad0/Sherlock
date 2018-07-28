package inc.ahmedmourad.sherlock.external.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import inc.ahmedmourad.sherlock.R;
import inc.ahmedmourad.sherlock.model.contract.FirebaseContract;
import inc.ahmedmourad.sherlock.model.pojo.SearchResult;
import inc.ahmedmourad.sherlock.model.room.database.SherlockDatabase;
import inc.ahmedmourad.sherlock.utils.ErrorUtils;
import inc.ahmedmourad.sherlock.utils.ListUtils;
import io.reactivex.disposables.Disposable;

class IngredientsRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

	private final Context context;

	private List<SearchResult> results = new ArrayList<>();

	private Disposable disposable;

	IngredientsRemoteViewsFactory(final Context context) {
		this.context = context.getApplicationContext();
	}

	@Override
	public void onCreate() {

	}

	@Override
	public void onDataSetChanged() {

		if (disposable != null)
			disposable.dispose();

		disposable = SherlockDatabase.getInstance(context)
				.resultsDao()
				.getResultsSingle()
				.subscribe(entities -> results = ListUtils.toSearchResults(entities),
						throwable -> ErrorUtils.general(context, throwable));
	}

	@Override
	public int getCount() {
		return results.size();
	}

	@Override
	public RemoteViews getViewAt(final int position) {

		if (position >= results.size())
			return null;

		final SearchResult result = results.get(position);

		final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.item_widget_result);

		views.setTextViewText(R.id.widget_result_date, result.getDate());
		views.setTextViewText(R.id.widget_result_notes, result.getChild().getNotes());
		views.setTextViewText(R.id.widget_result_location, FirebaseContract.Database.getLocation(context, result.getChild().getLocation()));

		setPicture(views, result.getChild().getPictureUrl());

		return views;
	}

	private void setPicture(@NonNull final RemoteViews views, @NonNull final String pictureUrl) {

		Bitmap bitmap;

		try {
			bitmap = Picasso.get()
					.load(pictureUrl)
					.get();
		} catch (IOException e) {
			bitmap = null;
			e.printStackTrace();
		}

		if (bitmap != null)
			views.setImageViewBitmap(R.id.widget_result_picture, bitmap);
		else
			views.setImageViewResource(R.id.widget_result_picture, R.drawable.placeholder);
	}

	@Override
	public RemoteViews getLoadingView() {
		return null;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public long getItemId(final int position) {
		return position;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public void onDestroy() {
		disposable.dispose();
	}
}
