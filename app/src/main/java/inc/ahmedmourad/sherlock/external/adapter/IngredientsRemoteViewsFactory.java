package inc.ahmedmourad.sherlock.external.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

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

	private int widgetId;

	private Disposable disposable;

	IngredientsRemoteViewsFactory(final Context context, final int widgetId) {
		this.context = context.getApplicationContext();
		this.widgetId = widgetId;
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

//		Picasso.get()
//				.load(result.getChild().getPictureUrl())
//				.into(views, R.id.widget_result_picture, new int[]{widgetId});

//		try {
//			views.setImageViewBitmap(R.id.widget_result_picture, Picasso.get()
//					.load(result.getChild().getPictureUrl())
//					.get());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

//		Log.e("00000000000000000000000", "0");
//		Picasso.get()
//				.load(result.getChild().getPictureUrl())
//				.into(new Target() {
//					@Override
//					public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//						views.setImageViewBitmap(R.id.widget_result_picture, bitmap);
//					}
//
//					@Override
//					public void onBitmapFailed(Exception e, Drawable errorDrawable) {
//						ErrorUtils.general(context, e);
//						views.setImageViewResource(R.id.widget_result_picture, R.drawable.placeholder);
//					}
//
//					@Override
//					public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//					}
//				});

		Log.e("00000000000000000000000", result.toString());

		return views;
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
