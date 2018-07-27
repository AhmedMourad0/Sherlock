package inc.ahmedmourad.sherlock.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import inc.ahmedmourad.sherlock.model.pojo.SearchResult;
import inc.ahmedmourad.sherlock.model.room.entities.ResultEntity;

public final class ListUtils {

	@NonNull
	public static List<ResultEntity> toResultEntities(@Nullable final List<SearchResult> results) {

		if (results == null)
			return new ArrayList<>(0);

		final List<ResultEntity> list = new ArrayList<>(results.size());

		for (int i = 0; i < results.size(); ++i)
			list.add(ResultEntity.fromSearchResult(results.get(i)));

		return list;
	}

	@NonNull
	public static List<SearchResult> toSearchResults(@Nullable final List<ResultEntity> results) {

		if (results == null || results.isEmpty())
			return new ArrayList<>(0);

		final List<SearchResult> list = new ArrayList<>(results.size());

		for (int i = 0; i < results.size(); ++i)
			list.add(SearchResult.fromResultEntity(results.get(i)));

		return list;
	}
}
