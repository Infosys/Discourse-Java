/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.infy.service.ElasticSearchService;
import com.infy.service.model.TopicElasticSearchDocument;
import com.infy.utils.CommonUtils;
import com.infy.utils.QueryUtils;
import com.infy.web.rest.errors.BadRequestAlertException;

@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {

	private final Logger log = LoggerFactory.getLogger(ElasticSearchServiceImpl.class);

	private final Gson gson;

	private final RestHighLevelClient restHighLevelClient;

	@Value("${forums.elasticsearch.index}")
	private String topicIndex;

	public ElasticSearchServiceImpl(Gson gson, RestHighLevelClient restHighLevelClient) {
		this.gson = gson;
		this.restHighLevelClient = restHighLevelClient;
	}

	@Override
	public void addTopicElasticSearchDocument(TopicElasticSearchDocument topicElasticSearchDocument) {
		log.debug("Request to add TopicDocument in elastic search : {}", topicElasticSearchDocument);
		addObjectInIndex(topicIndex, gson.toJson(topicElasticSearchDocument));
	}

	@Override
	public void addObjectInIndex(String indexName, String object) {
		log.debug("Request to add object in index : {}", object);

		JsonObject jsonObj = JsonParser.parseString(object).getAsJsonObject();
		String id = jsonObj.get("id").getAsString();

		if (CommonUtils.isEmpty(id)) {
			throw new BadRequestAlertException("Id is null", "ElasticSearchDocument", "Id is null");
		}

		UpdateRequest request = new UpdateRequest(indexName, id);
		request.doc(gson.toJson(jsonObj), XContentType.JSON);
		request.upsert(gson.toJson(jsonObj), XContentType.JSON);

		try {
			restHighLevelClient.update(request, RequestOptions.DEFAULT);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public List<TopicElasticSearchDocument> searchByTags(String tags, int start, int size) {
		log.debug("Request to search by tags : {} ", tags);

		List<QueryBuilder> queryBuilders = new ArrayList<>();
		queryBuilders.add(QueryUtils.createTermQueryBuilder("visible", "true"));
		queryBuilders.add(QueryUtils.createMatchQueryBuilder("tags", tags, 100.0f));

		QueryBuilder queryBuilder = QueryUtils.createBooleanMustQuery(queryBuilders);

		SearchRequest searchRequest = new SearchRequest(topicIndex);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(queryBuilder).from(start).size(size);
		searchRequest.source(searchSourceBuilder);

		try {
			List<TopicElasticSearchDocument> topicElasticSearchDocuments = new ArrayList<>();

			SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
			SearchHit[] result = response.getHits().getHits();
			for (SearchHit searchHit : result) {
				TopicElasticSearchDocument topicElasticSearchDocument = gson.fromJson(searchHit.getSourceAsString(),
						TopicElasticSearchDocument.class);
				topicElasticSearchDocuments.add(topicElasticSearchDocument);
			}

			return topicElasticSearchDocuments;
		} catch (IOException e) {
			throw new BadRequestAlertException(e.getMessage(), "TopicElasticSearchDocument", e.getMessage());
		}
	}

	@Override
	public List<TopicElasticSearchDocument> searchByTitle(String title, int start, int size) {
		log.debug("Request to search by title : {} ", title);

		List<QueryBuilder> queryBuilders = new ArrayList<>();
		queryBuilders.add(QueryUtils.createMatchQueryBuilder("title", title, 80.0f));
		queryBuilders.add(QueryUtils.createMatchQueryBuilder("tags", title, 30.0f));
		queryBuilders.add(QueryUtils.createMatchQueryBuilder("raw", title, 10.0f));

		QueryBuilder disMaxQueryBuilder = QueryUtils.createDisMaxQuery(queryBuilders);

		List<QueryBuilder> booleanQueryBuilders = new ArrayList<>();
		booleanQueryBuilders.add(disMaxQueryBuilder);
		booleanQueryBuilders.add(QueryUtils.createTermQueryBuilder("visible", "true"));

		QueryBuilder queryBuilder = QueryUtils.createBooleanMustQuery(booleanQueryBuilders);

		SearchRequest searchRequest = new SearchRequest(topicIndex);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(queryBuilder).from(start).size(size);
		searchRequest.source(searchSourceBuilder);

		try {
			List<TopicElasticSearchDocument> topicElasticSearchDocuments = new ArrayList<>();

			SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
			SearchHit[] result = response.getHits().getHits();
			for (SearchHit searchHit : result) {
				TopicElasticSearchDocument topicElasticSearchDocument = gson.fromJson(searchHit.getSourceAsString(),
						TopicElasticSearchDocument.class);
				topicElasticSearchDocuments.add(topicElasticSearchDocument);
			}

			return topicElasticSearchDocuments;
		} catch (IOException e) {
			throw new BadRequestAlertException(e.getMessage(), "TopicElasticSearchDocument", e.getMessage());
		}
	}
}
