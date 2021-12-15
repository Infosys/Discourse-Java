/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.infy.service.PublicService;
import com.infy.service.model.AnnouncementResponse;

import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/public")
public class PublicResource {

	private final Logger log = LoggerFactory.getLogger(PublicResource.class);

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final PublicService publicService;

	public PublicResource(PublicService publicService) {
		this.publicService = publicService;
	}

	@GetMapping("/announcements")
	public ResponseEntity<List<AnnouncementResponse>> getAllAnnouncements(Pageable pageable) {
		log.debug("REST request to get a page of announcements");
		Page<AnnouncementResponse> page = publicService.getAllAnnouncement(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	@GetMapping("/announcements/{id}")
	public ResponseEntity<AnnouncementResponse> getAnnouncementsById(@PathVariable Long id) {
		log.debug("REST request to get Announcement : {}", id);
		Optional<AnnouncementResponse> announcementResponse = publicService.getAnnouncementById(id);
		return ResponseUtil.wrapOrNotFound(announcementResponse);
	}

}
