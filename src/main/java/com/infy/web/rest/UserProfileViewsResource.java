/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.UserProfileViewsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.UserProfileViewsDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.infy.domain.UserProfileViews}.
 */
@RestController
@RequestMapping("/api")
public class UserProfileViewsResource {

    private final Logger log = LoggerFactory.getLogger(UserProfileViewsResource.class);

    private static final String ENTITY_NAME = "userProfileViews";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserProfileViewsService userProfileViewsService;

    public UserProfileViewsResource(UserProfileViewsService userProfileViewsService) {
        this.userProfileViewsService = userProfileViewsService;
    }

    /**
     * {@code POST  /user-profile-views} : Create a new userProfileViews.
     *
     * @param userProfileViewsDTO the userProfileViewsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userProfileViewsDTO, or with status {@code 400 (Bad Request)} if the userProfileViews has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-profile-views")
    public ResponseEntity<UserProfileViewsDTO> createUserProfileViews(@Valid @RequestBody UserProfileViewsDTO userProfileViewsDTO) throws URISyntaxException {
        log.debug("REST request to save UserProfileViews : {}", userProfileViewsDTO);
        if (userProfileViewsDTO.getId() != null) {
            throw new BadRequestAlertException("A new userProfileViews cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserProfileViewsDTO result = userProfileViewsService.save(userProfileViewsDTO);
        return ResponseEntity.created(new URI("/api/user-profile-views/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-profile-views} : Updates an existing userProfileViews.
     *
     * @param userProfileViewsDTO the userProfileViewsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userProfileViewsDTO,
     * or with status {@code 400 (Bad Request)} if the userProfileViewsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userProfileViewsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-profile-views")
    public ResponseEntity<UserProfileViewsDTO> updateUserProfileViews(@Valid @RequestBody UserProfileViewsDTO userProfileViewsDTO) throws URISyntaxException {
        log.debug("REST request to update UserProfileViews : {}", userProfileViewsDTO);
        if (userProfileViewsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserProfileViewsDTO result = userProfileViewsService.save(userProfileViewsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userProfileViewsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-profile-views} : get all the userProfileViews.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userProfileViews in body.
     */
    @GetMapping("/user-profile-views")
    public ResponseEntity<List<UserProfileViewsDTO>> getAllUserProfileViews(Pageable pageable) {
        log.debug("REST request to get a page of UserProfileViews");
        Page<UserProfileViewsDTO> page = userProfileViewsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-profile-views/:id} : get the "id" userProfileViews.
     *
     * @param id the id of the userProfileViewsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userProfileViewsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-profile-views/{id}")
    public ResponseEntity<UserProfileViewsDTO> getUserProfileViews(@PathVariable Long id) {
        log.debug("REST request to get UserProfileViews : {}", id);
        Optional<UserProfileViewsDTO> userProfileViewsDTO = userProfileViewsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userProfileViewsDTO);
    }

    /**
     * {@code DELETE  /user-profile-views/:id} : delete the "id" userProfileViews.
     *
     * @param id the id of the userProfileViewsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-profile-views/{id}")
    public ResponseEntity<Void> deleteUserProfileViews(@PathVariable Long id) {
        log.debug("REST request to delete UserProfileViews : {}", id);
        userProfileViewsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
