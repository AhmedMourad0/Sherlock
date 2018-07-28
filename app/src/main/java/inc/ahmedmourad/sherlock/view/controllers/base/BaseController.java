package inc.ahmedmourad.sherlock.view.controllers.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.bluelinelabs.conductor.Controller;

public abstract class BaseController extends Controller {

	protected BaseController() {

	}

	protected BaseController(@Nullable Bundle args) {
		super(args);
	}

	protected void setSupportActionBar(final @NonNull Toolbar toolbar) {
		if (getActivity() != null)
			((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
	}
}
