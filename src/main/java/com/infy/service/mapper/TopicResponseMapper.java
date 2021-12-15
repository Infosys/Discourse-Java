/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.infy.domain.Topics;
import com.infy.service.model.TopicOrPostResponse;

@Mapper(componentModel = "spring", uses = {})
public interface TopicResponseMapper extends EntityMapper<TopicOrPostResponse, Topics> {

	@Mapping(source = "title", target = "name")
	@Mapping(source = "createdDate", target = "createdAt")
	@Mapping(source = "lastModifiedDate", target = "updatedAt")
	@Mapping(source = "postsCount",target = "postNumber")
	@Mapping(source = "visible",target = "hidden")
	@Mapping(source = "subtype",target = "tags")
	TopicOrPostResponse toDto(Topics topic);

	default Topics fromId(Long id) {
		if (id == null) {
			return null;
		}
		Topics topics = new Topics();
		topics.setId(id);
		return topics;
	}
}
