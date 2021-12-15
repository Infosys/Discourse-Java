/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.UserApiKeysService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.UserApiKeysDTO;

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
 * REST controller for managing {@link com.infy.domain.UserApiKeys}.
 */
@RestController
@RequestMapping("/api")
public class UserApiKeysResource {

    private final Logger log = LoggerFactory.getLogger(UserApiKeysResource.class);

    private static final String ENTITY_NAME = "userApiKeys";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserApiKeysService userApiKeysService;

    public UserApiKeysResource(UserApiKeysService userApiKeysService) {
        this.userApiKeysService = userApiKeysService;
    }

    /**
     * {@code POST  /user-api-keys} : Create a new userApiKeys.
     *
     * @param userApiKeysDTO the userApiKeysDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userApiKeysDTO, or with status {@code 400 (Bad Request)} if the userApiKeys has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-api-keys")
    public ResponseEntity<UserApiKeysDTO> createUserApiKeys(@Valid @RequestBody UserApiKeysDTO userApiKeysDTO) throws URISyntaxException {
        log.debug("REST request to save UserApiKeys : {}", userApiKeysDTO);
        if (userApiKeysDTO.getId() != null) {
            throw new BadRequestAlertException("A new userApiKeys cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserApiKeysDTO result = userApiKeysService.save(userApiKeysDTO);
        return ResponseEntity.created(new URI("/api/user-api-keys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-api-keys} : Updates an existing userApiKeys.
     *
     * @param userApiKeysDTO the userApiKeysDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userApiKeysDTO,
     * or with status {@code 400 (Bad Request)} if the userApiKeysDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userApiKeysDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-api-keys")
    public ResponseEntity<UserApiKeysDTO> updateUserApiKeys(@Valid @RequestBody UserApiKeysDTO userApiKeysDTO) throws URISyntaxException {
        log.debug("REST request to update UserApiKeys : {}", userApiKeysDTO);
        if (userApiKeysDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserApiKeysDTO result = userApiKeysService.save(userApiKeysDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userApiKeysDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-api-keys} : get all the userApiKeys.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userApiKeys in body.
     */
    @GetMapping("/user-api-keys")
    public ResponseEntity<List<UserApiKeysDTO>> getAllUserApiKeys(Pageable pageable) {
        log.debug("REST request to get a page of UserApiKeys");
        Page<UserApiKeysDTO> page = userApiKeysService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-api-keys/:id} : get the "id" userApiKeys.
     *
     * @param id the id of the userApiKeysDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userApiKeysDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-api-keys/{id}")
    public ResponseEntity<UserApiKeysDTO> getUserApiKeys(@PathVariable Long id) {
        log.debug("REST request to get UserApiKeys : {}", id);
        Optional<UserApiKeysDTO> userApiKeysDTO = userApiKeysService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userApiKeysDTO);
    }

    /**
     * {@code DELETE  /user-api-keys/:id} : delete the "id" userApiKeys.
     *
     * @param id the id of the userApiKeysDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-api-keys/{id}")
    public ResponseEntity<Void> deleteUserApiKeys(@PathVariable Long id) {
        log.debug("REST request to delete UserApiKeys : {}", id);
        userApiKeysService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
