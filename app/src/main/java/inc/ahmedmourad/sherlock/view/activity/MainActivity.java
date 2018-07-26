package inc.ahmedmourad.sherlock.view.activity;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bluelinelabs.conductor.ChangeHandlerFrameLayout;
import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.google.firebase.FirebaseApp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import inc.ahmedmourad.sherlock.R;
import inc.ahmedmourad.sherlock.bus.RxBus;
import inc.ahmedmourad.sherlock.utils.ErrorUtils;
import inc.ahmedmourad.sherlock.view.controllers.HomeController;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

	@BindView(R.id.controller_container)
	ChangeHandlerFrameLayout container;

	private Disposable disposable;

	private Router router;

	private Unbinder unbinder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		unbinder = ButterKnife.bind(this);

		FirebaseApp.initializeApp(getApplicationContext());

		router = Conductor.attachRouter(this, container, savedInstanceState);

		if (!router.hasRootController())
			router.setRoot(RouterTransaction.with(HomeController.newInstance()));
	}

	@Override
	protected void onStart() {
		super.onStart();
		disposable = RxBus.getInstance()
				.getPublishingStateRelay()
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(state -> {

					switch (state) {

						case RxBus.STATE_PUBLISHING_ONGOING:
							Snackbar.make(container, R.string.publishing, Snackbar.LENGTH_INDEFINITE).show();
							break;

						case RxBus.STATE_PUBLISHING_FAILURE:
							Snackbar.make(container, R.string.something_went_wrong, Snackbar.LENGTH_LONG).show();
							break;

						case RxBus.STATE_PUBLISHING_SUCCESS:
							Snackbar.make(container, R.string.published_successfully, Snackbar.LENGTH_LONG).show();
							break;
					}

				}, throwable -> ErrorUtils.general(getApplicationContext(), throwable));
	}

	@Override
	protected void onStop() {
		disposable.dispose();
		super.onStop();
	}

	@Override
	public void onBackPressed() {
		if (!router.handleBack())
			super.onBackPressed();
	}

	@Override
	protected void onDestroy() {
		unbinder.unbind();
		super.onDestroy();
	}
}
