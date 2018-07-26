package inc.ahmedmourad.sherlock.view.controllers.base;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.bluelinelabs.conductor.archlifecycle.LifecycleController;

import java.util.Objects;

public abstract class BaseLifecycleController extends LifecycleController {

	private ViewModelStore viewModelStore = new ViewModelStore();

	protected ViewModelProvider viewModelProvider(@Nullable ViewModelProvider.NewInstanceFactory factory) {
		return factory == null ? viewModelProvider() : new ViewModelProvider(viewModelStore,  factory);
	}

	protected ViewModelProvider viewModelProvider() {
		return new ViewModelProvider(viewModelStore, defaultFactory());
	}

	@NonNull
	private ViewModelProvider.AndroidViewModelFactory defaultFactory() {
		return new ViewModelProvider.AndroidViewModelFactory(Objects.requireNonNull(getActivity()).getApplication());
	}

	protected void setSupportActionBar(final @NonNull Toolbar toolbar) {
		if (getActivity() != null)
			((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
	}
}
