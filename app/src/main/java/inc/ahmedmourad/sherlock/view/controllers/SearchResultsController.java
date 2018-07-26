package inc.ahmedmourad.sherlock.view.controllers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.RouterTransaction;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import inc.ahmedmourad.sherlock.R;
import inc.ahmedmourad.sherlock.adapters.ResultsRecyclerAdapter;
import inc.ahmedmourad.sherlock.model.pojo.SearchCriteria;
import inc.ahmedmourad.sherlock.utils.ListUtils;
import inc.ahmedmourad.sherlock.view.controllers.base.BaseLifecycleController;
import inc.ahmedmourad.sherlock.viewmodel.SearchResultsViewModel;
import inc.ahmedmourad.sherlock.viewmodel.factories.SearchResultsViewModelFactory;

public class SearchResultsController extends BaseLifecycleController {

	@BindView(R.id.toolbar)
	Toolbar toolbar;

	@BindView(R.id.search_found_results_recycler)
	RecyclerView recyclerView;

	private Context context;

	private SearchCriteria criteria;

	private ResultsRecyclerAdapter adapter;

	@Nullable
	private SearchResultsViewModel viewModel;

	private Unbinder unbinder;

	@NonNull
	public static SearchResultsController newInstance(@NonNull final SearchCriteria criteria) {
		final SearchResultsController controller = new SearchResultsController();
		controller.setCriteria(criteria);
		return controller;
	}

	@NonNull
	@Override
	protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {

		final View view = inflater.inflate(R.layout.controller_search_found_results, container, false);

		unbinder = ButterKnife.bind(this, view);

		context = view.getContext();

		setSupportActionBar(toolbar);

		toolbar.setTitle(R.string.results);

		initializeRecyclerView();

		if (getActivity() != null)
			viewModel = viewModelProvider(new SearchResultsViewModelFactory(getActivity().getApplication(), criteria)).get(SearchResultsViewModel.class);

		if (viewModel != null) {
			viewModel.getSearchResults().observe(this, resultEntities ->
					adapter.updateList(ListUtils.toSearchResults(resultEntities))
			);
		}

		return view;
	}

	private void initializeRecyclerView() {
		adapter = new ResultsRecyclerAdapter(child ->
				getRouter().pushController(RouterTransaction.with(DisplayFoundController.newInstance(child)))
		);
		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
		recyclerView.setVerticalScrollBarEnabled(true);
	}

	private void setCriteria(@NonNull final SearchCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	protected void onDestroy() {
		unbinder.unbind();
		super.onDestroy();
	}
}
