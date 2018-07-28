package inc.ahmedmourad.sherlock.external.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

import java.util.UUID;

import inc.ahmedmourad.sherlock.R;
import inc.ahmedmourad.sherlock.external.adapter.IngredientsRemoteViewsService;
import inc.ahmedmourad.sherlock.model.room.database.SherlockDatabase;
import inc.ahmedmourad.sherlock.utils.DisposablesSparseArray;
import inc.ahmedmourad.sherlock.utils.ErrorUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AppWidget extends AppWidgetProvider {

	private DisposablesSparseArray disposables = new DisposablesSparseArray();

	@Override
	public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
		// There may be multiple widgets active, so update all of them
		for (int appWidgetId : appWidgetIds)
			disposables.put(appWidgetId, updateAppWidget(context, appWidgetManager, appWidgetId));
	}

	/**
	 * Used to update the ui of a certain widget
	 *
	 * @param context          The Context in which this receiver is running.
	 * @param appWidgetManager A AppWidgetManager object you can call AppWidgetManager.updateAppWidget on.
	 * @param appWidgetId      The appWidgetIds for which an update is needed.
	 */
	@NonNull
	private Disposable updateAppWidget(final Context context, final AppWidgetManager appWidgetManager, final int appWidgetId) {

		final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);

		views.setImageViewResource(R.id.widget_icon, R.drawable.ic_sherlock);

		views.setEmptyView(R.id.widget_list_view, R.id.widget_empty);

		return SherlockDatabase.getInstance(context)
				.resultsDao()
				.getAllIds()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(ids -> {

					final Intent intent = new Intent(context, IngredientsRemoteViewsService.class);

					intent.putExtra(IngredientsRemoteViewsService.EXTRA_WIDGET_ID, appWidgetId);

					// Each uri must be unique in order for the widget to be updated
					intent.setData(getUniqueDataUri(appWidgetId));

					views.setRemoteAdapter(R.id.widget_list_view, intent);

					// Instruct the widget manager to update the widget
					appWidgetManager.updateAppWidget(appWidgetId, views);

				}, throwable -> ErrorUtils.general(context, throwable));
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		disposables.dispose(appWidgetIds);
		super.onDeleted(context, appWidgetIds);
	}

	/**
	 * generates a unique data uri using the given values
	 *
	 * @param appWidgetId the widget id
	 * @return a unique data uri using the given values
	 */
	private static Uri getUniqueDataUri(final int appWidgetId) {
		return Uri.withAppendedPath(Uri.parse("sherlock://widget/id/"), String.valueOf(appWidgetId) + UUID.randomUUID());
	}

	@Override
	public void onDisabled(Context context) {
		disposables.dispose();
		super.onDisabled(context);
	}
}
