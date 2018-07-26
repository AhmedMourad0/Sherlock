package inc.ahmedmourad.sherlock.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import inc.ahmedmourad.sherlock.R;
import inc.ahmedmourad.sherlock.model.contract.FirebaseContract;
import inc.ahmedmourad.sherlock.model.pojo.Child;
import inc.ahmedmourad.sherlock.model.pojo.SearchResult;

public class ResultsRecyclerAdapter extends RecyclerView.Adapter<ResultsRecyclerAdapter.ViewHolder> {

	private List<SearchResult> resultsList = new ArrayList<>();
	private final OnResultSelectedListener listener;

	public ResultsRecyclerAdapter(@NonNull final OnResultSelectedListener listener) {
		this.listener = listener;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull final ViewGroup container, final int viewType) {
		return new ViewHolder(LayoutInflater.from(container.getContext()).inflate(R.layout.item_result, container, false));
	}

	@Override
	public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
		holder.bind(resultsList.get(position));
	}

	@Override
	public int getItemCount() {
		return resultsList.size();
	}

	public void updateList(@NonNull final List<SearchResult> list) {
		resultsList.clear();
		resultsList.addAll(list);
		notifyDataSetChanged();
	}

	class ViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.result_date)
		TextView dateTextView;

		@BindView(R.id.result_picture)
		ImageView pictureImageView;

		@BindView(R.id.result_notes)
		TextView notesTextView;

		@BindView(R.id.result_location)
		TextView locationTextView;

		private Picasso picasso;

		ViewHolder(final View view) {
			super(view);
			ButterKnife.bind(this, view);
			picasso = Picasso.get();
		}

		private void bind(@NonNull final SearchResult result) {

			picasso.load(result.getChild().getPictureUrl()).into(pictureImageView);

			dateTextView.setText(result.getDate());
			notesTextView.setText(result.getChild().getNotes());
			locationTextView.setText(FirebaseContract.Database.getLocation(itemView.getContext(), result.getChild().getLocation()));

			itemView.setOnClickListener(v -> listener.onResultSelectedListener(result.getChild()));
		}
	}

	@FunctionalInterface
	public interface OnResultSelectedListener {
		void onResultSelectedListener(@NonNull final Child child);
	}
}