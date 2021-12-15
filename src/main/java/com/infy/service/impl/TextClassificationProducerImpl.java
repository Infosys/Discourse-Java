/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.infy.avroschema.TextClassificationRequest;
import com.infy.service.TextClassificationProducer;

@Service
public class TextClassificationProducerImpl implements TextClassificationProducer {

	private final Logger log = LoggerFactory.getLogger(TextClassificationProducerImpl.class);

	@Value("${forums.classification.producer.topic-name}")
	private String textClassificationTopic;

	private final KafkaTemplate<String, TextClassificationRequest> kafkaTemplateTextClassification;

	public TextClassificationProducerImpl(
			KafkaTemplate<String, TextClassificationRequest> kafkaTemplateTextClassification) {
		this.kafkaTemplateTextClassification = kafkaTemplateTextClassification;
	}

	@Override
	public void produceTextClassification(TextClassificationRequest textClassificationRequest) {
		log.info("Request to produce Text Classification : {} ", textClassificationRequest);
		kafkaTemplateTextClassification.send(textClassificationTopic, textClassificationRequest);
	}
}
