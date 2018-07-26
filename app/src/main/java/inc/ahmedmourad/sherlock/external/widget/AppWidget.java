package inc.ahmedmourad.sherlock.external.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import inc.ahmedmourad.sherlock.R;
import inc.ahmedmourad.sherlock.external.adapter.IngredientsRemoteViewsService;

public class AppWidget extends AppWidgetProvider {

	@Override
	public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
		// There may be multiple widgets active, so update all of them
		for (int appWidgetId : appWidgetIds)
			updateAppWidget(context, appWidgetManager, appWidgetId);
	}

	/**
	 * Used to update the ui of a certain widget
	 *
	 * @param context          The Context in which this receiver is running.
	 * @param appWidgetManager A AppWidgetManager object you can call AppWidgetManager.updateAppWidget on.
	 * @param appWidgetId      The appWidgetIds for which an update is needed.
	 */
	private void updateAppWidget(final Context context, final AppWidgetManager appWidgetManager, final int appWidgetId) {

		final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);

		views.setImageViewResource(R.id.widget_icon, R.drawable.ic_sherlock);

		final Intent intent = new Intent(context, IngredientsRemoteViewsService.class);

		views.setRemoteAdapter(R.id.widget_list_view, intent);

		views.setEmptyView(R.id.widget_list_view, R.id.widget_empty);

		// Instruct the widget manager to update the widget
		appWidgetManager.updateAppWidget(appWidgetId, views);
	}
}
