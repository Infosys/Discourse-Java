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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.infy.service.UserStatsService;
import com.infy.service.dto.UserStatsDTO;
import com.infy.service.model.UserStatsResponse;
import com.infy.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.infy.domain.UserStats}.
 */
@RestController
@RequestMapping("/api")
public class UserStatsResource {

	private final Logger log = LoggerFactory.getLogger(UserStatsResource.class);

	private static final String ENTITY_NAME = "userStats";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final UserStatsService userStatsService;

	public UserStatsResource(UserStatsService userStatsService) {
		this.userStatsService = userStatsService;
	}

	/**
	 * {@code POST  /user-stats} : Create a new userStats.
	 *
	 * @param userStatsDTO the userStatsDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new userStatsDTO, or with status {@code 400 (Bad Request)}
	 *         if the userStats has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/user-stats")
	public ResponseEntity<UserStatsDTO> createUserStats(@Valid @RequestBody UserStatsDTO userStatsDTO)
			throws URISyntaxException {
		log.debug("REST request to save UserStats : {}", userStatsDTO);
		if (userStatsDTO.getId() != null) {
			throw new BadRequestAlertException("A new userStats cannot already have an ID", ENTITY_NAME, "idexists");
		}
		UserStatsDTO result = userStatsService.save(userStatsDTO);
		return ResponseEntity
				.created(new URI("/api/user-stats/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /user-stats} : Updates an existing userStats.
	 *
	 * @param userStatsDTO the userStatsDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated userStatsDTO, or with status {@code 400 (Bad Request)} if
	 *         the userStatsDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the userStatsDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/user-stats")
	public ResponseEntity<UserStatsDTO> updateUserStats(@Valid @RequestBody UserStatsDTO userStatsDTO)
			throws URISyntaxException {
		log.debug("REST request to update UserStats : {}", userStatsDTO);
		if (userStatsDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		UserStatsDTO result = userStatsService.save(userStatsDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
				userStatsDTO.getId().toString())).body(result);
	}

	/**
	 * {@code GET  /user-stats} : get all the userStats.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of userStats in body.
	 */
	@GetMapping("/user-stats")
	public ResponseEntity<List<UserStatsDTO>> getAllUserStats(Pageable pageable) {
		log.debug("REST request to get a page of UserStats");
		Page<UserStatsDTO> page = userStatsService.findAll(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /user-stats/:id} : get the "id" userStats.
	 *
	 * @param id the id of the userStatsDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the userStatsDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/user-stats/{id}")
	public ResponseEntity<UserStatsDTO> getUserStats(@PathVariable Long id) {
		log.debug("REST request to get UserStats : {}", id);
		Optional<UserStatsDTO> userStatsDTO = userStatsService.findOne(id);
		return ResponseUtil.wrapOrNotFound(userStatsDTO);
	}

	/**
	 * {@code DELETE  /user-stats/:id} : delete the "id" userStats.
	 *
	 * @param id the id of the userStatsDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/user-stats/{id}")
	public ResponseEntity<Void> deleteUserStats(@PathVariable Long id) {
		log.debug("REST request to delete UserStats : {}", id);
		userStatsService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
				.build();
	}

	@GetMapping("/user-stats/user")
	public ResponseEntity<UserStatsResponse> getUserStatsOfCurrentUser() {
		log.debug("REST request to get UserStats of current login user");
		Optional<UserStatsResponse> result = userStatsService.findByCurrentLoginUser();
		return ResponseUtil.wrapOrNotFound(result);
	}
}
