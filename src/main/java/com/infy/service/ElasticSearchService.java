/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import java.util.List;

import com.infy.service.model.TopicElasticSearchDocument;

public interface ElasticSearchService {

	void addTopicElasticSearchDocument(TopicElasticSearchDocument topicElasticSearchDocument);

	void addObjectInIndex(String indexName, String object);

	List<TopicElasticSearchDocument> searchByTags(String tags,int start,int size);

	List<TopicElasticSearchDocument> searchByTitle(String title,int start,int size);

}
