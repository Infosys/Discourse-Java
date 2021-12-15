/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.PushSubscriptionsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.PushSubscriptionsDTO;

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
 * REST controller for managing {@link com.infy.domain.PushSubscriptions}.
 */
@RestController
@RequestMapping("/api")
public class PushSubscriptionsResource {

    private final Logger log = LoggerFactory.getLogger(PushSubscriptionsResource.class);

    private static final String ENTITY_NAME = "pushSubscriptions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PushSubscriptionsService pushSubscriptionsService;

    public PushSubscriptionsResource(PushSubscriptionsService pushSubscriptionsService) {
        this.pushSubscriptionsService = pushSubscriptionsService;
    }

    /**
     * {@code POST  /push-subscriptions} : Create a new pushSubscriptions.
     *
     * @param pushSubscriptionsDTO the pushSubscriptionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pushSubscriptionsDTO, or with status {@code 400 (Bad Request)} if the pushSubscriptions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/push-subscriptions")
    public ResponseEntity<PushSubscriptionsDTO> createPushSubscriptions(@Valid @RequestBody PushSubscriptionsDTO pushSubscriptionsDTO) throws URISyntaxException {
        log.debug("REST request to save PushSubscriptions : {}", pushSubscriptionsDTO);
        if (pushSubscriptionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new pushSubscriptions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PushSubscriptionsDTO result = pushSubscriptionsService.save(pushSubscriptionsDTO);
        return ResponseEntity.created(new URI("/api/push-subscriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /push-subscriptions} : Updates an existing pushSubscriptions.
     *
     * @param pushSubscriptionsDTO the pushSubscriptionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pushSubscriptionsDTO,
     * or with status {@code 400 (Bad Request)} if the pushSubscriptionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pushSubscriptionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/push-subscriptions")
    public ResponseEntity<PushSubscriptionsDTO> updatePushSubscriptions(@Valid @RequestBody PushSubscriptionsDTO pushSubscriptionsDTO) throws URISyntaxException {
        log.debug("REST request to update PushSubscriptions : {}", pushSubscriptionsDTO);
        if (pushSubscriptionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PushSubscriptionsDTO result = pushSubscriptionsService.save(pushSubscriptionsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pushSubscriptionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /push-subscriptions} : get all the pushSubscriptions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pushSubscriptions in body.
     */
    @GetMapping("/push-subscriptions")
    public ResponseEntity<List<PushSubscriptionsDTO>> getAllPushSubscriptions(Pageable pageable) {
        log.debug("REST request to get a page of PushSubscriptions");
        Page<PushSubscriptionsDTO> page = pushSubscriptionsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /push-subscriptions/:id} : get the "id" pushSubscriptions.
     *
     * @param id the id of the pushSubscriptionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pushSubscriptionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/push-subscriptions/{id}")
    public ResponseEntity<PushSubscriptionsDTO> getPushSubscriptions(@PathVariable Long id) {
        log.debug("REST request to get PushSubscriptions : {}", id);
        Optional<PushSubscriptionsDTO> pushSubscriptionsDTO = pushSubscriptionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pushSubscriptionsDTO);
    }

    /**
     * {@code DELETE  /push-subscriptions/:id} : delete the "id" pushSubscriptions.
     *
     * @param id the id of the pushSubscriptionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/push-subscriptions/{id}")
    public ResponseEntity<Void> deletePushSubscriptions(@PathVariable Long id) {
        log.debug("REST request to delete PushSubscriptions : {}", id);
        pushSubscriptionsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
