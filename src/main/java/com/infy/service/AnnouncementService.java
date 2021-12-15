/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.infy.service.dto.AnnouncementDTO;
import com.infy.service.model.AnnouncementRequest;
import com.infy.service.model.AnnouncementResponse;

/**
 * Service Interface for managing {@link com.infy.domain.Announcement}.
 */
public interface AnnouncementService {

	/**
	 * Save a announcment.
	 *
	 * @param announcementDTO the entity to save.
	 * @return the persisted entity.
	 */
	AnnouncementDTO save(AnnouncementDTO announcementDTO);

	/**
	 * Get all the announcments.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<AnnouncementDTO> findAll(Pageable pageable);

	/**
	 * Get the "id" announcment.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<AnnouncementDTO> findOne(Long id);

	/**
	 * Delete the "id" announcment.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);

	AnnouncementDTO createAnnouncement(AnnouncementRequest announcementRequest);

	AnnouncementDTO updateAnnouncement(Long id, AnnouncementRequest announcementRequest);

	Page<AnnouncementDTO> findAllAdmin(Pageable pageable);

	void deleteAnnouncement(Long id);

	Page<AnnouncementResponse> findAllActiveAnnouncement(Pageable pageable);

	Optional<AnnouncementResponse> findByIdActiveAnnouncement(Long id);
}
