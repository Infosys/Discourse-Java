/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import java.time.Instant;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infy.domain.Announcement;
import com.infy.domain.Users;
import com.infy.domain.enumeration.AnnouncementStatus;
import com.infy.repository.AnnouncmentRepository;
import com.infy.repository.UsersRepository;
import com.infy.security.SecurityUtils;
import com.infy.service.AnnouncementService;
import com.infy.service.dto.AnnouncementDTO;
import com.infy.service.mapper.AnnouncementMapper;
import com.infy.service.mapper.AnnouncementResponseMapper;
import com.infy.service.model.AnnouncementRequest;
import com.infy.service.model.AnnouncementResponse;
import com.infy.web.rest.errors.BadRequestAlertException;

/**
 * Service Implementation for managing {@link Announcement}.
 */
@Service
@Transactional
public class AnnouncementServiceImpl implements AnnouncementService {

	private final Logger log = LoggerFactory.getLogger(AnnouncementServiceImpl.class);

	private final AnnouncmentRepository announcmentRepository;

	private final AnnouncementMapper announcementMapper;

	private final UsersRepository usersRepository;

	private final AnnouncementResponseMapper announcementResponseMapper;

	public AnnouncementServiceImpl(AnnouncmentRepository announcmentRepository, AnnouncementMapper announcementMapper,
			UsersRepository usersRepository, AnnouncementResponseMapper announcementResponseMapper) {
		this.announcmentRepository = announcmentRepository;
		this.announcementMapper = announcementMapper;
		this.usersRepository = usersRepository;
		this.announcementResponseMapper = announcementResponseMapper;
	}

