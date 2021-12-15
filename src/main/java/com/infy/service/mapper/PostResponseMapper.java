/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.infy.domain.Posts;
import com.infy.service.model.TopicOrPostResponse;

@Mapper(componentModel = "spring", uses = {})
public interface PostResponseMapper extends EntityMapper<TopicOrPostResponse, Posts> {

	@Mapping(source = "createdDate", target = "createdAt")
	@Mapping(source = "lastModifiedDate", target = "updatedAt")
	@Mapping(source = "raw",target = "cooked")
	TopicOrPostResponse toDto(Posts post);

	default Posts fromId(Long id) {
		if (id == null) {
			return null;
		}
		Posts posts = new Posts();
		posts.setId(id);
		return posts;
	}
}
