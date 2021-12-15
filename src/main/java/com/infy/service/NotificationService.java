/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.domain.Notification;
import com.infy.service.dto.NotificationDTO;
import com.infy.service.model.NotificationSeenRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.Notification}.
 */
public interface NotificationService {

	/**
	 * Save a notification.
	 *
	 * @param notificationDTO the entity to save.
	 * @return the persisted entity.
	 */
	NotificationDTO save(NotificationDTO notificationDTO);

	/**
	 * Get all the notifications.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<NotificationDTO> findAll(Pageable pageable);

	/**
	 * Get the "id" notification.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<NotificationDTO> findOne(Long id);

	/**
	 * Delete the "id" notification.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);

	Page<NotificationDTO> getAllNotificationOfCurrentUser(Pageable pageable);

	void seenNotification(Long id);

	Page<NotificationDTO> getAllNewNotification(Pageable pageable);

	void pushNotificationToFireBase(Notification notification);

	void createNotification(Notification notification);

	void seenMultipleNotifications(NotificationSeenRequest notificationSeenRequest);
}
