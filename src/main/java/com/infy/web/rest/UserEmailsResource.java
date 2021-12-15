/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.UserEmailsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.UserEmailsDTO;

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
 * REST controller for managing {@link com.infy.domain.UserEmails}.
 */
@RestController
@RequestMapping("/api")
public class UserEmailsResource {

    private final Logger log = LoggerFactory.getLogger(UserEmailsResource.class);

    private static final String ENTITY_NAME = "userEmails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserEmailsService userEmailsService;

    public UserEmailsResource(UserEmailsService userEmailsService) {
        this.userEmailsService = userEmailsService;
    }

    /**
     * {@code POST  /user-emails} : Create a new userEmails.
     *
     * @param userEmailsDTO the userEmailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userEmailsDTO, or with status {@code 400 (Bad Request)} if the userEmails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-emails")
    public ResponseEntity<UserEmailsDTO> createUserEmails(@Valid @RequestBody UserEmailsDTO userEmailsDTO) throws URISyntaxException {
        log.debug("REST request to save UserEmails : {}", userEmailsDTO);
        if (userEmailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new userEmails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserEmailsDTO result = userEmailsService.save(userEmailsDTO);
        return ResponseEntity.created(new URI("/api/user-emails/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-emails} : Updates an existing userEmails.
     *
     * @param userEmailsDTO the userEmailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userEmailsDTO,
     * or with status {@code 400 (Bad Request)} if the userEmailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userEmailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-emails")
    public ResponseEntity<UserEmailsDTO> updateUserEmails(@Valid @RequestBody UserEmailsDTO userEmailsDTO) throws URISyntaxException {
        log.debug("REST request to update UserEmails : {}", userEmailsDTO);
        if (userEmailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserEmailsDTO result = userEmailsService.save(userEmailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userEmailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-emails} : get all the userEmails.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userEmails in body.
     */
    @GetMapping("/user-emails")
    public ResponseEntity<List<UserEmailsDTO>> getAllUserEmails(Pageable pageable) {
        log.debug("REST request to get a page of UserEmails");
        Page<UserEmailsDTO> page = userEmailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-emails/:id} : get the "id" userEmails.
     *
     * @param id the id of the userEmailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userEmailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-emails/{id}")
    public ResponseEntity<UserEmailsDTO> getUserEmails(@PathVariable Long id) {
        log.debug("REST request to get UserEmails : {}", id);
        Optional<UserEmailsDTO> userEmailsDTO = userEmailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userEmailsDTO);
    }

    /**
     * {@code DELETE  /user-emails/:id} : delete the "id" userEmails.
     *
     * @param id the id of the userEmailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-emails/{id}")
    public ResponseEntity<Void> deleteUserEmails(@PathVariable Long id) {
        log.debug("REST request to delete UserEmails : {}", id);
        userEmailsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
