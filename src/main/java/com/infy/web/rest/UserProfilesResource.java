/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.UserProfilesService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.UserProfilesDTO;

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
 * REST controller for managing {@link com.infy.domain.UserProfiles}.
 */
@RestController
@RequestMapping("/api")
public class UserProfilesResource {

    private final Logger log = LoggerFactory.getLogger(UserProfilesResource.class);

    private static final String ENTITY_NAME = "userProfiles";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserProfilesService userProfilesService;

    public UserProfilesResource(UserProfilesService userProfilesService) {
        this.userProfilesService = userProfilesService;
    }

    /**
     * {@code POST  /user-profiles} : Create a new userProfiles.
     *
     * @param userProfilesDTO the userProfilesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userProfilesDTO, or with status {@code 400 (Bad Request)} if the userProfiles has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-profiles")
    public ResponseEntity<UserProfilesDTO> createUserProfiles(@Valid @RequestBody UserProfilesDTO userProfilesDTO) throws URISyntaxException {
        log.debug("REST request to save UserProfiles : {}", userProfilesDTO);
        if (userProfilesDTO.getId() != null) {
            throw new BadRequestAlertException("A new userProfiles cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserProfilesDTO result = userProfilesService.save(userProfilesDTO);
        return ResponseEntity.created(new URI("/api/user-profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-profiles} : Updates an existing userProfiles.
     *
     * @param userProfilesDTO the userProfilesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userProfilesDTO,
     * or with status {@code 400 (Bad Request)} if the userProfilesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userProfilesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-profiles")
    public ResponseEntity<UserProfilesDTO> updateUserProfiles(@Valid @RequestBody UserProfilesDTO userProfilesDTO) throws URISyntaxException {
        log.debug("REST request to update UserProfiles : {}", userProfilesDTO);
        if (userProfilesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserProfilesDTO result = userProfilesService.save(userProfilesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userProfilesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-profiles} : get all the userProfiles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userProfiles in body.
     */
    @GetMapping("/user-profiles")
    public ResponseEntity<List<UserProfilesDTO>> getAllUserProfiles(Pageable pageable) {
        log.debug("REST request to get a page of UserProfiles");
        Page<UserProfilesDTO> page = userProfilesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-profiles/:id} : get the "id" userProfiles.
     *
     * @param id the id of the userProfilesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userProfilesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-profiles/{id}")
    public ResponseEntity<UserProfilesDTO> getUserProfiles(@PathVariable Long id) {
        log.debug("REST request to get UserProfiles : {}", id);
        Optional<UserProfilesDTO> userProfilesDTO = userProfilesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userProfilesDTO);
    }

    /**
     * {@code DELETE  /user-profiles/:id} : delete the "id" userProfiles.
     *
     * @param id the id of the userProfilesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-profiles/{id}")
    public ResponseEntity<Void> deleteUserProfiles(@PathVariable Long id) {
        log.debug("REST request to delete UserProfiles : {}", id);
        userProfilesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
