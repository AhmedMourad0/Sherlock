package inc.ahmedmourad.sherlock.model.contract;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import inc.ahmedmourad.sherlock.R;
import inc.ahmedmourad.sherlock.interfaces.Gender;
import inc.ahmedmourad.sherlock.interfaces.Hair;
import inc.ahmedmourad.sherlock.interfaces.Skin;

public final class FirebaseContract {

	public static final class Database {

		public static final String PATH_CHILDREN = "c";

		public static final String CHILDREN_FIRST_NAME = "f";
		public static final String CHILDREN_LAST_NAME = "l";
		public static final String CHILDREN_LOCATION = "c";
		public static final String CHILDREN_START_AGE = "a";
		public static final String CHILDREN_END_AGE = "b";
		public static final String CHILDREN_START_HEIGHT = "h";
		public static final String CHILDREN_END_HEIGHT = "i";
		public static final String CHILDREN_GENDER = "g";
		public static final String CHILDREN_SKIN = "s";
		public static final String CHILDREN_HAIR = "r";
		public static final String CHILDREN_PICTURE_URL = "p";
		public static final String CHILDREN_NOTES = "n";

		@NonNull
		public static String getSkin(@NonNull final Context context, final int skin) {

			switch (skin) {

				case Skin.SKIN_WHITE:
					return context.getString(R.string.white_skin);

				case Skin.SKIN_WHEAT:
					return context.getString(R.string.wheatish_skin);

				case Skin.SKIN_DARK:
					return context.getString(R.string.dark_skin);

				default:
					return context.getString(R.string.not_available);
			}
		}

		@NonNull
		public static String getHair(@NonNull final Context context, final int hair) {

			switch (hair) {

				case Hair.HAIR_BLONDE:
					return context.getString(R.string.blonde_hair);

				case Hair.HAIR_BROWN:
					return context.getString(R.string.brown_hair);

				case Hair.HAIR_DARK:
					return context.getString(R.string.dark_hair);

				default:
					return context.getString(R.string.not_available);
			}
		}

		@NonNull
		public static String getGender(@NonNull final Context context, final int gender) {

			switch (gender) {

				case Gender.GENDER_MALE:
					return context.getString(R.string.male);

				case Gender.GENDER_FEMALE:
					return context.getString(R.string.female);

				default:
					return context.getString(R.string.not_available);
			}
		}

		@NonNull
		public static String getFullName(@NonNull final Context context, @NonNull final String firstName, @NonNull final String lastName) {

			final boolean isFirstNameEmpty = TextUtils.isEmpty(firstName);
			final boolean isLastNameEmpty = TextUtils.isEmpty(lastName);

			if (isFirstNameEmpty && isLastNameEmpty)
				return context.getString(R.string.not_available);

			String fullName = "";

			if (!isFirstNameEmpty)
				fullName += firstName;

			if (!isFirstNameEmpty && !isLastNameEmpty)
				fullName += " ";

			if (!isLastNameEmpty)
				fullName += lastName;

			return fullName;
		}

		@NonNull
		public static String getAge(@NonNull final Context context, final int startAge, final int endAge) {
			return context.getString(R.string.years_range, startAge + " - " + endAge);
		}

		@NonNull
		public static String getLocation(@NonNull final Context context, @NonNull final String location) {

			final String[] details = location.split("\\|\\|");

			if (details.length < 3)
				return context.getString(R.string.not_available);

			final boolean isNameEmpty = TextUtils.isEmpty(details[1]);
			final boolean isAddressEmpty = TextUtils.isEmpty(details[2]);

			if (isNameEmpty && isAddressEmpty)
				return context.getString(R.string.not_available);

			if (!isNameEmpty && !isAddressEmpty) {

				return context.getString(R.string.location, details[1], details[2]);

			} else {

				if (!isNameEmpty)
					return details[1];
				else
					return details[2];
			}
		}

		@NonNull
		public static String getHeight(@NonNull final Context context, final int startHeight, final int endHeight) {
			return context.getString(R.string.height_range,
					formatHeight(context, startHeight),
					formatHeight(context, endHeight)
			);
		}

		@NonNull
		private static String formatHeight(@NonNull final Context context, final int height) {

			String result = "";

			final int meters = height / 100;
			final int centimeters = height % 100;

			if (meters > 0)
				result += context.getResources().getQuantityString(R.plurals.height_meters, meters, meters);

			if (meters > 0 && centimeters > 0)
				result += " ";

			if (centimeters > 0)
				result += context.getResources().getQuantityString(R.plurals.height_centimeters, centimeters, centimeters);

			return TextUtils.isEmpty(result) ? context.getString(R.string.not_available) : result;
		}
	}

	public static final class Storage {

		public static final String PATH_CHILDREN = "c";

		public static final String FILE_FORMAT = ".jpeg";

		@NonNull
		public static byte[] getImageBytes(@NonNull final ImageView imageView) {

			imageView.setDrawingCacheEnabled(true);
			imageView.buildDrawingCache();

			final Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
			final ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

			return stream.toByteArray();
		}

		@Nullable
		public static Bitmap getImageBitmap(@Nullable final byte[] imageBytes) {
			return imageBytes == null ? null : BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
		}
	}
}
