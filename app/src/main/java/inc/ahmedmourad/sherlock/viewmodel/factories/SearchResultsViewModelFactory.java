package inc.ahmedmourad.sherlock.viewmodel.factories;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import inc.ahmedmourad.sherlock.model.pojo.SearchCriteria;
import inc.ahmedmourad.sherlock.viewmodel.SearchResultsViewModel;

public class SearchResultsViewModelFactory extends ViewModelProvider.NewInstanceFactory {

	private final Application application;
	private final SearchCriteria criteria;

	public SearchResultsViewModelFactory(@NonNull final Application application, @NonNull final SearchCriteria criteria) {
		this.application = application;
		this.criteria = criteria;
	}

	@SuppressWarnings("unchecked")
	@NonNull
	@Override
	public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
		return (T) new SearchResultsViewModel(application, criteria);
	}
}
