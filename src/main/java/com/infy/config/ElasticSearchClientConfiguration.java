/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.config;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.infy.domain.enumeration.TypeOfAuth;

@Configuration
@EnableConfigurationProperties(ElasticsearchProperties.class)
public class ElasticSearchClientConfiguration extends AbstractFactoryBean<RestHighLevelClient> {

	private static final Logger log = LoggerFactory.getLogger(ElasticSearchClientConfiguration.class);

	@Value("${forums.elastic-search.port}")
	private int elasticPort;

	@Value("${forums.elastic-search.host}")
	private String hostname;

	@Value("${forums.elastic-search.username}")
	private String elasticUsername;

	@Value("${forums.elastic-search.password}")
	private String elasticPassword;

	@Value("${forums.elastic-search.token}")
	private String elasticToken;

	@Value("${forums.elastic-search.api-key}")
	private String elasticApiKeyId;

	@Value("${forums.elastic-search.api-secret}")
	private String elasticApiKeySecret;

	private RestHighLevelClient restHighLevelClient;

	@Override
	public void destroy() {
		try {
			if (restHighLevelClient != null) {
				restHighLevelClient.close();
			}
		} catch (final Exception e) {
			log.error("Error closing ElasticSearch client: ", e);
		}
	}

	@Override
	public Class<RestHighLevelClient> getObjectType() {
		return RestHighLevelClient.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	@Override
	public RestHighLevelClient createInstance() {
		boolean isSecured = false;
		TypeOfAuth authType = TypeOfAuth.BASIC;
		return buildClient(isSecured, authType);
	}

	private RestHighLevelClient buildClient(boolean isSecured, TypeOfAuth authType) {
		log.info("Building elastic client");

		RestClientBuilder builder = null;

		if (isSecured == false) {
			builder = RestClient.builder(new HttpHost(hostname, elasticPort, "http"));
		} else {
			if (authType.equals(TypeOfAuth.BASIC)) {
				final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
				credentialsProvider.setCredentials(AuthScope.ANY,
						new UsernamePasswordCredentials(elasticUsername, elasticPassword));

				builder = RestClient.builder(new HttpHost(hostname, elasticPort))
						.setHttpClientConfigCallback(new HttpClientConfigCallback() {
							@Override
							public HttpAsyncClientBuilder customizeHttpClient(
									HttpAsyncClientBuilder httpClientBuilder) {
								return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
							}
						});
			} else if (authType.equals(TypeOfAuth.TOKEN)) {
				builder = RestClient.builder(new HttpHost(hostname, elasticPort, "http"));
				Header[] defaultHeaders = new Header[] { new BasicHeader("Authorization", "Bearer " + elasticToken) };
				builder.setDefaultHeaders(defaultHeaders);
			} else if (authType.equals(TypeOfAuth.API)) {
				String apiKeyAuth = Base64.getEncoder()
						.encodeToString((elasticApiKeyId + ":" + elasticApiKeySecret).getBytes(StandardCharsets.UTF_8));

				builder = RestClient.builder(new HttpHost(hostname, elasticPort, "http"));
				Header[] defaultHeaders = new Header[] { new BasicHeader("Authorization", "ApiKey " + apiKeyAuth) };
				builder.setDefaultHeaders(defaultHeaders);
			}
		}

		try {
			log.info("Building rest high level java client: {} {}", hostname, elasticPort);
			restHighLevelClient = new RestHighLevelClient(builder);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return restHighLevelClient;
	}
}
