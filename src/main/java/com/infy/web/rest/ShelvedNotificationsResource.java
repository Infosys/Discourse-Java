/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.ShelvedNotificationsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.ShelvedNotificationsDTO;

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
 * REST controller for managing {@link com.infy.domain.ShelvedNotifications}.
 */
@RestController
@RequestMapping("/api")
public class ShelvedNotificationsResource {

    private final Logger log = LoggerFactory.getLogger(ShelvedNotificationsResource.class);

    private static final String ENTITY_NAME = "shelvedNotifications";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShelvedNotificationsService shelvedNotificationsService;

    public ShelvedNotificationsResource(ShelvedNotificationsService shelvedNotificationsService) {
        this.shelvedNotificationsService = shelvedNotificationsService;
    }

    /**
     * {@code POST  /shelved-notifications} : Create a new shelvedNotifications.
     *
     * @param shelvedNotificationsDTO the shelvedNotificationsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shelvedNotificationsDTO, or with status {@code 400 (Bad Request)} if the shelvedNotifications has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/shelved-notifications")
    public ResponseEntity<ShelvedNotificationsDTO> createShelvedNotifications(@Valid @RequestBody ShelvedNotificationsDTO shelvedNotificationsDTO) throws URISyntaxException {
        log.debug("REST request to save ShelvedNotifications : {}", shelvedNotificationsDTO);
        if (shelvedNotificationsDTO.getId() != null) {
            throw new BadRequestAlertException("A new shelvedNotifications cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShelvedNotificationsDTO result = shelvedNotificationsService.save(shelvedNotificationsDTO);
        return ResponseEntity.created(new URI("/api/shelved-notifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /shelved-notifications} : Updates an existing shelvedNotifications.
     *
     * @param shelvedNotificationsDTO the shelvedNotificationsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shelvedNotificationsDTO,
     * or with status {@code 400 (Bad Request)} if the shelvedNotificationsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shelvedNotificationsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/shelved-notifications")
    public ResponseEntity<ShelvedNotificationsDTO> updateShelvedNotifications(@Valid @RequestBody ShelvedNotificationsDTO shelvedNotificationsDTO) throws URISyntaxException {
        log.debug("REST request to update ShelvedNotifications : {}", shelvedNotificationsDTO);
        if (shelvedNotificationsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ShelvedNotificationsDTO result = shelvedNotificationsService.save(shelvedNotificationsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, shelvedNotificationsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /shelved-notifications} : get all the shelvedNotifications.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shelvedNotifications in body.
     */
    @GetMapping("/shelved-notifications")
    public ResponseEntity<List<ShelvedNotificationsDTO>> getAllShelvedNotifications(Pageable pageable) {
        log.debug("REST request to get a page of ShelvedNotifications");
        Page<ShelvedNotificationsDTO> page = shelvedNotificationsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /shelved-notifications/:id} : get the "id" shelvedNotifications.
     *
     * @param id the id of the shelvedNotificationsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shelvedNotificationsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/shelved-notifications/{id}")
    public ResponseEntity<ShelvedNotificationsDTO> getShelvedNotifications(@PathVariable Long id) {
        log.debug("REST request to get ShelvedNotifications : {}", id);
        Optional<ShelvedNotificationsDTO> shelvedNotificationsDTO = shelvedNotificationsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shelvedNotificationsDTO);
    }

    /**
     * {@code DELETE  /shelved-notifications/:id} : delete the "id" shelvedNotifications.
     *
     * @param id the id of the shelvedNotificationsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/shelved-notifications/{id}")
    public ResponseEntity<Void> deleteShelvedNotifications(@PathVariable Long id) {
        log.debug("REST request to delete ShelvedNotifications : {}", id);
        shelvedNotificationsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
