/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.UserCustomFieldsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.UserCustomFieldsDTO;

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
 * REST controller for managing {@link com.infy.domain.UserCustomFields}.
 */
@RestController
@RequestMapping("/api")
public class UserCustomFieldsResource {

    private final Logger log = LoggerFactory.getLogger(UserCustomFieldsResource.class);

    private static final String ENTITY_NAME = "userCustomFields";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserCustomFieldsService userCustomFieldsService;

    public UserCustomFieldsResource(UserCustomFieldsService userCustomFieldsService) {
        this.userCustomFieldsService = userCustomFieldsService;
    }

    /**
     * {@code POST  /user-custom-fields} : Create a new userCustomFields.
     *
     * @param userCustomFieldsDTO the userCustomFieldsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userCustomFieldsDTO, or with status {@code 400 (Bad Request)} if the userCustomFields has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-custom-fields")
    public ResponseEntity<UserCustomFieldsDTO> createUserCustomFields(@Valid @RequestBody UserCustomFieldsDTO userCustomFieldsDTO) throws URISyntaxException {
        log.debug("REST request to save UserCustomFields : {}", userCustomFieldsDTO);
        if (userCustomFieldsDTO.getId() != null) {
            throw new BadRequestAlertException("A new userCustomFields cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserCustomFieldsDTO result = userCustomFieldsService.save(userCustomFieldsDTO);
        return ResponseEntity.created(new URI("/api/user-custom-fields/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-custom-fields} : Updates an existing userCustomFields.
     *
     * @param userCustomFieldsDTO the userCustomFieldsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userCustomFieldsDTO,
     * or with status {@code 400 (Bad Request)} if the userCustomFieldsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userCustomFieldsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-custom-fields")
    public ResponseEntity<UserCustomFieldsDTO> updateUserCustomFields(@Valid @RequestBody UserCustomFieldsDTO userCustomFieldsDTO) throws URISyntaxException {
        log.debug("REST request to update UserCustomFields : {}", userCustomFieldsDTO);
        if (userCustomFieldsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserCustomFieldsDTO result = userCustomFieldsService.save(userCustomFieldsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userCustomFieldsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-custom-fields} : get all the userCustomFields.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userCustomFields in body.
     */
    @GetMapping("/user-custom-fields")
    public ResponseEntity<List<UserCustomFieldsDTO>> getAllUserCustomFields(Pageable pageable) {
        log.debug("REST request to get a page of UserCustomFields");
        Page<UserCustomFieldsDTO> page = userCustomFieldsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-custom-fields/:id} : get the "id" userCustomFields.
     *
     * @param id the id of the userCustomFieldsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userCustomFieldsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-custom-fields/{id}")
    public ResponseEntity<UserCustomFieldsDTO> getUserCustomFields(@PathVariable Long id) {
        log.debug("REST request to get UserCustomFields : {}", id);
        Optional<UserCustomFieldsDTO> userCustomFieldsDTO = userCustomFieldsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userCustomFieldsDTO);
    }

    /**
     * {@code DELETE  /user-custom-fields/:id} : delete the "id" userCustomFields.
     *
     * @param id the id of the userCustomFieldsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-custom-fields/{id}")
    public ResponseEntity<Void> deleteUserCustomFields(@PathVariable Long id) {
        log.debug("REST request to delete UserCustomFields : {}", id);
        userCustomFieldsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
