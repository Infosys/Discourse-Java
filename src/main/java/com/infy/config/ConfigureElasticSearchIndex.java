/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.config;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetMappingsRequest;
import org.elasticsearch.client.indices.GetMappingsResponse;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

@Configuration
public class ConfigureElasticSearchIndex {

	private final Logger log = LoggerFactory.getLogger(ConfigureElasticSearchIndex.class);

	@Autowired
	private RestHighLevelClient restHighLevelClient;

	@Value("${forums.elasticsearch.index-path}")
	private String filePath;

	@Value("${forums.elasticsearch.delete-index}")
	private boolean deleteIndex;

	@Value("${forums.elasticsearch.index}")
	private String indexName;

	public void configureIndex() {
		try {
			Resource resource = new ClassPathResource(filePath);
			String mapping = new String(resource.getInputStream().readAllBytes());

			if (createIndex(indexName, mapping, deleteIndex)) {
				log.info("Index is Created Successfully");
				if (createMapping(indexName, mapping)) {
					log.info("Mapping Created Successfully");
				} else {
					log.error("Mapping Creation is failed");
				}
			} else {
				log.error("Index creation is failed");
			}
		} catch (JsonIOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public boolean createIndex(String index, String mapping, boolean deleteIfPresent) throws IOException {
		if (isIndexExist(index)) {
			if (deleteIfPresent)
				deleteIndex(index);
			else
				return true;
		}
		CreateIndexRequest request = new CreateIndexRequest(index);
		request.source(mapping, XContentType.JSON);
		CreateIndexResponse rs = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);

		if (rs.isAcknowledged()) {
			return true;
		}
		return false;
	}

	public boolean isIndexExist(String index) throws IOException {
		GetIndexRequest indexRequest = new GetIndexRequest(index);
		return restHighLevelClient.indices().exists(indexRequest, RequestOptions.DEFAULT);
	}

	boolean deleteIndex(String index) throws IOException {
		DeleteIndexRequest request = new DeleteIndexRequest(index);
		try {
			AcknowledgedResponse deleteIndexResponse = restHighLevelClient.indices().delete(request,
					RequestOptions.DEFAULT);
			if (deleteIndexResponse.isAcknowledged()) {
				return isIndexExist(index);
			}
		} catch (ElasticsearchException exception) {
			if (exception.status() == RestStatus.NOT_FOUND) {
				return false;
			}
		}
		return false;
	}

	public boolean createMapping(String indexName, String mapping) {
		PutMappingRequest request = new PutMappingRequest(indexName);
		request = request.source(mapping, XContentType.JSON);
		try {
			AcknowledgedResponse putMappingResponse = restHighLevelClient.indices().putMapping(request,
					RequestOptions.DEFAULT);
			if (putMappingResponse.isAcknowledged()) {
				GetMappingsRequest getMappingRequest = new GetMappingsRequest();
				getMappingRequest.indices(indexName);
				GetMappingsResponse getMappingResponse = restHighLevelClient.indices().getMapping(getMappingRequest,
						RequestOptions.DEFAULT);
				if (getMappingResponse.mappings() != null) {
					return true;
				}
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return false;
	}

}
