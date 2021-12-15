/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infy.service.ElasticSearchService;
import com.infy.service.model.TopicElasticSearchDocument;

@RestController
@RequestMapping("/api")
public class TopicElasticSearchResource {

	private final Logger log = LoggerFactory.getLogger(TopicElasticSearchResource.class);

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final ElasticSearchService elasticSearchService;

	public TopicElasticSearchResource(ElasticSearchService elasticSearchService) {
		this.elasticSearchService = elasticSearchService;
	}

	@GetMapping("/search-topics-title/{title}")
	public ResponseEntity<List<TopicElasticSearchDocument>> getTopicsByElasticSearchTitle(@PathVariable String title,
			@RequestParam(defaultValue = "0", required = false) Integer start,
			@RequestParam(defaultValue = "20", required = false) Integer size) {
		log.debug("REST request to get Topics : {}", title);
		List<TopicElasticSearchDocument> result = elasticSearchService.searchByTitle(title, start, size);
		return ResponseEntity.ok(result);
	}

	@GetMapping("/search-topics-tags/{tags}")
	public ResponseEntity<List<TopicElasticSearchDocument>> getTopicByElasticSearchTitle(@PathVariable String tags,
			@RequestParam(defaultValue = "0", required = false) Integer start,
			@RequestParam(defaultValue = "20", required = false) Integer size) {
		log.debug("REST request to get Topics : {}", tags);
		List<TopicElasticSearchDocument> result = elasticSearchService.searchByTags(tags, start, size);
		return ResponseEntity.ok(result);
	}
}
