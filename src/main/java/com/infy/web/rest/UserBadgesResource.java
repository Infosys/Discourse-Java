/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.UserBadgesService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.UserBadgesDTO;

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
 * REST controller for managing {@link com.infy.domain.UserBadges}.
 */
@RestController
@RequestMapping("/api")
public class UserBadgesResource {

    private final Logger log = LoggerFactory.getLogger(UserBadgesResource.class);

    private static final String ENTITY_NAME = "userBadges";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserBadgesService userBadgesService;

    public UserBadgesResource(UserBadgesService userBadgesService) {
        this.userBadgesService = userBadgesService;
    }

    /**
     * {@code POST  /user-badges} : Create a new userBadges.
     *
     * @param userBadgesDTO the userBadgesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userBadgesDTO, or with status {@code 400 (Bad Request)} if the userBadges has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-badges")
    public ResponseEntity<UserBadgesDTO> createUserBadges(@Valid @RequestBody UserBadgesDTO userBadgesDTO) throws URISyntaxException {
        log.debug("REST request to save UserBadges : {}", userBadgesDTO);
        if (userBadgesDTO.getId() != null) {
            throw new BadRequestAlertException("A new userBadges cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserBadgesDTO result = userBadgesService.save(userBadgesDTO);
        return ResponseEntity.created(new URI("/api/user-badges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-badges} : Updates an existing userBadges.
     *
     * @param userBadgesDTO the userBadgesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userBadgesDTO,
     * or with status {@code 400 (Bad Request)} if the userBadgesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userBadgesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-badges")
    public ResponseEntity<UserBadgesDTO> updateUserBadges(@Valid @RequestBody UserBadgesDTO userBadgesDTO) throws URISyntaxException {
        log.debug("REST request to update UserBadges : {}", userBadgesDTO);
        if (userBadgesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserBadgesDTO result = userBadgesService.save(userBadgesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userBadgesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-badges} : get all the userBadges.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userBadges in body.
     */
    @GetMapping("/user-badges")
    public ResponseEntity<List<UserBadgesDTO>> getAllUserBadges(Pageable pageable) {
        log.debug("REST request to get a page of UserBadges");
        Page<UserBadgesDTO> page = userBadgesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-badges/:id} : get the "id" userBadges.
     *
     * @param id the id of the userBadgesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userBadgesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-badges/{id}")
    public ResponseEntity<UserBadgesDTO> getUserBadges(@PathVariable Long id) {
        log.debug("REST request to get UserBadges : {}", id);
        Optional<UserBadgesDTO> userBadgesDTO = userBadgesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userBadgesDTO);
    }

    /**
     * {@code DELETE  /user-badges/:id} : delete the "id" userBadges.
     *
     * @param id the id of the userBadgesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-badges/{id}")
    public ResponseEntity<Void> deleteUserBadges(@PathVariable Long id) {
        log.debug("REST request to delete UserBadges : {}", id);
        userBadgesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
