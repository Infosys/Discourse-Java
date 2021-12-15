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

import com.infy.service.UserActionsService;
import com.infy.service.dto.UserActionsDTO;
import com.infy.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.infy.domain.UserActions}.
 */
@RestController
@RequestMapping("/api")
public class UserActionsResource {

    private final Logger log = LoggerFactory.getLogger(UserActionsResource.class);

    private static final String ENTITY_NAME = "userActions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserActionsService userActionsService;

    public UserActionsResource(UserActionsService userActionsService) {
        this.userActionsService = userActionsService;
    }

    /**
     * {@code POST  /user-actions} : Create a new userActions.
     *
     * @param userActionsDTO the userActionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userActionsDTO, or with status {@code 400 (Bad Request)} if the userActions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-actions")
    public ResponseEntity<UserActionsDTO> createUserActions(@Valid @RequestBody UserActionsDTO userActionsDTO) throws URISyntaxException {
        log.debug("REST request to save UserActions : {}", userActionsDTO);
        if (userActionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new userActions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserActionsDTO result = userActionsService.save(userActionsDTO);
        return ResponseEntity.created(new URI("/api/user-actions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-actions} : Updates an existing userActions.
     *
     * @param userActionsDTO the userActionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userActionsDTO,
     * or with status {@code 400 (Bad Request)} if the userActionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userActionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-actions")
    public ResponseEntity<UserActionsDTO> updateUserActions(@Valid @RequestBody UserActionsDTO userActionsDTO) throws URISyntaxException {
        log.debug("REST request to update UserActions : {}", userActionsDTO);
        if (userActionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserActionsDTO result = userActionsService.save(userActionsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userActionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-actions} : get all the userActions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userActions in body.
     */
    @GetMapping("/user-actions")
    public ResponseEntity<List<UserActionsDTO>> getAllUserActions(Pageable pageable) {
        log.debug("REST request to get a page of UserActions");
        Page<UserActionsDTO> page = userActionsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-actions/:id} : get the "id" userActions.
     *
     * @param id the id of the userActionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userActionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-actions/{id}")
    public ResponseEntity<UserActionsDTO> getUserActions(@PathVariable Long id) {
        log.debug("REST request to get UserActions : {}", id);
        Optional<UserActionsDTO> userActionsDTO = userActionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userActionsDTO);
    }

    /**
     * {@code DELETE  /user-actions/:id} : delete the "id" userActions.
     *
     * @param id the id of the userActionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-actions/{id}")
    public ResponseEntity<Void> deleteUserActions(@PathVariable Long id) {
        log.debug("REST request to delete UserActions : {}", id);
        userActionsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
