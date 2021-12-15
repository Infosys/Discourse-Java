/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.UserWarningsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.UserWarningsDTO;

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
 * REST controller for managing {@link com.infy.domain.UserWarnings}.
 */
@RestController
@RequestMapping("/api")
public class UserWarningsResource {

    private final Logger log = LoggerFactory.getLogger(UserWarningsResource.class);

    private static final String ENTITY_NAME = "userWarnings";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserWarningsService userWarningsService;

    public UserWarningsResource(UserWarningsService userWarningsService) {
        this.userWarningsService = userWarningsService;
    }

    /**
     * {@code POST  /user-warnings} : Create a new userWarnings.
     *
     * @param userWarningsDTO the userWarningsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userWarningsDTO, or with status {@code 400 (Bad Request)} if the userWarnings has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-warnings")
    public ResponseEntity<UserWarningsDTO> createUserWarnings(@Valid @RequestBody UserWarningsDTO userWarningsDTO) throws URISyntaxException {
        log.debug("REST request to save UserWarnings : {}", userWarningsDTO);
        if (userWarningsDTO.getId() != null) {
            throw new BadRequestAlertException("A new userWarnings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserWarningsDTO result = userWarningsService.save(userWarningsDTO);
        return ResponseEntity.created(new URI("/api/user-warnings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-warnings} : Updates an existing userWarnings.
     *
     * @param userWarningsDTO the userWarningsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userWarningsDTO,
     * or with status {@code 400 (Bad Request)} if the userWarningsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userWarningsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-warnings")
    public ResponseEntity<UserWarningsDTO> updateUserWarnings(@Valid @RequestBody UserWarningsDTO userWarningsDTO) throws URISyntaxException {
        log.debug("REST request to update UserWarnings : {}", userWarningsDTO);
        if (userWarningsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserWarningsDTO result = userWarningsService.save(userWarningsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userWarningsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-warnings} : get all the userWarnings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userWarnings in body.
     */
    @GetMapping("/user-warnings")
    public ResponseEntity<List<UserWarningsDTO>> getAllUserWarnings(Pageable pageable) {
        log.debug("REST request to get a page of UserWarnings");
        Page<UserWarningsDTO> page = userWarningsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-warnings/:id} : get the "id" userWarnings.
     *
     * @param id the id of the userWarningsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userWarningsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-warnings/{id}")
    public ResponseEntity<UserWarningsDTO> getUserWarnings(@PathVariable Long id) {
        log.debug("REST request to get UserWarnings : {}", id);
        Optional<UserWarningsDTO> userWarningsDTO = userWarningsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userWarningsDTO);
    }

    /**
     * {@code DELETE  /user-warnings/:id} : delete the "id" userWarnings.
     *
     * @param id the id of the userWarningsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-warnings/{id}")
    public ResponseEntity<Void> deleteUserWarnings(@PathVariable Long id) {
        log.debug("REST request to delete UserWarnings : {}", id);
        userWarningsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