	@Override
	public AnnouncementDTO save(AnnouncementDTO announcementDTO) {
		log.debug("Request to save Announcement : {}", announcementDTO);
		Announcement announcement = announcementMapper.toEntity(announcementDTO);
		announcement = announcmentRepository.save(announcement);
		return announcementMapper.toDto(announcement);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<AnnouncementDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Announcments");
		return announcmentRepository.findAll(pageable).map(announcementMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<AnnouncementDTO> findOne(Long id) {
		log.debug("Request to get Announcement : {}", id);
		return announcmentRepository.findById(id).map(announcementMapper::toDto);
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete Announcement : {}", id);
		announcmentRepository.deleteById(id);
	}

	@Override
	public AnnouncementDTO createAnnouncement(AnnouncementRequest announcementRequest) {
		log.debug("Request to create New Announcement : {} ", announcementRequest);

		Announcement announcement = new Announcement();
		announcement.setTitle(announcementRequest.getTitle());
		announcement.setRaw(announcementRequest.getRaw());
		announcement.setStatus(AnnouncementStatus.ACTIVE);
		announcement = announcmentRepository.save(announcement);

		return announcementMapper.toDto(announcement);


//		Optional<String> userIdOptional = SecurityUtils.getCurrentUserLoginUserId();
//		if (userIdOptional.isEmpty()) {
//			throw new BadRequestAlertException("Invalid user id", "Users", "Invalid user id");
//		}
//
//		Optional<Users> usersOptional = usersRepository.findByUserId(userIdOptional.get());
//		if (usersOptional.isEmpty()) {
//			throw new BadRequestAlertException("User is not Onboarded", "Users", "User is not Onboarded");
//		}
//		Users users = usersOptional.get();
//
//		if (users.isAdmin()) {
//			Announcement announcement = new Announcement();
//			announcement.setTitle(announcementRequest.getTitle());
//			announcement.setRaw(announcementRequest.getRaw());
//			announcement.setStatus(AnnouncementStatus.ACTIVE);
//			announcement = announcmentRepository.save(announcement);
//
//			return announcementMapper.toDto(announcement);
//		} else {
//			throw new BadRequestAlertException("Only Admin can create Announcement", "Announcement",
//					"Only Admin can create Announcement");
//		}
	}

	@Override
	public AnnouncementDTO updateAnnouncement(Long id, AnnouncementRequest announcementRequest) {
		log.debug("Request to update Announcement : {} ", announcementRequest);

		Optional<Announcement> announcementOptional = announcmentRepository.findById(id);
		if (announcementOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid Announcement id", "Announcement",
					"Invalid Announcement id");
		}
		Announcement announcement = announcementOptional.get();
		announcement.setTitle(announcementRequest.getTitle());
		announcement.setRaw(announcementRequest.getRaw());
		announcement = announcmentRepository.save(announcement);

		return announcementMapper.toDto(announcement);
//
//		Optional<String> userIdOptional = SecurityUtils.getCurrentUserLoginUserId();
//		if (userIdOptional.isEmpty()) {
//			throw new BadRequestAlertException("Invalid user id", "Users", "Invalid user id");
//		}
//
//		Optional<Users> usersOptional = usersRepository.findByUserId(userIdOptional.get());
//		if (usersOptional.isEmpty()) {
//			throw new BadRequestAlertException("User is not Onboarded", "Users", "User is not Onboarded");
//		}
//		Users users = usersOptional.get();
//
//		if (users.isAdmin()) {
//
//			Optional<Announcement> announcementOptional = announcmentRepository.findById(id);
//			if (announcementOptional.isEmpty()) {
//				throw new BadRequestAlertException("Invalid Announcement id", "Announcement",
//						"Invalid Announcement id");
//			}
//			Announcement announcement = announcementOptional.get();
//			announcement.setTitle(announcementRequest.getTitle());
//			announcement.setRaw(announcementRequest.getRaw());
//			announcement = announcmentRepository.save(announcement);
//
//			return announcementMapper.toDto(announcement);
//		} else {
//			throw new BadRequestAlertException("Only Admin can update Announcement", "Announcement",
//					"Only Admin can update Announcement");
//		}
	}

	@Override
	public void deleteAnnouncement(Long id) {
		log.debug("Request to delete Announcement : {} ", id);

		Optional<String> userIdOptional = SecurityUtils.getCurrentUserLoginUserId();
		if (userIdOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid user id", "Users", "Invalid user id");
		}

		Optional<Users> usersOptional = usersRepository.findByUserId(userIdOptional.get());
		if (usersOptional.isEmpty()) {
			throw new BadRequestAlertException("User is not Onboarded", "Users", "User is not Onboarded");
		}
		Users users = usersOptional.get();

		if (users.isAdmin()) {

			Optional<Announcement> announcementOptional = announcmentRepository.findById(id);
			if (announcementOptional.isEmpty()) {
				throw new BadRequestAlertException("Invalid Announcement id", "Announcement",
						"Invalid Announcement id");
			}

			Announcement announcement = announcementOptional.get();
			announcement.setDeletedAt(Instant.now());
			announcement.setDeletedBy(users.getUserId());
			announcement.setStatus(AnnouncementStatus.INACTIVE);

			announcmentRepository.save(announcement);
		} else {
			throw new BadRequestAlertException("Only Admin can delete Announcement", "Announcement",
					"Only Admin can delete Announcement");
		}
	}

	@Override
	public Page<AnnouncementDTO> findAllAdmin(Pageable pageable) {
		log.debug("Request to get all Announcments");

//		Optional<String> userIdOptional = SecurityUtils.getCurrentUserLoginUserId();
//		if (userIdOptional.isEmpty()) {
//			throw new BadRequestAlertException("Invalid user id", "Users", "Invalid user id");
//		}
//
//		Optional<Users> usersOptional = usersRepository.findByUserId(userIdOptional.get());
//		if (usersOptional.isEmpty()) {
//			throw new BadRequestAlertException("User is not Onboarded", "Users", "User is not Onboarded");
//		}
//		Users users = usersOptional.get();
//
//		if (!users.isAdmin()) {
//			throw new BadRequestAlertException("User is not Admin", "Users", "User is not Admin");
//		}

		return announcmentRepository.findAll(pageable).map(announcementMapper::toDto);
	}

	@Override
	public Page<AnnouncementResponse> findAllActiveAnnouncement(Pageable pageable) {
		log.debug("find all active Announcements");
		return announcmentRepository.findByStatus(AnnouncementStatus.ACTIVE, pageable)
				.map(announcementResponseMapper::toDto);
	}

	@Override
	public Optional<AnnouncementResponse> findByIdActiveAnnouncement(Long id) {
		log.debug("find Announcement by id : {} ", id);
		return announcmentRepository.findByIdAndStatus(id, AnnouncementStatus.ACTIVE)
				.map(announcementResponseMapper::toDto);
	}
}
