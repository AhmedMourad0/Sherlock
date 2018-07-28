package inc.ahmedmourad.sherlock.utils;

import android.util.SparseArray;

import io.reactivex.disposables.Disposable;

public class DisposablesSparseArray extends SparseArray<Disposable> {

	public void dispose() {
		for (int i = 0; i < size(); ++i)
			valueAt(i).dispose();
		clear();
	}

	public void dispose(int[] keys) {
		for (int key : keys) {
			dispose(key);
			remove(key);
		}
	}

	@Override
	public void put(int key, Disposable value) {
		dispose(key);
		super.put(key, value);
	}

	private void dispose(int key) {
		final Disposable disposable = get(key);
		if (disposable != null)
			disposable.dispose();
	}
}
