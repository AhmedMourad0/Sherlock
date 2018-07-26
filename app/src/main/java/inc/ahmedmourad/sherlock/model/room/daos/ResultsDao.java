package inc.ahmedmourad.sherlock.model.room.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.List;

import inc.ahmedmourad.sherlock.model.room.entities.ResultEntity;
import io.reactivex.Flowable;

@Dao
public abstract class ResultsDao {

	@Query("SELECT " +
			ResultEntity.COLUMN_ID + ", " +
			ResultEntity.COLUMN_DATE + ", " +
			ResultEntity.COLUMN_FIRST_NAME + ", " +
			ResultEntity.COLUMN_LAST_NAME + ", " +
			ResultEntity.COLUMN_PICTURE_URL + ", " +
			ResultEntity.COLUMN_LOCATION + ", " +
			ResultEntity.COLUMN_NOTES + ", " +
			ResultEntity.COLUMN_GENDER + ", " +
			ResultEntity.COLUMN_SKIN + ", " +
			ResultEntity.COLUMN_HAIR + ", " +
			ResultEntity.COLUMN_START_AGE + ", " +
			ResultEntity.COLUMN_END_AGE + ", " +
			ResultEntity.COLUMN_START_HEIGHT + ", " +
			ResultEntity.COLUMN_END_HEIGHT +
			" FROM " +
			ResultEntity.TABLE_NAME)
	public abstract LiveData<List<ResultEntity>> getResults();

	@Query("SELECT " +
			ResultEntity.COLUMN_ID + ", " +
			ResultEntity.COLUMN_DATE + ", " +
			ResultEntity.COLUMN_FIRST_NAME + ", " +
			ResultEntity.COLUMN_LAST_NAME + ", " +
			ResultEntity.COLUMN_PICTURE_URL + ", " +
			ResultEntity.COLUMN_LOCATION + ", " +
			ResultEntity.COLUMN_NOTES + ", " +
			ResultEntity.COLUMN_GENDER + ", " +
			ResultEntity.COLUMN_SKIN + ", " +
			ResultEntity.COLUMN_HAIR + ", " +
			ResultEntity.COLUMN_START_AGE + ", " +
			ResultEntity.COLUMN_END_AGE + ", " +
			ResultEntity.COLUMN_START_HEIGHT + ", " +
			ResultEntity.COLUMN_END_HEIGHT +
			" FROM " +
			ResultEntity.TABLE_NAME)
	public abstract Flowable<List<ResultEntity>> getResultsFlowable();

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	abstract void bulkInsert(final List<ResultEntity> resultsEntities);

	@Query("DELETE FROM " + ResultEntity.TABLE_NAME)
	abstract void deleteAll();

	@Transaction
	public void replaceResults(final List<ResultEntity> resultsEntities) {
		deleteAll();
		bulkInsert(resultsEntities);
	}
}
