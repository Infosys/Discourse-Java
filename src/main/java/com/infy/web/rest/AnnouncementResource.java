/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.infy.service.AnnouncementService;
import com.infy.service.dto.AnnouncementDTO;
import com.infy.service.model.AnnouncementRequest;
import com.infy.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.infy.domain.Announcement}.
 */
@RestController
@RequestMapping("/api")
public class AnnouncementResource {

	private final Logger log = LoggerFactory.getLogger(AnnouncementResource.class);

	private static final String ENTITY_NAME = "announcement";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final AnnouncementService announcementService;

	public AnnouncementResource(AnnouncementService announcementService) {
		this.announcementService = announcementService;
	}

//	/**
//	 * {@code POST  /announcements} : Create a new announcement.
//	 *
//	 * @param announcementDTO the announcementDTO to create.
//	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
//	 *         body the new announcementDTO, or with status {@code 400 (Bad Request)}
//	 *         if the announcement has already an ID.
//	 * @throws URISyntaxException if the Location URI syntax is incorrect.
//	 */
//	@PostMapping("/announcements")
//	public ResponseEntity<AnnouncementDTO> createAnnouncement(@RequestBody AnnouncementDTO announcementDTO)
//			throws URISyntaxException {
//		log.debug("REST request to save Announcement : {}", announcementDTO);
//		if (announcementDTO.getId() != null) {
//			throw new BadRequestAlertException("A new announcement cannot already have an ID", ENTITY_NAME, "idexists");
//		}
//		AnnouncementDTO result = announcementService.save(announcementDTO);
//		return ResponseEntity
//				.created(new URI("/api/announcements/" + result.getId())).headers(HeaderUtil
//						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
//				.body(result);
//	}
//
//	/**
//	 * {@code PUT  /announcements} : Updates an existing announcement.
//	 *
//	 * @param announcementDTO the announcementDTO to update.
//	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
//	 *         the updated announcementDTO, or with status {@code 400 (Bad Request)}
//	 *         if the announcementDTO is not valid, or with status
//	 *         {@code 500 (Internal Server Error)} if the announcementDTO couldn't be
//	 *         updated.
//	 * @throws URISyntaxException if the Location URI syntax is incorrect.
//	 */
//	@PutMapping("/announcements")
//	public ResponseEntity<AnnouncementDTO> updateAnnouncement(@RequestBody AnnouncementDTO announcementDTO)
//			throws URISyntaxException {
//		log.debug("REST request to update Announcement : {}", announcementDTO);
//		if (announcementDTO.getId() == null) {
//			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
//		}
//		AnnouncementDTO result = announcementService.save(announcementDTO);
//		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
//				announcementDTO.getId().toString())).body(result);
//	}
//
//	/**
//	 * {@code GET  /announcements} : get all the announcements.
//	 *
//	 * @param pageable the pagination information.
//	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
//	 *         of announcements in body.
//	 */
//	@GetMapping("/announcements")
//	public ResponseEntity<List<AnnouncementDTO>> getAllAnnouncements(Pageable pageable) {
//		log.debug("REST request to get a page of announcements");
//		Page<AnnouncementDTO> page = announcementService.findAll(pageable);
//		HttpHeaders headers = PaginationUtil
//				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
//		return ResponseEntity.ok().headers(headers).body(page.getContent());
//	}
//
//	/**
//	 * {@code GET  /announcements/:id} : get the "id" announcement.
//	 *
//	 * @param id the id of the announcementDTO to retrieve.
//	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
//	 *         the announcementDTO, or with status {@code 404 (Not Found)}.
//	 */
//	@GetMapping("/announcements/{id}")
//	public ResponseEntity<AnnouncementDTO> getAnnouncement(@PathVariable Long id) {
//		log.debug("REST request to get Announcement : {}", id);
//		Optional<AnnouncementDTO> announcementDTO = announcementService.findOne(id);
//		return ResponseUtil.wrapOrNotFound(announcementDTO);
//	}
//
//	/**
//	 * {@code DELETE  /announcements/:id} : delete the "id" announcement.
//	 *
//	 * @param id the id of the announcementDTO to delete.
//	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
//	 */
//	@DeleteMapping("/announcements/{id}")
//	public ResponseEntity<Void> deleteAnnouncement(@PathVariable Long id) {
//		log.debug("REST request to delete Announcement : {}", id);
//		announcementService.delete(id);
//		return ResponseEntity.noContent()
//				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
//				.build();
//	}

	@PostMapping("/announcements")
	@Secured({ "ROLE_ADMIN" })
	public ResponseEntity<AnnouncementDTO> createAnnouncementNew(
			@Valid @RequestBody AnnouncementRequest announcementRequest) throws URISyntaxException {
		log.debug("REST request to create Announcement : {}", announcementRequest);
		AnnouncementDTO result = announcementService.createAnnouncement(announcementRequest);
		return ResponseEntity
				.created(new URI("/api/announcements-new/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	@PutMapping("/announcements/{id}")
	@Secured({ "ROLE_ADMIN" })
	public ResponseEntity<AnnouncementDTO> updateAnnouncementNew(@PathVariable Long id,
			@Valid @RequestBody AnnouncementRequest announcementRequest) throws URISyntaxException {
		log.debug("REST request to update Announcement : {}", announcementRequest);
		if (id == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		AnnouncementDTO result = announcementService.updateAnnouncement(id, announcementRequest);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	@DeleteMapping("/announcements/{id}")
	@Secured({ "ROLE_ADMIN" })
	public ResponseEntity<Void> deleteAnnouncementNew(@PathVariable Long id) {
		log.debug("REST request to delete Announcement : {}", id);
		announcementService.deleteAnnouncement(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
				.build();
	}

	@GetMapping("/announcements")
	@Secured({ "ROLE_ADMIN" })
	public ResponseEntity<List<AnnouncementDTO>> getAllAnnouncementsAdmin(Pageable pageable) {
		log.debug("REST request to get a page of announcements");
		Page<AnnouncementDTO> page = announcementService.findAllAdmin(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}
}
