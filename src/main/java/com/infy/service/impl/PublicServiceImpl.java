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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infy.service.AnnouncementService;
import com.infy.service.PublicService;
import com.infy.service.model.AnnouncementResponse;

@Service
@Transactional
public class PublicServiceImpl implements PublicService {

	private final Logger log = LoggerFactory.getLogger(PublicServiceImpl.class);

	private final AnnouncementService announcementService;

	public PublicServiceImpl(AnnouncementService announcementService) {
		this.announcementService = announcementService;
	}

	@Override
	public Page<AnnouncementResponse> getAllAnnouncement(Pageable pageable) {
		log.debug("Get All Announcement");
		return announcementService.findAllActiveAnnouncement(pageable);
	}

	@Override
	public Optional<AnnouncementResponse> getAnnouncementById(Long id) {
		log.debug("Get Announcement By id : {}", id);
		return announcementService.findByIdActiveAnnouncement(id);
	}
}
