/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.UserHistoriesService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.UserHistoriesDTO;

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
 * REST controller for managing {@link com.infy.domain.UserHistories}.
 */
@RestController
@RequestMapping("/api")
public class UserHistoriesResource {

    private final Logger log = LoggerFactory.getLogger(UserHistoriesResource.class);

    private static final String ENTITY_NAME = "userHistories";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserHistoriesService userHistoriesService;

    public UserHistoriesResource(UserHistoriesService userHistoriesService) {
        this.userHistoriesService = userHistoriesService;
    }

    /**
     * {@code POST  /user-histories} : Create a new userHistories.
     *
     * @param userHistoriesDTO the userHistoriesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userHistoriesDTO, or with status {@code 400 (Bad Request)} if the userHistories has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-histories")
    public ResponseEntity<UserHistoriesDTO> createUserHistories(@Valid @RequestBody UserHistoriesDTO userHistoriesDTO) throws URISyntaxException {
        log.debug("REST request to save UserHistories : {}", userHistoriesDTO);
        if (userHistoriesDTO.getId() != null) {
            throw new BadRequestAlertException("A new userHistories cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserHistoriesDTO result = userHistoriesService.save(userHistoriesDTO);
        return ResponseEntity.created(new URI("/api/user-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-histories} : Updates an existing userHistories.
     *
     * @param userHistoriesDTO the userHistoriesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userHistoriesDTO,
     * or with status {@code 400 (Bad Request)} if the userHistoriesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userHistoriesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-histories")
    public ResponseEntity<UserHistoriesDTO> updateUserHistories(@Valid @RequestBody UserHistoriesDTO userHistoriesDTO) throws URISyntaxException {
        log.debug("REST request to update UserHistories : {}", userHistoriesDTO);
        if (userHistoriesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserHistoriesDTO result = userHistoriesService.save(userHistoriesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userHistoriesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-histories} : get all the userHistories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userHistories in body.
     */
    @GetMapping("/user-histories")
    public ResponseEntity<List<UserHistoriesDTO>> getAllUserHistories(Pageable pageable) {
        log.debug("REST request to get a page of UserHistories");
        Page<UserHistoriesDTO> page = userHistoriesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-histories/:id} : get the "id" userHistories.
     *
     * @param id the id of the userHistoriesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userHistoriesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-histories/{id}")
    public ResponseEntity<UserHistoriesDTO> getUserHistories(@PathVariable Long id) {
        log.debug("REST request to get UserHistories : {}", id);
        Optional<UserHistoriesDTO> userHistoriesDTO = userHistoriesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userHistoriesDTO);
    }

    /**
     * {@code DELETE  /user-histories/:id} : delete the "id" userHistories.
     *
     * @param id the id of the userHistoriesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-histories/{id}")
    public ResponseEntity<Void> deleteUserHistories(@PathVariable Long id) {
        log.debug("REST request to delete UserHistories : {}", id);
        userHistoriesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
