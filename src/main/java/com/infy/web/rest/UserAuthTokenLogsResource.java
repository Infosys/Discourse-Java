/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.UserAuthTokenLogsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.UserAuthTokenLogsDTO;

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
 * REST controller for managing {@link com.infy.domain.UserAuthTokenLogs}.
 */
@RestController
@RequestMapping("/api")
public class UserAuthTokenLogsResource {

    private final Logger log = LoggerFactory.getLogger(UserAuthTokenLogsResource.class);

    private static final String ENTITY_NAME = "userAuthTokenLogs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserAuthTokenLogsService userAuthTokenLogsService;

    public UserAuthTokenLogsResource(UserAuthTokenLogsService userAuthTokenLogsService) {
        this.userAuthTokenLogsService = userAuthTokenLogsService;
    }

    /**
     * {@code POST  /user-auth-token-logs} : Create a new userAuthTokenLogs.
     *
     * @param userAuthTokenLogsDTO the userAuthTokenLogsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userAuthTokenLogsDTO, or with status {@code 400 (Bad Request)} if the userAuthTokenLogs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-auth-token-logs")
    public ResponseEntity<UserAuthTokenLogsDTO> createUserAuthTokenLogs(@Valid @RequestBody UserAuthTokenLogsDTO userAuthTokenLogsDTO) throws URISyntaxException {
        log.debug("REST request to save UserAuthTokenLogs : {}", userAuthTokenLogsDTO);
        if (userAuthTokenLogsDTO.getId() != null) {
            throw new BadRequestAlertException("A new userAuthTokenLogs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserAuthTokenLogsDTO result = userAuthTokenLogsService.save(userAuthTokenLogsDTO);
        return ResponseEntity.created(new URI("/api/user-auth-token-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-auth-token-logs} : Updates an existing userAuthTokenLogs.
     *
     * @param userAuthTokenLogsDTO the userAuthTokenLogsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userAuthTokenLogsDTO,
     * or with status {@code 400 (Bad Request)} if the userAuthTokenLogsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userAuthTokenLogsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-auth-token-logs")
    public ResponseEntity<UserAuthTokenLogsDTO> updateUserAuthTokenLogs(@Valid @RequestBody UserAuthTokenLogsDTO userAuthTokenLogsDTO) throws URISyntaxException {
        log.debug("REST request to update UserAuthTokenLogs : {}", userAuthTokenLogsDTO);
        if (userAuthTokenLogsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserAuthTokenLogsDTO result = userAuthTokenLogsService.save(userAuthTokenLogsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userAuthTokenLogsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-auth-token-logs} : get all the userAuthTokenLogs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userAuthTokenLogs in body.
     */
    @GetMapping("/user-auth-token-logs")
    public ResponseEntity<List<UserAuthTokenLogsDTO>> getAllUserAuthTokenLogs(Pageable pageable) {
        log.debug("REST request to get a page of UserAuthTokenLogs");
        Page<UserAuthTokenLogsDTO> page = userAuthTokenLogsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-auth-token-logs/:id} : get the "id" userAuthTokenLogs.
     *
     * @param id the id of the userAuthTokenLogsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userAuthTokenLogsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-auth-token-logs/{id}")
    public ResponseEntity<UserAuthTokenLogsDTO> getUserAuthTokenLogs(@PathVariable Long id) {
        log.debug("REST request to get UserAuthTokenLogs : {}", id);
        Optional<UserAuthTokenLogsDTO> userAuthTokenLogsDTO = userAuthTokenLogsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userAuthTokenLogsDTO);
    }

    /**
     * {@code DELETE  /user-auth-token-logs/:id} : delete the "id" userAuthTokenLogs.
     *
     * @param id the id of the userAuthTokenLogsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-auth-token-logs/{id}")
    public ResponseEntity<Void> deleteUserAuthTokenLogs(@PathVariable Long id) {
        log.debug("REST request to delete UserAuthTokenLogs : {}", id);
        userAuthTokenLogsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
