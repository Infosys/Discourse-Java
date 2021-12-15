/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.utils;

import java.util.List;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.DisMaxQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

public class QueryUtils {

	public static QueryBuilder createMatchQueryBuilder(String fieldName, String value) {
		return QueryBuilders.matchQuery(fieldName, value);
	}

	public static QueryBuilder createTermQueryBuilder(String fieldName, String value) {
		return QueryBuilders.termQuery(fieldName, value);
	}

	public static QueryBuilder createMatchQueryBuilder(String fieldName, String value, float weightage) {
		return QueryBuilders.matchQuery(fieldName, value).boost(weightage);
	}

	public static QueryBuilder mergeQueries(List<QueryBuilder> queries) {
		return createDisMaxQuery(queries);
	}

	public static QueryBuilder createDisMaxQuery(List<QueryBuilder> queries) {
		DisMaxQueryBuilder query = QueryBuilders.disMaxQuery();
		for (QueryBuilder q : queries) {
			query.add(q);
		}
		return query;
	}

	public static QueryBuilder createBooleanMustQuery(List<QueryBuilder> queries) {
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		for (QueryBuilder q : queries) {
			query.must(q);
		}
		return query;
	}
}
