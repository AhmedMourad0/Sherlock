package inc.ahmedmourad.sherlock.view.controllers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bluelinelabs.conductor.RouterTransaction;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import inc.ahmedmourad.sherlock.R;
import inc.ahmedmourad.sherlock.adapters.SectionsRecyclerAdapter;
import inc.ahmedmourad.sherlock.adapters.pojo.Section;
import inc.ahmedmourad.sherlock.view.controllers.base.BaseController;

public class HomeController extends BaseController {

	@BindView(R.id.toolbar)
	Toolbar toolbar;

	@BindView(R.id.home_recycler)
	RecyclerView recyclerView;

	private Context context;

	private Unbinder unbinder;

	@NonNull
	public static HomeController newInstance() {
		return new HomeController();
	}

	@NonNull
	@Override
	protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {

		final View view = inflater.inflate(R.layout.controller_home, container, false);

		unbinder = ButterKnife.bind(this, view);

		context = view.getContext();

		setSupportActionBar(toolbar);

		initializeRecyclerView();

		return view;
	}

	private void initializeRecyclerView() {
		final SectionsRecyclerAdapter adapter = new SectionsRecyclerAdapter(createSectionsList(), sectionController -> {
			if (sectionController == null)
				Toast.makeText(context.getApplicationContext(), R.string.coming_soon, Toast.LENGTH_LONG).show();
			else
				getRouter().pushController(RouterTransaction.with(sectionController));
		});
		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new GridLayoutManager(context, context.getResources().getInteger(R.integer.home_column_count)));
		recyclerView.setVerticalScrollBarEnabled(true);
	}

	private List<Section> createSectionsList() {
		final List<Section> list = new ArrayList<>(4);
		list.add(Section.create(context.getString(R.string.found_a_child), R.drawable.found_child, AddFoundController.newInstance()));
		list.add(Section.create(context.getString(R.string.search), R.drawable.search_child, SearchFoundController.newInstance()));
		list.add(Section.create(context.getString(R.string.coming_soon), R.drawable.coming_soon, null));
		list.add(Section.create(context.getString(R.string.coming_soon), R.drawable.coming_soon, null));
		return list;
	}

	@Override
	protected void onDestroy() {
		unbinder.unbind();
		super.onDestroy();
	}
}
