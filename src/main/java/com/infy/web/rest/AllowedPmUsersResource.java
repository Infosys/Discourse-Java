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

import com.infy.service.AllowedPmUsersService;
import com.infy.service.dto.AllowedPmUsersDTO;
import com.infy.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.infy.domain.AllowedPmUsers}.
 */
@RestController
@RequestMapping("/api")
public class AllowedPmUsersResource {

	private final Logger log = LoggerFactory.getLogger(AllowedPmUsersResource.class);

	private static final String ENTITY_NAME = "allowedPmUsers";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final AllowedPmUsersService allowedPmUsersService;

	public AllowedPmUsersResource(AllowedPmUsersService allowedPmUsersService) {
		this.allowedPmUsersService = allowedPmUsersService;
	}

	/**
	 * {@code POST  /allowed-pm-users} : Create a new allowedPmUsers.
	 *
	 * @param allowedPmUsersDTO the allowedPmUsersDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new allowedPmUsersDTO, or with status
	 *         {@code 400 (Bad Request)} if the allowedPmUsers has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/allowed-pm-users")
	public ResponseEntity<AllowedPmUsersDTO> createAllowedPmUsers(
			@Valid @RequestBody AllowedPmUsersDTO allowedPmUsersDTO) throws URISyntaxException {
		log.debug("REST request to save AllowedPmUsers : {}", allowedPmUsersDTO);
		if (allowedPmUsersDTO.getId() != null) {
			throw new BadRequestAlertException("A new allowedPmUsers cannot already have an ID", ENTITY_NAME,
					"idexists");
		}
		AllowedPmUsersDTO result = allowedPmUsersService.save(allowedPmUsersDTO);
		return ResponseEntity
				.created(new URI("/api/allowed-pm-users/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /allowed-pm-users} : Updates an existing allowedPmUsers.
	 *
	 * @param allowedPmUsersDTO the allowedPmUsersDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated allowedPmUsersDTO, or with status
	 *         {@code 400 (Bad Request)} if the allowedPmUsersDTO is not valid, or
	 *         with status {@code 500 (Internal Server Error)} if the
	 *         allowedPmUsersDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/allowed-pm-users")
	public ResponseEntity<AllowedPmUsersDTO> updateAllowedPmUsers(
			@Valid @RequestBody AllowedPmUsersDTO allowedPmUsersDTO) throws URISyntaxException {
		log.debug("REST request to update AllowedPmUsers : {}", allowedPmUsersDTO);
		if (allowedPmUsersDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		AllowedPmUsersDTO result = allowedPmUsersService.save(allowedPmUsersDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
				allowedPmUsersDTO.getId().toString())).body(result);
	}

	/**
	 * {@code GET  /allowed-pm-users} : get all the allowedPmUsers.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of allowedPmUsers in body.
	 */
	@GetMapping("/allowed-pm-users")
	public ResponseEntity<List<AllowedPmUsersDTO>> getAllAllowedPmUsers(Pageable pageable) {
		log.debug("REST request to get a page of AllowedPmUsers");
		Page<AllowedPmUsersDTO> page = allowedPmUsersService.findAll(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /allowed-pm-users/:id} : get the "id" allowedPmUsers.
	 *
	 * @param id the id of the allowedPmUsersDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the allowedPmUsersDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/allowed-pm-users/{id}")
	public ResponseEntity<AllowedPmUsersDTO> getAllowedPmUsers(@PathVariable Long id) {
		log.debug("REST request to get AllowedPmUsers : {}", id);
		Optional<AllowedPmUsersDTO> allowedPmUsersDTO = allowedPmUsersService.findOne(id);
		return ResponseUtil.wrapOrNotFound(allowedPmUsersDTO);
	}

	/**
	 * {@code DELETE  /allowed-pm-users/:id} : delete the "id" allowedPmUsers.
	 *
	 * @param id the id of the allowedPmUsersDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/allowed-pm-users/{id}")
	public ResponseEntity<Void> deleteAllowedPmUsers(@PathVariable Long id) {
		log.debug("REST request to delete AllowedPmUsers : {}", id);
		allowedPmUsersService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
				.build();
	}
}
