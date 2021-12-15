/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;

import org.mapstruct.Mapper;

import com.infy.domain.Announcement;
import com.infy.service.model.AnnouncementResponse;

@Mapper(componentModel = "spring", uses = {})
public interface AnnouncementResponseMapper extends EntityMapper<AnnouncementResponse, Announcement> {

	default Announcement fromId(Long id) {
		if (id == null) {
			return null;
		}
		Announcement announcement = new Announcement();
		announcement.setId(id);
		return announcement;
	}
}
