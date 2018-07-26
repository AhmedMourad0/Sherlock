package inc.ahmedmourad.sherlock.external.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import inc.ahmedmourad.sherlock.R;
import inc.ahmedmourad.sherlock.model.contract.FirebaseContract;
import inc.ahmedmourad.sherlock.model.pojo.SearchResult;
import inc.ahmedmourad.sherlock.model.room.database.SherlockDatabase;
import inc.ahmedmourad.sherlock.utils.ErrorUtils;
import inc.ahmedmourad.sherlock.utils.ListUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

class IngredientsRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

	private final Context context;

	private List<SearchResult> results = new ArrayList<>();

	private Disposable disposable;

	IngredientsRemoteViewsFactory(final Context context) {
		this.context = context.getApplicationContext();
	}

	@Override
	public void onCreate() {
		disposable = SherlockDatabase.getInstance(context)
				.resultsDao()
				.getResultsFlowable()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(entities -> this.results = ListUtils.toSearchResults(entities),
						throwable -> ErrorUtils.general(context, throwable));
	}

	@Override
	public void onDataSetChanged() {

	}

	@Override
	public int getCount() {
		return results.size();
	}

	@Override
	public RemoteViews getViewAt(final int position) {

		if (results.size() == 0)
			return null;

		final SearchResult result = results.get(position);

		final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.item_widget_result);

		views.setTextViewText(R.id.widget_result_date, result.getDate());
		views.setTextViewText(R.id.widget_result_notes, result.getChild().getNotes());
		views.setTextViewText(R.id.widget_result_location, FirebaseContract.Database.getLocation(context, result.getChild().getLocation()));

		Picasso.get().load(result.getChild().getPictureUrl()).into(new Target() {
			@Override
			public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
				views.setImageViewBitmap(R.id.widget_result_picture, bitmap);
			}

			@Override
			public void onBitmapFailed(Exception e, Drawable errorDrawable) {
				ErrorUtils.general(context, e);
			}

			@Override
			public void onPrepareLoad(Drawable placeHolderDrawable) {

			}
		});

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
