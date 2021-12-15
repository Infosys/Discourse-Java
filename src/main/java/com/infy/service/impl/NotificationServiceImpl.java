/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.FireBaseMessagingService;
import com.infy.service.NotificationService;
import com.infy.domain.Notification;
import com.infy.domain.Users;
import com.infy.domain.enumeration.NotificationStatus;
import com.infy.repository.NotificationRepository;
import com.infy.repository.UsersRepository;
import com.infy.security.SecurityUtils;
import com.infy.service.dto.NotificationDTO;
import com.infy.service.mapper.NotificationMapper;
import com.infy.service.model.NotificationSeenRequest;
import com.infy.web.rest.errors.BadRequestAlertException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Notification}.
 */
@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

	private final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);

	private final NotificationRepository notificationRepository;

	private final NotificationMapper notificationMapper;

	private final FireBaseMessagingService fireBaseMessagingService;

	private final UsersRepository usersRepository;

	public NotificationServiceImpl(NotificationRepository notificationRepository, NotificationMapper notificationMapper,
			FireBaseMessagingService fireBaseMessagingService, UsersRepository usersRepository) {
		this.notificationRepository = notificationRepository;
		this.notificationMapper = notificationMapper;
		this.fireBaseMessagingService = fireBaseMessagingService;
		this.usersRepository = usersRepository;
	}

	@Override
	public NotificationDTO save(NotificationDTO notificationDTO) {
		log.debug("Request to save Notification : {}", notificationDTO);
		Notification notification = notificationMapper.toEntity(notificationDTO);
		notification = notificationRepository.save(notification);
		return notificationMapper.toDto(notification);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<NotificationDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Notifications");
		return notificationRepository.findAll(pageable).map(notificationMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<NotificationDTO> findOne(Long id) {
		log.debug("Request to get Notification : {}", id);
		return notificationRepository.findById(id).map(notificationMapper::toDto);
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete Notification : {}", id);
		notificationRepository.deleteById(id);
	}

	@Override
	public Page<NotificationDTO> getAllNotificationOfCurrentUser(Pageable pageable) {
		log.debug("Request to get All Notification of current user");

		Optional<String> userIdOptional = SecurityUtils.getCurrentUserLoginUserId();
		if (userIdOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid user id", "Users", "Invalid user id");
		}
		return notificationRepository.findByUserId(userIdOptional.get(), pageable).map(notificationMapper::toDto);
	}

	@Override
	public void seenNotification(Long id) {
		log.debug("Seen Notification : {}", id);

		Optional<String> userIdOptional = SecurityUtils.getCurrentUserLoginUserId();
		if (userIdOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid user id", "Users", "Invalid user id");
		}

		Optional<Notification> notificationOptional = notificationRepository.findById(id);
		if (notificationOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid notification id", "Notification", "Invalid notification id");
		}

		Notification notification = notificationOptional.get();

		notification.setSeenAt(Instant.now());
		notification.setNotificationStatus(NotificationStatus.SEEN);
		notificationRepository.save(notification);
	}

	@Override
	public Page<NotificationDTO> getAllNewNotification(Pageable pageable) {
		log.debug("Request to get All New Notification");

		Optional<String> userIdOptional = SecurityUtils.getCurrentUserLoginUserId();
		if (userIdOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid user id", "Users", "Invalid user id");
		}
		return notificationRepository
				.findByUserIdAndNotificationStatus(userIdOptional.get(), NotificationStatus.CREATED, pageable)
				.map(notificationMapper::toDto);
	}

	@Override
	public void createNotification(Notification notification) {
		log.debug("Request to save notification : {}", notification);
		notification = notificationRepository.save(notification);
		pushNotificationToFireBase(notification);
	}

	@Override
	public void pushNotificationToFireBase(Notification notification) {
		log.debug("Request to push notification to firebase");

		Optional<Users> usersOptional = usersRepository.findByUserId(notification.getUserId());
		if (usersOptional.isPresent()) {
			if (usersOptional.get().getFireBaseToken() != null && usersOptional.get().getNotificationSubscription()) {
				fireBaseMessagingService.sendNotification(notification, usersOptional.get().getFireBaseToken());
			}
		}
	}

	@Override
	public void seenMultipleNotifications(NotificationSeenRequest notificationSeenRequest) {
		log.debug("Seen Multiple Notifications : {}", notificationSeenRequest);

		Optional<String> userIdOptional = SecurityUtils.getCurrentUserLoginUserId();
		if (userIdOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid user id", "Users", "Invalid user id");
		}

		notificationSeenRequest.getNotificationIds().forEach(id -> {
			Optional<Notification> notificationOptional = notificationRepository.findById(id);
			if (notificationOptional.isPresent()) {
				Notification notification = notificationOptional.get();

				notification.setSeenAt(Instant.now());
				notification.setNotificationStatus(NotificationStatus.SEEN);
				notificationRepository.save(notification);
			}
		});
	}
}
