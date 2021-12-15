/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.UserAssociatedAccountsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.UserAssociatedAccountsDTO;

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
 * REST controller for managing {@link com.infy.domain.UserAssociatedAccounts}.
 */
@RestController
@RequestMapping("/api")
public class UserAssociatedAccountsResource {

    private final Logger log = LoggerFactory.getLogger(UserAssociatedAccountsResource.class);

    private static final String ENTITY_NAME = "userAssociatedAccounts";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserAssociatedAccountsService userAssociatedAccountsService;

    public UserAssociatedAccountsResource(UserAssociatedAccountsService userAssociatedAccountsService) {
        this.userAssociatedAccountsService = userAssociatedAccountsService;
    }

    /**
     * {@code POST  /user-associated-accounts} : Create a new userAssociatedAccounts.
     *
     * @param userAssociatedAccountsDTO the userAssociatedAccountsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userAssociatedAccountsDTO, or with status {@code 400 (Bad Request)} if the userAssociatedAccounts has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-associated-accounts")
    public ResponseEntity<UserAssociatedAccountsDTO> createUserAssociatedAccounts(@Valid @RequestBody UserAssociatedAccountsDTO userAssociatedAccountsDTO) throws URISyntaxException {
        log.debug("REST request to save UserAssociatedAccounts : {}", userAssociatedAccountsDTO);
        if (userAssociatedAccountsDTO.getId() != null) {
            throw new BadRequestAlertException("A new userAssociatedAccounts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserAssociatedAccountsDTO result = userAssociatedAccountsService.save(userAssociatedAccountsDTO);
        return ResponseEntity.created(new URI("/api/user-associated-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-associated-accounts} : Updates an existing userAssociatedAccounts.
     *
     * @param userAssociatedAccountsDTO the userAssociatedAccountsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userAssociatedAccountsDTO,
     * or with status {@code 400 (Bad Request)} if the userAssociatedAccountsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userAssociatedAccountsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-associated-accounts")
    public ResponseEntity<UserAssociatedAccountsDTO> updateUserAssociatedAccounts(@Valid @RequestBody UserAssociatedAccountsDTO userAssociatedAccountsDTO) throws URISyntaxException {
        log.debug("REST request to update UserAssociatedAccounts : {}", userAssociatedAccountsDTO);
        if (userAssociatedAccountsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserAssociatedAccountsDTO result = userAssociatedAccountsService.save(userAssociatedAccountsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userAssociatedAccountsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-associated-accounts} : get all the userAssociatedAccounts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userAssociatedAccounts in body.
     */
    @GetMapping("/user-associated-accounts")
    public ResponseEntity<List<UserAssociatedAccountsDTO>> getAllUserAssociatedAccounts(Pageable pageable) {
        log.debug("REST request to get a page of UserAssociatedAccounts");
        Page<UserAssociatedAccountsDTO> page = userAssociatedAccountsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-associated-accounts/:id} : get the "id" userAssociatedAccounts.
     *
     * @param id the id of the userAssociatedAccountsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userAssociatedAccountsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-associated-accounts/{id}")
    public ResponseEntity<UserAssociatedAccountsDTO> getUserAssociatedAccounts(@PathVariable Long id) {
        log.debug("REST request to get UserAssociatedAccounts : {}", id);
        Optional<UserAssociatedAccountsDTO> userAssociatedAccountsDTO = userAssociatedAccountsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userAssociatedAccountsDTO);
    }

    /**
     * {@code DELETE  /user-associated-accounts/:id} : delete the "id" userAssociatedAccounts.
     *
     * @param id the id of the userAssociatedAccountsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-associated-accounts/{id}")
    public ResponseEntity<Void> deleteUserAssociatedAccounts(@PathVariable Long id) {
        log.debug("REST request to delete UserAssociatedAccounts : {}", id);
        userAssociatedAccountsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
