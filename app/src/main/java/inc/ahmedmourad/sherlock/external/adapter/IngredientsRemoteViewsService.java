package inc.ahmedmourad.sherlock.external.adapter;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class IngredientsRemoteViewsService extends RemoteViewsService {

	public static final String EXTRA_WIDGET_ID = "widget_id";

	@Override
	public RemoteViewsFactory onGetViewFactory(final Intent intent) {
		return new IngredientsRemoteViewsFactory(this, intent.getIntExtra(EXTRA_WIDGET_ID, -1));
	}
}
