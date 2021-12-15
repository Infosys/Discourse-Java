/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.UserSearchDataService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.UserSearchDataDTO;

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
 * REST controller for managing {@link com.infy.domain.UserSearchData}.
 */
@RestController
@RequestMapping("/api")
public class UserSearchDataResource {

    private final Logger log = LoggerFactory.getLogger(UserSearchDataResource.class);

    private static final String ENTITY_NAME = "userSearchData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserSearchDataService userSearchDataService;

    public UserSearchDataResource(UserSearchDataService userSearchDataService) {
        this.userSearchDataService = userSearchDataService;
    }

    /**
     * {@code POST  /user-search-data} : Create a new userSearchData.
     *
     * @param userSearchDataDTO the userSearchDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userSearchDataDTO, or with status {@code 400 (Bad Request)} if the userSearchData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-search-data")
    public ResponseEntity<UserSearchDataDTO> createUserSearchData(@Valid @RequestBody UserSearchDataDTO userSearchDataDTO) throws URISyntaxException {
        log.debug("REST request to save UserSearchData : {}", userSearchDataDTO);
        if (userSearchDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new userSearchData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserSearchDataDTO result = userSearchDataService.save(userSearchDataDTO);
        return ResponseEntity.created(new URI("/api/user-search-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-search-data} : Updates an existing userSearchData.
     *
     * @param userSearchDataDTO the userSearchDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userSearchDataDTO,
     * or with status {@code 400 (Bad Request)} if the userSearchDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userSearchDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-search-data")
    public ResponseEntity<UserSearchDataDTO> updateUserSearchData(@Valid @RequestBody UserSearchDataDTO userSearchDataDTO) throws URISyntaxException {
        log.debug("REST request to update UserSearchData : {}", userSearchDataDTO);
        if (userSearchDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserSearchDataDTO result = userSearchDataService.save(userSearchDataDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userSearchDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-search-data} : get all the userSearchData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userSearchData in body.
     */
    @GetMapping("/user-search-data")
    public ResponseEntity<List<UserSearchDataDTO>> getAllUserSearchData(Pageable pageable) {
        log.debug("REST request to get a page of UserSearchData");
        Page<UserSearchDataDTO> page = userSearchDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-search-data/:id} : get the "id" userSearchData.
     *
     * @param id the id of the userSearchDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userSearchDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-search-data/{id}")
    public ResponseEntity<UserSearchDataDTO> getUserSearchData(@PathVariable Long id) {
        log.debug("REST request to get UserSearchData : {}", id);
        Optional<UserSearchDataDTO> userSearchDataDTO = userSearchDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userSearchDataDTO);
    }

    /**
     * {@code DELETE  /user-search-data/:id} : delete the "id" userSearchData.
     *
     * @param id the id of the userSearchDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-search-data/{id}")
    public ResponseEntity<Void> deleteUserSearchData(@PathVariable Long id) {
        log.debug("REST request to delete UserSearchData : {}", id);
        userSearchDataService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
