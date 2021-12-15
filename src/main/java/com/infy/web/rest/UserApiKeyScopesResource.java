/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.UserApiKeyScopesService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.UserApiKeyScopesDTO;

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
 * REST controller for managing {@link com.infy.domain.UserApiKeyScopes}.
 */
@RestController
@RequestMapping("/api")
public class UserApiKeyScopesResource {

    private final Logger log = LoggerFactory.getLogger(UserApiKeyScopesResource.class);

    private static final String ENTITY_NAME = "userApiKeyScopes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserApiKeyScopesService userApiKeyScopesService;

    public UserApiKeyScopesResource(UserApiKeyScopesService userApiKeyScopesService) {
        this.userApiKeyScopesService = userApiKeyScopesService;
    }

    /**
     * {@code POST  /user-api-key-scopes} : Create a new userApiKeyScopes.
     *
     * @param userApiKeyScopesDTO the userApiKeyScopesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userApiKeyScopesDTO, or with status {@code 400 (Bad Request)} if the userApiKeyScopes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-api-key-scopes")
    public ResponseEntity<UserApiKeyScopesDTO> createUserApiKeyScopes(@Valid @RequestBody UserApiKeyScopesDTO userApiKeyScopesDTO) throws URISyntaxException {
        log.debug("REST request to save UserApiKeyScopes : {}", userApiKeyScopesDTO);
        if (userApiKeyScopesDTO.getId() != null) {
            throw new BadRequestAlertException("A new userApiKeyScopes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserApiKeyScopesDTO result = userApiKeyScopesService.save(userApiKeyScopesDTO);
        return ResponseEntity.created(new URI("/api/user-api-key-scopes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-api-key-scopes} : Updates an existing userApiKeyScopes.
     *
     * @param userApiKeyScopesDTO the userApiKeyScopesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userApiKeyScopesDTO,
     * or with status {@code 400 (Bad Request)} if the userApiKeyScopesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userApiKeyScopesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-api-key-scopes")
    public ResponseEntity<UserApiKeyScopesDTO> updateUserApiKeyScopes(@Valid @RequestBody UserApiKeyScopesDTO userApiKeyScopesDTO) throws URISyntaxException {
        log.debug("REST request to update UserApiKeyScopes : {}", userApiKeyScopesDTO);
        if (userApiKeyScopesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserApiKeyScopesDTO result = userApiKeyScopesService.save(userApiKeyScopesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userApiKeyScopesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-api-key-scopes} : get all the userApiKeyScopes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userApiKeyScopes in body.
     */
    @GetMapping("/user-api-key-scopes")
    public ResponseEntity<List<UserApiKeyScopesDTO>> getAllUserApiKeyScopes(Pageable pageable) {
        log.debug("REST request to get a page of UserApiKeyScopes");
        Page<UserApiKeyScopesDTO> page = userApiKeyScopesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-api-key-scopes/:id} : get the "id" userApiKeyScopes.
     *
     * @param id the id of the userApiKeyScopesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userApiKeyScopesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-api-key-scopes/{id}")
    public ResponseEntity<UserApiKeyScopesDTO> getUserApiKeyScopes(@PathVariable Long id) {
        log.debug("REST request to get UserApiKeyScopes : {}", id);
        Optional<UserApiKeyScopesDTO> userApiKeyScopesDTO = userApiKeyScopesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userApiKeyScopesDTO);
    }

    /**
     * {@code DELETE  /user-api-key-scopes/:id} : delete the "id" userApiKeyScopes.
     *
     * @param id the id of the userApiKeyScopesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-api-key-scopes/{id}")
    public ResponseEntity<Void> deleteUserApiKeyScopes(@PathVariable Long id) {
        log.debug("REST request to delete UserApiKeyScopes : {}", id);
        userApiKeyScopesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
