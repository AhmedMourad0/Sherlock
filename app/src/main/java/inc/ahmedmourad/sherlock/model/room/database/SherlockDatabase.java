package inc.ahmedmourad.sherlock.model.room.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import inc.ahmedmourad.sherlock.model.room.daos.ResultsDao;
import inc.ahmedmourad.sherlock.model.room.entities.ResultEntity;

@Database(entities = {ResultEntity.class}, version = 1)
public abstract class SherlockDatabase extends RoomDatabase {

	private static final String DATABASE_NAME = "SherlockDatabase";

	private static volatile SherlockDatabase INSTANCE = null;

	@NonNull
	public static SherlockDatabase getInstance(final Context context) {

		if (INSTANCE != null) {
			return INSTANCE;
		} else {
			synchronized (SherlockDatabase.class) {
				return INSTANCE != null ? INSTANCE : (INSTANCE = buildDatabase(context));
			}
		}
	}

	@NonNull
	private static SherlockDatabase buildDatabase(final Context context) {
		return Room.databaseBuilder(
				context.getApplicationContext(),
				SherlockDatabase.class,
				SherlockDatabase.DATABASE_NAME).build();
	}

	public abstract ResultsDao resultsDao();
}
