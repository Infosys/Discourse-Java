/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.UserFieldOptionsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.UserFieldOptionsDTO;

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
 * REST controller for managing {@link com.infy.domain.UserFieldOptions}.
 */
@RestController
@RequestMapping("/api")
public class UserFieldOptionsResource {

    private final Logger log = LoggerFactory.getLogger(UserFieldOptionsResource.class);

    private static final String ENTITY_NAME = "userFieldOptions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserFieldOptionsService userFieldOptionsService;

    public UserFieldOptionsResource(UserFieldOptionsService userFieldOptionsService) {
        this.userFieldOptionsService = userFieldOptionsService;
    }

    /**
     * {@code POST  /user-field-options} : Create a new userFieldOptions.
     *
     * @param userFieldOptionsDTO the userFieldOptionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userFieldOptionsDTO, or with status {@code 400 (Bad Request)} if the userFieldOptions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-field-options")
    public ResponseEntity<UserFieldOptionsDTO> createUserFieldOptions(@Valid @RequestBody UserFieldOptionsDTO userFieldOptionsDTO) throws URISyntaxException {
        log.debug("REST request to save UserFieldOptions : {}", userFieldOptionsDTO);
        if (userFieldOptionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new userFieldOptions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserFieldOptionsDTO result = userFieldOptionsService.save(userFieldOptionsDTO);
        return ResponseEntity.created(new URI("/api/user-field-options/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-field-options} : Updates an existing userFieldOptions.
     *
     * @param userFieldOptionsDTO the userFieldOptionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userFieldOptionsDTO,
     * or with status {@code 400 (Bad Request)} if the userFieldOptionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userFieldOptionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-field-options")
    public ResponseEntity<UserFieldOptionsDTO> updateUserFieldOptions(@Valid @RequestBody UserFieldOptionsDTO userFieldOptionsDTO) throws URISyntaxException {
        log.debug("REST request to update UserFieldOptions : {}", userFieldOptionsDTO);
        if (userFieldOptionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserFieldOptionsDTO result = userFieldOptionsService.save(userFieldOptionsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userFieldOptionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-field-options} : get all the userFieldOptions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userFieldOptions in body.
     */
    @GetMapping("/user-field-options")
    public ResponseEntity<List<UserFieldOptionsDTO>> getAllUserFieldOptions(Pageable pageable) {
        log.debug("REST request to get a page of UserFieldOptions");
        Page<UserFieldOptionsDTO> page = userFieldOptionsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-field-options/:id} : get the "id" userFieldOptions.
     *
     * @param id the id of the userFieldOptionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userFieldOptionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-field-options/{id}")
    public ResponseEntity<UserFieldOptionsDTO> getUserFieldOptions(@PathVariable Long id) {
        log.debug("REST request to get UserFieldOptions : {}", id);
        Optional<UserFieldOptionsDTO> userFieldOptionsDTO = userFieldOptionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userFieldOptionsDTO);
    }

    /**
     * {@code DELETE  /user-field-options/:id} : delete the "id" userFieldOptions.
     *
     * @param id the id of the userFieldOptionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-field-options/{id}")
    public ResponseEntity<Void> deleteUserFieldOptions(@PathVariable Long id) {
        log.debug("REST request to delete UserFieldOptions : {}", id);
        userFieldOptionsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
