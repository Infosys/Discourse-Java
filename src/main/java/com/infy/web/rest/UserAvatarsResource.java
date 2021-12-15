/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.UserAvatarsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.UserAvatarsDTO;

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
 * REST controller for managing {@link com.infy.domain.UserAvatars}.
 */
@RestController
@RequestMapping("/api")
public class UserAvatarsResource {

    private final Logger log = LoggerFactory.getLogger(UserAvatarsResource.class);

    private static final String ENTITY_NAME = "userAvatars";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserAvatarsService userAvatarsService;

    public UserAvatarsResource(UserAvatarsService userAvatarsService) {
        this.userAvatarsService = userAvatarsService;
    }

    /**
     * {@code POST  /user-avatars} : Create a new userAvatars.
     *
     * @param userAvatarsDTO the userAvatarsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userAvatarsDTO, or with status {@code 400 (Bad Request)} if the userAvatars has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-avatars")
    public ResponseEntity<UserAvatarsDTO> createUserAvatars(@Valid @RequestBody UserAvatarsDTO userAvatarsDTO) throws URISyntaxException {
        log.debug("REST request to save UserAvatars : {}", userAvatarsDTO);
        if (userAvatarsDTO.getId() != null) {
            throw new BadRequestAlertException("A new userAvatars cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserAvatarsDTO result = userAvatarsService.save(userAvatarsDTO);
        return ResponseEntity.created(new URI("/api/user-avatars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-avatars} : Updates an existing userAvatars.
     *
     * @param userAvatarsDTO the userAvatarsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userAvatarsDTO,
     * or with status {@code 400 (Bad Request)} if the userAvatarsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userAvatarsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-avatars")
    public ResponseEntity<UserAvatarsDTO> updateUserAvatars(@Valid @RequestBody UserAvatarsDTO userAvatarsDTO) throws URISyntaxException {
        log.debug("REST request to update UserAvatars : {}", userAvatarsDTO);
        if (userAvatarsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserAvatarsDTO result = userAvatarsService.save(userAvatarsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userAvatarsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-avatars} : get all the userAvatars.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userAvatars in body.
     */
    @GetMapping("/user-avatars")
    public ResponseEntity<List<UserAvatarsDTO>> getAllUserAvatars(Pageable pageable) {
        log.debug("REST request to get a page of UserAvatars");
        Page<UserAvatarsDTO> page = userAvatarsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-avatars/:id} : get the "id" userAvatars.
     *
     * @param id the id of the userAvatarsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userAvatarsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-avatars/{id}")
    public ResponseEntity<UserAvatarsDTO> getUserAvatars(@PathVariable Long id) {
        log.debug("REST request to get UserAvatars : {}", id);
        Optional<UserAvatarsDTO> userAvatarsDTO = userAvatarsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userAvatarsDTO);
    }

    /**
     * {@code DELETE  /user-avatars/:id} : delete the "id" userAvatars.
     *
     * @param id the id of the userAvatarsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-avatars/{id}")
    public ResponseEntity<Void> deleteUserAvatars(@PathVariable Long id) {
        log.debug("REST request to delete UserAvatars : {}", id);
        userAvatarsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
