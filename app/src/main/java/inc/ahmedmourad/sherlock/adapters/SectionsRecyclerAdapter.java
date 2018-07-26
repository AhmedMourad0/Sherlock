package inc.ahmedmourad.sherlock.adapters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluelinelabs.conductor.Controller;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import inc.ahmedmourad.sherlock.R;
import inc.ahmedmourad.sherlock.adapters.pojo.Section;

public class SectionsRecyclerAdapter extends RecyclerView.Adapter<SectionsRecyclerAdapter.ViewHolder> {

	private final List<Section> sectionsList;
	private final OnSectionSelectedListener listener;

	public SectionsRecyclerAdapter(@NonNull List<Section> sectionsList, @NonNull final  OnSectionSelectedListener listener) {
		this.sectionsList = sectionsList;
		this.listener = listener;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull final ViewGroup container, final int viewType) {
		return new ViewHolder(LayoutInflater.from(container.getContext()).inflate(R.layout.item_section, container, false));
	}

	@Override
	public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
		holder.bind(sectionsList.get(position));
	}

	@Override
	public int getItemCount() {
		return sectionsList.size();
	}

	class ViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.section_name)
		TextView nameTextView;

		@BindView(R.id.section_image)
		ImageView imageView;

		ViewHolder(final View view) {
			super(view);
			ButterKnife.bind(this, view);
		}

		private void bind(@NonNull final Section section) {
			nameTextView.setText(section.getName());
			imageView.setImageResource(section.getImageDrawable());
			itemView.setOnClickListener(v -> listener.onSectionSelectedListener(section.getController()));
		}
	}

	@FunctionalInterface
	public interface OnSectionSelectedListener {
		void onSectionSelectedListener(@Nullable final Controller sectionController);
	}
}