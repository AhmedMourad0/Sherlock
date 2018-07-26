package inc.ahmedmourad.sherlock.utils;

import android.content.res.Resources;

public final class DimensUtils {

	public static int dp(final float dp) {
		return Math.round(dp * Resources.getSystem().getDisplayMetrics().density);
	}
}
