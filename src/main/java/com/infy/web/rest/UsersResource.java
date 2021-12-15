/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infy.service.UsersService;
import com.infy.service.model.FireBaseTokenRequest;
import com.infy.service.model.UsersResponse;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.infy.domain.Users}.
 */
@RestController
@RequestMapping("/api")
public class UsersResource {

	private final Logger log = LoggerFactory.getLogger(UsersResource.class);

	private static final String ENTITY_NAME = "users";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final UsersService usersService;

	public UsersResource(UsersService usersService) {
		this.usersService = usersService;
	}

//    /**
//     * {@code POST  /users} : Create a new users.
//     *
//     * @param usersDTO the usersDTO to create.
//     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new usersDTO, or with status {@code 400 (Bad Request)} if the users has already an ID.
//     * @throws URISyntaxException if the Location URI syntax is incorrect.
//     */
//    @PostMapping("/users")
//    public ResponseEntity<UsersDTO> createUsers(@Valid @RequestBody UsersDTO usersDTO) throws URISyntaxException {
//        log.debug("REST request to save Users : {}", usersDTO);
//        if (usersDTO.getId() != null) {
//            throw new BadRequestAlertException("A new users cannot already have an ID", ENTITY_NAME, "idexists");
//        }
//        UsersDTO result = usersService.save(usersDTO);
//        return ResponseEntity.created(new URI("/api/users/" + result.getId()))
//            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
//            .body(result);
//    }
//
//    /**
//     * {@code PUT  /users} : Updates an existing users.
//     *
//     * @param usersDTO the usersDTO to update.
//     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated usersDTO,
//     * or with status {@code 400 (Bad Request)} if the usersDTO is not valid,
//     * or with status {@code 500 (Internal Server Error)} if the usersDTO couldn't be updated.
//     * @throws URISyntaxException if the Location URI syntax is incorrect.
//     */
//    @PutMapping("/users")
//    public ResponseEntity<UsersDTO> updateUsers(@Valid @RequestBody UsersDTO usersDTO) throws URISyntaxException {
//        log.debug("REST request to update Users : {}", usersDTO);
//        if (usersDTO.getId() == null) {
//            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
//        }
//        UsersDTO result = usersService.save(usersDTO);
//        return ResponseEntity.ok()
//            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, usersDTO.getId().toString()))
//            .body(result);
//    }
//
//    /**
//     * {@code GET  /users} : get all the users.
//     *
//     * @param pageable the pagination information.
//     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of users in body.
//     */
//    @GetMapping("/users-app")
//    public ResponseEntity<List<UsersDTO>> getAllUsers(Pageable pageable) {
//        log.debug("REST request to get a page of Users");
//        Page<UsersDTO> page = usersService.findAll(pageable);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
//        return ResponseEntity.ok().headers(headers).body(page.getContent());
//    }
//
//    /**
//     * {@code GET  /users/:id} : get the "id" users.
//     *
//     * @param id the id of the usersDTO to retrieve.
//     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the usersDTO, or with status {@code 404 (Not Found)}.
//     */
//    @GetMapping("/users/{id}")
//    public ResponseEntity<UsersDTO> getUsers(@PathVariable Long id) {
//        log.debug("REST request to get Users : {}", id);
//        Optional<UsersDTO> usersDTO = usersService.findOne(id);
//        return ResponseUtil.wrapOrNotFound(usersDTO);
//    }
//
//    /**
//     * {@code DELETE  /users/:id} : delete the "id" users.
//     *
//     * @param id the id of the usersDTO to delete.
//     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
//     */
//    @DeleteMapping("/users/{id}")
//    public ResponseEntity<Void> deleteUsers(@PathVariable Long id) {
//        log.debug("REST request to delete Users : {}", id);
//        usersService.delete(id);
//        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
//    }

	@GetMapping("/currentLoginUsers")
	public ResponseEntity<UsersResponse> getCurrentLoginUser() {
		log.debug("REST request to get current login Users : {}");
		Optional<UsersResponse> result = usersService.currentLoginUser();
		return ResponseUtil.wrapOrNotFound(result);
	}

	@GetMapping("/users-accept-privacy")
	public ResponseEntity<UsersResponse> usersAcceptPrivacy() {
		log.debug("REST request to accept privacy of current login Users : {}");
		Optional<UsersResponse> result = usersService.acceptPrivacyOfCurrentUser();
		return ResponseUtil.wrapOrNotFound(result);
	}

	@GetMapping("/users/userid/{id}")
	public ResponseEntity<UsersResponse> getUsersByUserId(@PathVariable String id) {
		log.debug("REST request to get Users by userId : {}");
		Optional<UsersResponse> result = usersService.findByUserId(id);
		return ResponseUtil.wrapOrNotFound(result);
	}

	@GetMapping("/users/username/{id}")
	public ResponseEntity<List<UsersResponse>> getUsersByUserName(@PathVariable String id) {
		log.debug("REST request to get Users by username : {}");
		List<UsersResponse> result = usersService.findByUserName(id);
		return ResponseEntity.ok().body(result);
	}

	@PostMapping("/users/token")
	public ResponseEntity<Void> updateFireBaseMessagingToken(
			@Valid @RequestBody FireBaseTokenRequest fireBaseTokenRequest) {
		log.debug("REST request to update fireBaseToken");
		usersService.updateToken(fireBaseTokenRequest);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/users/notification-subscription")
	public ResponseEntity<Void> usersNotificationSubscription(
			@RequestParam(defaultValue = "true", required = true) Boolean subscribe) {
		log.debug("REST request to update notification subscription : {}", subscribe);
		usersService.updateNotificationSubscription(subscribe);
		return ResponseEntity.noContent().build();
	}
}
