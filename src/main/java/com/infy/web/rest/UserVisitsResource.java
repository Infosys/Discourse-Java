/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.UserVisitsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.UserVisitsDTO;

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
 * REST controller for managing {@link com.infy.domain.UserVisits}.
 */
@RestController
@RequestMapping("/api")
public class UserVisitsResource {

    private final Logger log = LoggerFactory.getLogger(UserVisitsResource.class);

    private static final String ENTITY_NAME = "userVisits";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserVisitsService userVisitsService;

    public UserVisitsResource(UserVisitsService userVisitsService) {
        this.userVisitsService = userVisitsService;
    }

    /**
     * {@code POST  /user-visits} : Create a new userVisits.
     *
     * @param userVisitsDTO the userVisitsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userVisitsDTO, or with status {@code 400 (Bad Request)} if the userVisits has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-visits")
    public ResponseEntity<UserVisitsDTO> createUserVisits(@Valid @RequestBody UserVisitsDTO userVisitsDTO) throws URISyntaxException {
        log.debug("REST request to save UserVisits : {}", userVisitsDTO);
        if (userVisitsDTO.getId() != null) {
            throw new BadRequestAlertException("A new userVisits cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserVisitsDTO result = userVisitsService.save(userVisitsDTO);
        return ResponseEntity.created(new URI("/api/user-visits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-visits} : Updates an existing userVisits.
     *
     * @param userVisitsDTO the userVisitsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userVisitsDTO,
     * or with status {@code 400 (Bad Request)} if the userVisitsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userVisitsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-visits")
    public ResponseEntity<UserVisitsDTO> updateUserVisits(@Valid @RequestBody UserVisitsDTO userVisitsDTO) throws URISyntaxException {
        log.debug("REST request to update UserVisits : {}", userVisitsDTO);
        if (userVisitsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserVisitsDTO result = userVisitsService.save(userVisitsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userVisitsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-visits} : get all the userVisits.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userVisits in body.
     */
    @GetMapping("/user-visits")
    public ResponseEntity<List<UserVisitsDTO>> getAllUserVisits(Pageable pageable) {
        log.debug("REST request to get a page of UserVisits");
        Page<UserVisitsDTO> page = userVisitsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-visits/:id} : get the "id" userVisits.
     *
     * @param id the id of the userVisitsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userVisitsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-visits/{id}")
    public ResponseEntity<UserVisitsDTO> getUserVisits(@PathVariable Long id) {
        log.debug("REST request to get UserVisits : {}", id);
        Optional<UserVisitsDTO> userVisitsDTO = userVisitsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userVisitsDTO);
    }

    /**
     * {@code DELETE  /user-visits/:id} : delete the "id" userVisits.
     *
     * @param id the id of the userVisitsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-visits/{id}")
    public ResponseEntity<Void> deleteUserVisits(@PathVariable Long id) {
        log.debug("REST request to delete UserVisits : {}", id);
        userVisitsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
