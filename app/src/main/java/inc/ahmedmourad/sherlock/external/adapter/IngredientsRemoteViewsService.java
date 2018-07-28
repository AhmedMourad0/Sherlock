package inc.ahmedmourad.sherlock.external.adapter;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class IngredientsRemoteViewsService extends RemoteViewsService {

	@Override
	public RemoteViewsFactory onGetViewFactory(final Intent intent) {
		return new IngredientsRemoteViewsFactory(this);
	}
}
