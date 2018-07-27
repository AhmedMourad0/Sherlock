package inc.ahmedmourad.sherlock.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;

import java.net.ConnectException;
import java.net.UnknownHostException;

import inc.ahmedmourad.sherlock.R;

public final class ErrorUtils {

	/**
	 * A network error
	 *
	 * @param context   context
	 * @param exception the error exception
	 */
	public static void network(@NonNull final Context context, @Nullable final Exception exception) {

		if (exception == null ||
				exception instanceof ConnectException ||
				exception instanceof UnknownHostException)
			Toast.makeText(context,
					R.string.network_error,
					Toast.LENGTH_LONG
			).show();
		else if (exception.getCause() == null)
			Toast.makeText(context,
					context.getString(R.string.network_error_no_cause,
							exception.getLocalizedMessage()
					), Toast.LENGTH_LONG
			).show();
		else
			Toast.makeText(context,
					context.getString(R.string.network_error_cause,
							exception.getLocalizedMessage(),
							exception.getCause().getLocalizedMessage()
					), Toast.LENGTH_LONG
			).show();

		if (exception != null) {

			exception.printStackTrace();

			if (exception.getCause() != null)
				exception.getCause().printStackTrace();
		}
	}

	/**
	 * A not so serious error
	 *
	 * @param context   context
	 * @param throwable the error throwable
	 */
	public static void general(@NonNull final Context context, @Nullable final Throwable throwable) {

		if (throwable == null)
			Toast.makeText(context,
					context.getString(R.string.error),
					Toast.LENGTH_LONG
			).show();
		else if (throwable.getCause() == null)
			Toast.makeText(context,
					context.getString(R.string.error_no_cause,
							throwable.getLocalizedMessage()
					), Toast.LENGTH_LONG
			).show();
		else
			Toast.makeText(context,
					context.getString(R.string.error_cause,
							throwable.getLocalizedMessage(),
							throwable.getCause().getLocalizedMessage()
					), Toast.LENGTH_LONG
			).show();

		if (throwable != null) {

			throwable.printStackTrace();

			if (throwable.getCause() != null)
				throwable.getCause().printStackTrace();
		}
	}

	/**
	 * A not so serious error
	 *
	 * @param context   context
	 * @param error the database error
	 */
	public static void firebase(@NonNull final Context context, @NonNull final DatabaseError error) {
		Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
		error.toException().printStackTrace();
	}
}
