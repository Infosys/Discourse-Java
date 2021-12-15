/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.UserSecurityKeysService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.UserSecurityKeysDTO;

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
 * REST controller for managing {@link com.infy.domain.UserSecurityKeys}.
 */
@RestController
@RequestMapping("/api")
public class UserSecurityKeysResource {

    private final Logger log = LoggerFactory.getLogger(UserSecurityKeysResource.class);

    private static final String ENTITY_NAME = "userSecurityKeys";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserSecurityKeysService userSecurityKeysService;

    public UserSecurityKeysResource(UserSecurityKeysService userSecurityKeysService) {
        this.userSecurityKeysService = userSecurityKeysService;
    }

    /**
     * {@code POST  /user-security-keys} : Create a new userSecurityKeys.
     *
     * @param userSecurityKeysDTO the userSecurityKeysDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userSecurityKeysDTO, or with status {@code 400 (Bad Request)} if the userSecurityKeys has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-security-keys")
    public ResponseEntity<UserSecurityKeysDTO> createUserSecurityKeys(@Valid @RequestBody UserSecurityKeysDTO userSecurityKeysDTO) throws URISyntaxException {
        log.debug("REST request to save UserSecurityKeys : {}", userSecurityKeysDTO);
        if (userSecurityKeysDTO.getId() != null) {
            throw new BadRequestAlertException("A new userSecurityKeys cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserSecurityKeysDTO result = userSecurityKeysService.save(userSecurityKeysDTO);
        return ResponseEntity.created(new URI("/api/user-security-keys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-security-keys} : Updates an existing userSecurityKeys.
     *
     * @param userSecurityKeysDTO the userSecurityKeysDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userSecurityKeysDTO,
     * or with status {@code 400 (Bad Request)} if the userSecurityKeysDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userSecurityKeysDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-security-keys")
    public ResponseEntity<UserSecurityKeysDTO> updateUserSecurityKeys(@Valid @RequestBody UserSecurityKeysDTO userSecurityKeysDTO) throws URISyntaxException {
        log.debug("REST request to update UserSecurityKeys : {}", userSecurityKeysDTO);
        if (userSecurityKeysDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserSecurityKeysDTO result = userSecurityKeysService.save(userSecurityKeysDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userSecurityKeysDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-security-keys} : get all the userSecurityKeys.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userSecurityKeys in body.
     */
    @GetMapping("/user-security-keys")
    public ResponseEntity<List<UserSecurityKeysDTO>> getAllUserSecurityKeys(Pageable pageable) {
        log.debug("REST request to get a page of UserSecurityKeys");
        Page<UserSecurityKeysDTO> page = userSecurityKeysService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-security-keys/:id} : get the "id" userSecurityKeys.
     *
     * @param id the id of the userSecurityKeysDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userSecurityKeysDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-security-keys/{id}")
    public ResponseEntity<UserSecurityKeysDTO> getUserSecurityKeys(@PathVariable Long id) {
        log.debug("REST request to get UserSecurityKeys : {}", id);
        Optional<UserSecurityKeysDTO> userSecurityKeysDTO = userSecurityKeysService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userSecurityKeysDTO);
    }

    /**
     * {@code DELETE  /user-security-keys/:id} : delete the "id" userSecurityKeys.
     *
     * @param id the id of the userSecurityKeysDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-security-keys/{id}")
    public ResponseEntity<Void> deleteUserSecurityKeys(@PathVariable Long id) {
        log.debug("REST request to delete UserSecurityKeys : {}", id);
        userSecurityKeysService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
