package inc.ahmedmourad.sherlock.repository;

import android.support.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import inc.ahmedmourad.sherlock.model.contract.FirebaseContract;

public class SearchResultsRepository {

	private final DatabaseReference childrenReference;

	public SearchResultsRepository() {
		childrenReference = FirebaseDatabase.getInstance().getReference(FirebaseContract.Database.PATH_CHILDREN);
	}

	public void filterBySkin(final int skin, @NonNull final ValueEventListener listener) {
		childrenReference.orderByChild(FirebaseContract.Database.CHILDREN_SKIN)
				.equalTo(skin)
				.addValueEventListener(listener);
	}
}
