/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.UserIpAddressHistoriesService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.UserIpAddressHistoriesDTO;

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
 * REST controller for managing {@link com.infy.domain.UserIpAddressHistories}.
 */
@RestController
@RequestMapping("/api")
public class UserIpAddressHistoriesResource {

    private final Logger log = LoggerFactory.getLogger(UserIpAddressHistoriesResource.class);

    private static final String ENTITY_NAME = "userIpAddressHistories";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserIpAddressHistoriesService userIpAddressHistoriesService;

    public UserIpAddressHistoriesResource(UserIpAddressHistoriesService userIpAddressHistoriesService) {
        this.userIpAddressHistoriesService = userIpAddressHistoriesService;
    }

    /**
     * {@code POST  /user-ip-address-histories} : Create a new userIpAddressHistories.
     *
     * @param userIpAddressHistoriesDTO the userIpAddressHistoriesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userIpAddressHistoriesDTO, or with status {@code 400 (Bad Request)} if the userIpAddressHistories has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-ip-address-histories")
    public ResponseEntity<UserIpAddressHistoriesDTO> createUserIpAddressHistories(@Valid @RequestBody UserIpAddressHistoriesDTO userIpAddressHistoriesDTO) throws URISyntaxException {
        log.debug("REST request to save UserIpAddressHistories : {}", userIpAddressHistoriesDTO);
        if (userIpAddressHistoriesDTO.getId() != null) {
            throw new BadRequestAlertException("A new userIpAddressHistories cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserIpAddressHistoriesDTO result = userIpAddressHistoriesService.save(userIpAddressHistoriesDTO);
        return ResponseEntity.created(new URI("/api/user-ip-address-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-ip-address-histories} : Updates an existing userIpAddressHistories.
     *
     * @param userIpAddressHistoriesDTO the userIpAddressHistoriesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userIpAddressHistoriesDTO,
     * or with status {@code 400 (Bad Request)} if the userIpAddressHistoriesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userIpAddressHistoriesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-ip-address-histories")
    public ResponseEntity<UserIpAddressHistoriesDTO> updateUserIpAddressHistories(@Valid @RequestBody UserIpAddressHistoriesDTO userIpAddressHistoriesDTO) throws URISyntaxException {
        log.debug("REST request to update UserIpAddressHistories : {}", userIpAddressHistoriesDTO);
        if (userIpAddressHistoriesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserIpAddressHistoriesDTO result = userIpAddressHistoriesService.save(userIpAddressHistoriesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userIpAddressHistoriesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-ip-address-histories} : get all the userIpAddressHistories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userIpAddressHistories in body.
     */
    @GetMapping("/user-ip-address-histories")
    public ResponseEntity<List<UserIpAddressHistoriesDTO>> getAllUserIpAddressHistories(Pageable pageable) {
        log.debug("REST request to get a page of UserIpAddressHistories");
        Page<UserIpAddressHistoriesDTO> page = userIpAddressHistoriesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-ip-address-histories/:id} : get the "id" userIpAddressHistories.
     *
     * @param id the id of the userIpAddressHistoriesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userIpAddressHistoriesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-ip-address-histories/{id}")
    public ResponseEntity<UserIpAddressHistoriesDTO> getUserIpAddressHistories(@PathVariable Long id) {
        log.debug("REST request to get UserIpAddressHistories : {}", id);
        Optional<UserIpAddressHistoriesDTO> userIpAddressHistoriesDTO = userIpAddressHistoriesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userIpAddressHistoriesDTO);
    }

    /**
     * {@code DELETE  /user-ip-address-histories/:id} : delete the "id" userIpAddressHistories.
     *
     * @param id the id of the userIpAddressHistoriesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-ip-address-histories/{id}")
    public ResponseEntity<Void> deleteUserIpAddressHistories(@PathVariable Long id) {
        log.debug("REST request to delete UserIpAddressHistories : {}", id);
        userIpAddressHistoriesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
