/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import java.net.URISyntaxException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.service.CreateMessageService;
import com.infy.service.model.CreateMessageRequest;
import com.infy.service.model.TopicOrPostResponse;

@RestController
@RequestMapping("/api")
public class CreateMessageResorces {

	private final Logger log = LoggerFactory.getLogger(CreateMessageResorces.class);

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final CreateMessageService createMessageService;

	public CreateMessageResorces(CreateMessageService createMessageService) {
		this.createMessageService = createMessageService;
	}

	@PostMapping("/message")
	public ResponseEntity<TopicOrPostResponse> createMessageResources(@Valid @RequestBody CreateMessageRequest createMessageRequest)
			throws URISyntaxException {
		log.debug("REST request to create message : {}", createMessageRequest);
		TopicOrPostResponse result = createMessageService.createMessage(createMessageRequest);
		return ResponseEntity.ok().body(result);
	}

}
