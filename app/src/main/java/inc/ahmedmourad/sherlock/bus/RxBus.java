package inc.ahmedmourad.sherlock.bus;

import com.jakewharton.rxrelay2.PublishRelay;

public final class RxBus {

	public static final int STATE_PUBLISHING_FAILURE = -1;
	public static final int STATE_PUBLISHING_ONGOING = 0;
	public static final int STATE_PUBLISHING_SUCCESS = 1;

	private static final RxBus INSTANCE = new RxBus();

	private final PublishRelay<Integer> publishingStateRelay = PublishRelay.create();

	// To prevent instantiation outside the class
	private RxBus() {

	}

	public static RxBus getInstance() {
		return INSTANCE;
	}

	public void setPublishingState(final int state) {
		publishingStateRelay.accept(state);
	}

	public PublishRelay<Integer> getPublishingStateRelay() {
		return publishingStateRelay;
	}
}
