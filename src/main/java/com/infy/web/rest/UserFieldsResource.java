/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.UserFieldsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.UserFieldsDTO;

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
 * REST controller for managing {@link com.infy.domain.UserFields}.
 */
@RestController
@RequestMapping("/api")
public class UserFieldsResource {

    private final Logger log = LoggerFactory.getLogger(UserFieldsResource.class);

    private static final String ENTITY_NAME = "userFields";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserFieldsService userFieldsService;

    public UserFieldsResource(UserFieldsService userFieldsService) {
        this.userFieldsService = userFieldsService;
    }

    /**
     * {@code POST  /user-fields} : Create a new userFields.
     *
     * @param userFieldsDTO the userFieldsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userFieldsDTO, or with status {@code 400 (Bad Request)} if the userFields has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-fields")
    public ResponseEntity<UserFieldsDTO> createUserFields(@Valid @RequestBody UserFieldsDTO userFieldsDTO) throws URISyntaxException {
        log.debug("REST request to save UserFields : {}", userFieldsDTO);
        if (userFieldsDTO.getId() != null) {
            throw new BadRequestAlertException("A new userFields cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserFieldsDTO result = userFieldsService.save(userFieldsDTO);
        return ResponseEntity.created(new URI("/api/user-fields/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-fields} : Updates an existing userFields.
     *
     * @param userFieldsDTO the userFieldsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userFieldsDTO,
     * or with status {@code 400 (Bad Request)} if the userFieldsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userFieldsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-fields")
    public ResponseEntity<UserFieldsDTO> updateUserFields(@Valid @RequestBody UserFieldsDTO userFieldsDTO) throws URISyntaxException {
        log.debug("REST request to update UserFields : {}", userFieldsDTO);
        if (userFieldsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserFieldsDTO result = userFieldsService.save(userFieldsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userFieldsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-fields} : get all the userFields.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userFields in body.
     */
    @GetMapping("/user-fields")
    public ResponseEntity<List<UserFieldsDTO>> getAllUserFields(Pageable pageable) {
        log.debug("REST request to get a page of UserFields");
        Page<UserFieldsDTO> page = userFieldsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-fields/:id} : get the "id" userFields.
     *
     * @param id the id of the userFieldsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userFieldsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-fields/{id}")
    public ResponseEntity<UserFieldsDTO> getUserFields(@PathVariable Long id) {
        log.debug("REST request to get UserFields : {}", id);
        Optional<UserFieldsDTO> userFieldsDTO = userFieldsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userFieldsDTO);
    }

    /**
     * {@code DELETE  /user-fields/:id} : delete the "id" userFields.
     *
     * @param id the id of the userFieldsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-fields/{id}")
    public ResponseEntity<Void> deleteUserFields(@PathVariable Long id) {
        log.debug("REST request to delete UserFields : {}", id);
        userFieldsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
