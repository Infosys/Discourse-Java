/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.UserExportsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.UserExportsDTO;

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
 * REST controller for managing {@link com.infy.domain.UserExports}.
 */
@RestController
@RequestMapping("/api")
public class UserExportsResource {

    private final Logger log = LoggerFactory.getLogger(UserExportsResource.class);

    private static final String ENTITY_NAME = "userExports";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserExportsService userExportsService;

    public UserExportsResource(UserExportsService userExportsService) {
        this.userExportsService = userExportsService;
    }

    /**
     * {@code POST  /user-exports} : Create a new userExports.
     *
     * @param userExportsDTO the userExportsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userExportsDTO, or with status {@code 400 (Bad Request)} if the userExports has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-exports")
    public ResponseEntity<UserExportsDTO> createUserExports(@Valid @RequestBody UserExportsDTO userExportsDTO) throws URISyntaxException {
        log.debug("REST request to save UserExports : {}", userExportsDTO);
        if (userExportsDTO.getId() != null) {
            throw new BadRequestAlertException("A new userExports cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserExportsDTO result = userExportsService.save(userExportsDTO);
        return ResponseEntity.created(new URI("/api/user-exports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-exports} : Updates an existing userExports.
     *
     * @param userExportsDTO the userExportsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userExportsDTO,
     * or with status {@code 400 (Bad Request)} if the userExportsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userExportsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-exports")
    public ResponseEntity<UserExportsDTO> updateUserExports(@Valid @RequestBody UserExportsDTO userExportsDTO) throws URISyntaxException {
        log.debug("REST request to update UserExports : {}", userExportsDTO);
        if (userExportsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserExportsDTO result = userExportsService.save(userExportsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userExportsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-exports} : get all the userExports.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userExports in body.
     */
    @GetMapping("/user-exports")
    public ResponseEntity<List<UserExportsDTO>> getAllUserExports(Pageable pageable) {
        log.debug("REST request to get a page of UserExports");
        Page<UserExportsDTO> page = userExportsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-exports/:id} : get the "id" userExports.
     *
     * @param id the id of the userExportsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userExportsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-exports/{id}")
    public ResponseEntity<UserExportsDTO> getUserExports(@PathVariable Long id) {
        log.debug("REST request to get UserExports : {}", id);
        Optional<UserExportsDTO> userExportsDTO = userExportsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userExportsDTO);
    }

    /**
     * {@code DELETE  /user-exports/:id} : delete the "id" userExports.
     *
     * @param id the id of the userExportsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-exports/{id}")
    public ResponseEntity<Void> deleteUserExports(@PathVariable Long id) {
        log.debug("REST request to delete UserExports : {}", id);
        userExportsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
