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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.infy.service.NotificationService;
import com.infy.service.dto.NotificationDTO;
import com.infy.service.model.NotificationSeenRequest;
import com.infy.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.infy.domain.Notification}.
 */
@RestController
@RequestMapping("/api")
public class NotificationResource {

	private final Logger log = LoggerFactory.getLogger(NotificationResource.class);

	private static final String ENTITY_NAME = "notification";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final NotificationService notificationService;

	public NotificationResource(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

//	/**
//	 * {@code POST  /notification-new} : Create a new notification.
//	 *
//	 * @param notificationDTO the notificationDTO to create.
//	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
//	 *         body the new notificationDTO, or with status
//	 *         {@code 400 (Bad Request)} if the notification has already an ID.
//	 * @throws URISyntaxException if the Location URI syntax is incorrect.
//	 */
//	@PostMapping("/notification-new")
//	public ResponseEntity<NotificationDTO> createNotification(@RequestBody NotificationDTO notificationDTO)
//			throws URISyntaxException {
//		log.debug("REST request to save Notification : {}", notificationDTO);
//		if (notificationDTO.getId() != null) {
//			throw new BadRequestAlertException("A new notification cannot already have an ID", ENTITY_NAME, "idexists");
//		}
//		NotificationDTO result = notificationService.save(notificationDTO);
//		return ResponseEntity
//				.created(new URI("/api/notification-new/" + result.getId())).headers(HeaderUtil
//						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
//				.body(result);
//	}
//
//	/**
//	 * {@code PUT  /notification-new} : Updates an existing notification.
//	 *
//	 * @param notificationDTO the notificationDTO to update.
//	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
//	 *         the updated notificationDTO, or with status {@code 400 (Bad Request)}
//	 *         if the notificationDTO is not valid, or with status
//	 *         {@code 500 (Internal Server Error)} if the notificationDTO couldn't
//	 *         be updated.
//	 * @throws URISyntaxException if the Location URI syntax is incorrect.
//	 */
//	@PutMapping("/notification-new")
//	public ResponseEntity<NotificationDTO> updateNotification(@RequestBody NotificationDTO notificationDTO)
//			throws URISyntaxException {
//		log.debug("REST request to update Notification : {}", notificationDTO);
//		if (notificationDTO.getId() == null) {
//			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
//		}
//		NotificationDTO result = notificationService.save(notificationDTO);
//		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
//				notificationDTO.getId().toString())).body(result);
//	}
//
//	/**
//	 * {@code GET  /notification-new} : get all the notifications.
//	 *
//	 * @param pageable the pagination information.
//	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
//	 *         of notifications in body.
//	 */
//	@GetMapping("/notification-new")
//	public ResponseEntity<List<NotificationDTO>> getAllNotifications(Pageable pageable) {
//		log.debug("REST request to get a page of Notifications");
//		Page<NotificationDTO> page = notificationService.findAll(pageable);
//		HttpHeaders headers = PaginationUtil
//				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
//		return ResponseEntity.ok().headers(headers).body(page.getContent());
//	}
//
//	/**
//	 * {@code GET  /notifications/:id} : get the "id" notification.
//	 *
//	 * @param id the id of the notificationDTO to retrieve.
//	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
//	 *         the notificationDTO, or with status {@code 404 (Not Found)}.
//	 */
//	@GetMapping("/notification-new/{id}")
//	public ResponseEntity<NotificationDTO> getNotification(@PathVariable Long id) {
//		log.debug("REST request to get Notification : {}", id);
//		Optional<NotificationDTO> notificationDTO = notificationService.findOne(id);
//		return ResponseUtil.wrapOrNotFound(notificationDTO);
//	}
//
//	/**
//	 * {@code DELETE  /notification-new/:id} : delete the "id" notification.
//	 *
//	 * @param id the id of the notificationDTO to delete.
//	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
//	 */
//	@DeleteMapping("/notification-new/{id}")
//	public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
//		log.debug("REST request to delete Notification : {}", id);
//		notificationService.delete(id);
//		return ResponseEntity.noContent()
//				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
//				.build();
//	}

	@GetMapping("/notifications")
	public ResponseEntity<List<NotificationDTO>> getAllNotificationsOfCurrentUser(Pageable pageable) {
		log.debug("REST request to get a page of Notifications of current user");
		Page<NotificationDTO> page = notificationService.getAllNotificationOfCurrentUser(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	@GetMapping("/notifications/unread")
	public ResponseEntity<List<NotificationDTO>> getAllUnReadNotificationsOfCurrentUser(Pageable pageable) {
		log.debug("REST request to get a page of UnRead Notifications of current user");
		Page<NotificationDTO> page = notificationService.getAllNewNotification(pageable);
		;
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	@GetMapping("/notifications/seen/{id}")
	public ResponseEntity<Void> seenNotification(@PathVariable Long id) {
		log.debug("REST request to seen notification : {}", id);
		notificationService.seenNotification(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/notifications/seen")
	public ResponseEntity<Void> seenMultipleNotification(@RequestBody NotificationSeenRequest notificationSeenRequest) {
		log.debug("REST request to seen notifications : {}", notificationSeenRequest);
		notificationService.seenMultipleNotifications(notificationSeenRequest);
		return ResponseEntity.noContent().build();
	}
}
