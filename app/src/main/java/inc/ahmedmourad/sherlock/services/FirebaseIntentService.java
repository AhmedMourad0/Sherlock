package inc.ahmedmourad.sherlock.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.parceler.Parcels;

import inc.ahmedmourad.sherlock.bus.RxBus;
import inc.ahmedmourad.sherlock.model.contract.FirebaseContract;
import inc.ahmedmourad.sherlock.model.pojo.Child;
import inc.ahmedmourad.sherlock.utils.ErrorUtils;

public class FirebaseIntentService extends IntentService {

	private static final String ACTION_PUBLISH_FOUND = "pf";

	private static final String EXTRA_FOUND = "f";
	private static final String EXTRA_IMAGE_BYTES = "ib";

	public FirebaseIntentService() {
		super("FirebaseIntentService");
	}

	public static void startActionPublishFound(Context context, Child child, byte[] imageBytes) {
		Intent intent = new Intent(context, FirebaseIntentService.class);
		intent.setAction(ACTION_PUBLISH_FOUND);
		intent.putExtra(EXTRA_FOUND, Parcels.wrap(child));
		intent.putExtra(EXTRA_IMAGE_BYTES, imageBytes);
		context.startService(intent);
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		if (intent == null || intent.getAction() == null) {
			ErrorUtils.general(getApplicationContext(), null);
			RxBus.getInstance().setPublishingState(RxBus.STATE_PUBLISHING_FAILURE);
			return;
		}

		switch (intent.getAction()) {

			case ACTION_PUBLISH_FOUND:
				handleActionPublishFound(Parcels.unwrap(intent.getParcelableExtra(EXTRA_FOUND)),
						intent.getByteArrayExtra(EXTRA_IMAGE_BYTES)
				);
				break;
		}
	}

	private void handleActionPublishFound(Child child, byte[] imageBytes) {

		final String time = Long.toString(System.currentTimeMillis());

		final StorageReference filePath =
				FirebaseStorage.getInstance()
						.getReference(FirebaseContract.Storage.PATH_CHILDREN)
						.child(time + FirebaseContract.Storage.FILE_FORMAT);

		filePath.putBytes(imageBytes)
				.addOnCompleteListener(task -> {

					if (!task.isSuccessful()) {
						ErrorUtils.network(getApplicationContext(), task.getException());
						RxBus.getInstance().setPublishingState(RxBus.STATE_PUBLISHING_FAILURE);
						return;
					}

					filePath.getDownloadUrl().addOnCompleteListener(task1 -> {

						if (!task1.isSuccessful()) {
							ErrorUtils.network(getApplicationContext(), task1.getException());
							RxBus.getInstance().setPublishingState(RxBus.STATE_PUBLISHING_FAILURE);
							return;
						}

						final String downloadUri = task1.getResult().toString();

						child.setPictureUrl(downloadUri);

						FirebaseDatabase.getInstance()
								.getReference(FirebaseContract.Database.PATH_CHILDREN)
								.child(time)
								.updateChildren(child.toMap())
								.addOnCompleteListener(task2 -> {

									if (!task2.isSuccessful()) {
										ErrorUtils.network(getApplicationContext(), task2.getException());
										RxBus.getInstance().setPublishingState(RxBus.STATE_PUBLISHING_FAILURE);
										return;
									}

									RxBus.getInstance().setPublishingState(RxBus.STATE_PUBLISHING_SUCCESS);
								});
					});
				});
	}
}
