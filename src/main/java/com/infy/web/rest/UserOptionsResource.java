/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.UserOptionsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.UserOptionsDTO;

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
 * REST controller for managing {@link com.infy.domain.UserOptions}.
 */
@RestController
@RequestMapping("/api")
public class UserOptionsResource {

    private final Logger log = LoggerFactory.getLogger(UserOptionsResource.class);

    private static final String ENTITY_NAME = "userOptions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserOptionsService userOptionsService;

    public UserOptionsResource(UserOptionsService userOptionsService) {
        this.userOptionsService = userOptionsService;
    }

    /**
     * {@code POST  /user-options} : Create a new userOptions.
     *
     * @param userOptionsDTO the userOptionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userOptionsDTO, or with status {@code 400 (Bad Request)} if the userOptions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-options")
    public ResponseEntity<UserOptionsDTO> createUserOptions(@Valid @RequestBody UserOptionsDTO userOptionsDTO) throws URISyntaxException {
        log.debug("REST request to save UserOptions : {}", userOptionsDTO);
        if (userOptionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new userOptions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserOptionsDTO result = userOptionsService.save(userOptionsDTO);
        return ResponseEntity.created(new URI("/api/user-options/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-options} : Updates an existing userOptions.
     *
     * @param userOptionsDTO the userOptionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userOptionsDTO,
     * or with status {@code 400 (Bad Request)} if the userOptionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userOptionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-options")
    public ResponseEntity<UserOptionsDTO> updateUserOptions(@Valid @RequestBody UserOptionsDTO userOptionsDTO) throws URISyntaxException {
        log.debug("REST request to update UserOptions : {}", userOptionsDTO);
        if (userOptionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserOptionsDTO result = userOptionsService.save(userOptionsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userOptionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-options} : get all the userOptions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userOptions in body.
     */
    @GetMapping("/user-options")
    public ResponseEntity<List<UserOptionsDTO>> getAllUserOptions(Pageable pageable) {
        log.debug("REST request to get a page of UserOptions");
        Page<UserOptionsDTO> page = userOptionsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-options/:id} : get the "id" userOptions.
     *
     * @param id the id of the userOptionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userOptionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-options/{id}")
    public ResponseEntity<UserOptionsDTO> getUserOptions(@PathVariable Long id) {
        log.debug("REST request to get UserOptions : {}", id);
        Optional<UserOptionsDTO> userOptionsDTO = userOptionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userOptionsDTO);
    }

    /**
     * {@code DELETE  /user-options/:id} : delete the "id" userOptions.
     *
     * @param id the id of the userOptionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-options/{id}")
    public ResponseEntity<Void> deleteUserOptions(@PathVariable Long id) {
        log.debug("REST request to delete UserOptions : {}", id);
        userOptionsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
