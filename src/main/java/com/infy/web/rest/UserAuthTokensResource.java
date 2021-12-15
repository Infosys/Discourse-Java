/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.UserAuthTokensService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.UserAuthTokensDTO;

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
 * REST controller for managing {@link com.infy.domain.UserAuthTokens}.
 */
@RestController
@RequestMapping("/api")
public class UserAuthTokensResource {

    private final Logger log = LoggerFactory.getLogger(UserAuthTokensResource.class);

    private static final String ENTITY_NAME = "userAuthTokens";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserAuthTokensService userAuthTokensService;

    public UserAuthTokensResource(UserAuthTokensService userAuthTokensService) {
        this.userAuthTokensService = userAuthTokensService;
    }

    /**
     * {@code POST  /user-auth-tokens} : Create a new userAuthTokens.
     *
     * @param userAuthTokensDTO the userAuthTokensDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userAuthTokensDTO, or with status {@code 400 (Bad Request)} if the userAuthTokens has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-auth-tokens")
    public ResponseEntity<UserAuthTokensDTO> createUserAuthTokens(@Valid @RequestBody UserAuthTokensDTO userAuthTokensDTO) throws URISyntaxException {
        log.debug("REST request to save UserAuthTokens : {}", userAuthTokensDTO);
        if (userAuthTokensDTO.getId() != null) {
            throw new BadRequestAlertException("A new userAuthTokens cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserAuthTokensDTO result = userAuthTokensService.save(userAuthTokensDTO);
        return ResponseEntity.created(new URI("/api/user-auth-tokens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-auth-tokens} : Updates an existing userAuthTokens.
     *
     * @param userAuthTokensDTO the userAuthTokensDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userAuthTokensDTO,
     * or with status {@code 400 (Bad Request)} if the userAuthTokensDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userAuthTokensDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-auth-tokens")
    public ResponseEntity<UserAuthTokensDTO> updateUserAuthTokens(@Valid @RequestBody UserAuthTokensDTO userAuthTokensDTO) throws URISyntaxException {
        log.debug("REST request to update UserAuthTokens : {}", userAuthTokensDTO);
        if (userAuthTokensDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserAuthTokensDTO result = userAuthTokensService.save(userAuthTokensDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userAuthTokensDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-auth-tokens} : get all the userAuthTokens.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userAuthTokens in body.
     */
    @GetMapping("/user-auth-tokens")
    public ResponseEntity<List<UserAuthTokensDTO>> getAllUserAuthTokens(Pageable pageable) {
        log.debug("REST request to get a page of UserAuthTokens");
        Page<UserAuthTokensDTO> page = userAuthTokensService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-auth-tokens/:id} : get the "id" userAuthTokens.
     *
     * @param id the id of the userAuthTokensDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userAuthTokensDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-auth-tokens/{id}")
    public ResponseEntity<UserAuthTokensDTO> getUserAuthTokens(@PathVariable Long id) {
        log.debug("REST request to get UserAuthTokens : {}", id);
        Optional<UserAuthTokensDTO> userAuthTokensDTO = userAuthTokensService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userAuthTokensDTO);
    }

    /**
     * {@code DELETE  /user-auth-tokens/:id} : delete the "id" userAuthTokens.
     *
     * @param id the id of the userAuthTokensDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-auth-tokens/{id}")
    public ResponseEntity<Void> deleteUserAuthTokens(@PathVariable Long id) {
        log.debug("REST request to delete UserAuthTokens : {}", id);
        userAuthTokensService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
