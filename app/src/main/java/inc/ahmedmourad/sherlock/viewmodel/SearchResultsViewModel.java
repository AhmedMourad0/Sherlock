package inc.ahmedmourad.sherlock.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import inc.ahmedmourad.sherlock.model.pojo.Child;
import inc.ahmedmourad.sherlock.model.pojo.SearchCriteria;
import inc.ahmedmourad.sherlock.model.pojo.SearchResult;
import inc.ahmedmourad.sherlock.model.room.database.SherlockDatabase;
import inc.ahmedmourad.sherlock.model.room.entities.ResultEntity;
import inc.ahmedmourad.sherlock.repository.SearchResultsRepository;
import inc.ahmedmourad.sherlock.utils.ErrorUtils;
import inc.ahmedmourad.sherlock.utils.ListUtils;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchResultsViewModel extends ViewModel {

	private final Application application;

	private final SearchResultsRepository repository;

	private final SherlockDatabase db;

	private final LiveData<List<ResultEntity>> searchResults;

	private Disposable disposable;

	public SearchResultsViewModel(@NonNull final Application application, @NonNull final SearchCriteria criteria) {
		this.application = application;
		repository = new SearchResultsRepository();
		db = SherlockDatabase.getInstance(application);
		searchResults = db.resultsDao().getResults();
		search(criteria);
	}

	private void search(@NonNull final SearchCriteria criteria) {
		repository.filterBySkin(criteria.getSkin(), new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

				final List<SearchResult> results = new ArrayList<>();

				for (DataSnapshot snapshot : dataSnapshot.getChildren())
					results.add(SearchResult.create(application, Long.parseLong(snapshot.getKey()), Child.fromDataSnapshot(snapshot)));

				criteria.filter(results);

				disposable = Completable.fromAction(() -> db.resultsDao().replaceResults(ListUtils.toResultEntities(results)))
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(() -> {

						}, throwable -> ErrorUtils.general(application, throwable));
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
				ErrorUtils.firebase(application, databaseError);
			}
		});
	}

	@NonNull
	public LiveData<List<ResultEntity>> getSearchResults() {
		return searchResults;
	}

	@Override
	protected void onCleared() {
		if (disposable != null)
			disposable.dispose();
		super.onCleared();
	}
}
