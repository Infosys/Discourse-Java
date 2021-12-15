/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.infy.avroschema.TextClassificationResponse;
import com.infy.domain.enumeration.TextClassificationType;
import com.infy.service.PostsService;
import com.infy.service.TextClassificationConsumer;
import com.infy.service.TextClassificationService;
import com.infy.service.TopicsService;
import com.infy.service.dto.TextClassificationDTO;

@Service
public class TextClassificationConsumerImpl implements TextClassificationConsumer {

	private final Logger log = LoggerFactory.getLogger(TextClassificationConsumerImpl.class);

	private final TextClassificationService textClassificationService;

	private final PostsService postsService;

	private final TopicsService topicsService;

	public TextClassificationConsumerImpl(TextClassificationService textClassificationService,
			PostsService postsService, TopicsService topicsService) {
		this.textClassificationService = textClassificationService;
		this.postsService = postsService;
		this.topicsService = topicsService;
	}

	@Override
	@KafkaListener(topics = "${forums.classification.consumer.topic-name}")
	public void consumeTextClassification(TextClassificationResponse textClassificationResponse) {
		log.debug("Consuming Text Classification Response : {} ", textClassificationResponse);

		if (textClassificationResponse.getType() != null) {
			TextClassificationDTO textClassificationDTO = new TextClassificationDTO();

			if (textClassificationResponse.getType().equals(TextClassificationType.POST.toString())) {

				Optional<TextClassificationDTO> textOptional = textClassificationService
						.findByPostId(textClassificationResponse.getId());
				if (textOptional.isPresent()) {
					textClassificationDTO = textOptional.get();
				} else {
					textClassificationDTO.setType(TextClassificationType.POST);
					textClassificationDTO.setContentId(textClassificationResponse.getId());
				}
			} else {
				Optional<TextClassificationDTO> textOptional = textClassificationService
						.findByTopicId(textClassificationResponse.getId());
				if (textOptional.isPresent()) {
					textClassificationDTO = textOptional.get();
				} else {
					textClassificationDTO.setType(TextClassificationType.TOPIC);
					textClassificationDTO.setContentId(textClassificationResponse.getId());
				}
			}

			textClassificationDTO.setToxicity(textClassificationResponse.getToxicity());
			textClassificationDTO.setThreat(textClassificationResponse.getThreat());
			textClassificationDTO.setIdentityHate(textClassificationResponse.getIdentityHate());
			textClassificationDTO.setSevereToxicity(textClassificationResponse.getSevereToxicity());
			textClassificationDTO.setObscene(textClassificationResponse.getObscene());
			textClassificationDTO.setInsult(textClassificationResponse.getInsult());

			textClassificationDTO = textClassificationService.save(textClassificationDTO);

			if (textClassificationDTO.getType().equals(TextClassificationType.POST)) {
				if (textClassificationDTO.getToxicity() > 0.8) {
					postsService.setPostsAsHidden(textClassificationDTO.getContentId());
				}
			} else {
				if (textClassificationDTO.getToxicity() > 0.8) {
					topicsService.hideTopicUsingClassifier(textClassificationDTO.getContentId());
				}
			}
		}

	}
}
