/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.UnsubscribeKeysService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.UnsubscribeKeysDTO;

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
 * REST controller for managing {@link com.infy.domain.UnsubscribeKeys}.
 */
@RestController
@RequestMapping("/api")
public class UnsubscribeKeysResource {

    private final Logger log = LoggerFactory.getLogger(UnsubscribeKeysResource.class);

    private static final String ENTITY_NAME = "unsubscribeKeys";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UnsubscribeKeysService unsubscribeKeysService;

    public UnsubscribeKeysResource(UnsubscribeKeysService unsubscribeKeysService) {
        this.unsubscribeKeysService = unsubscribeKeysService;
    }

    /**
     * {@code POST  /unsubscribe-keys} : Create a new unsubscribeKeys.
     *
     * @param unsubscribeKeysDTO the unsubscribeKeysDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new unsubscribeKeysDTO, or with status {@code 400 (Bad Request)} if the unsubscribeKeys has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/unsubscribe-keys")
    public ResponseEntity<UnsubscribeKeysDTO> createUnsubscribeKeys(@Valid @RequestBody UnsubscribeKeysDTO unsubscribeKeysDTO) throws URISyntaxException {
        log.debug("REST request to save UnsubscribeKeys : {}", unsubscribeKeysDTO);
        if (unsubscribeKeysDTO.getId() != null) {
            throw new BadRequestAlertException("A new unsubscribeKeys cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UnsubscribeKeysDTO result = unsubscribeKeysService.save(unsubscribeKeysDTO);
        return ResponseEntity.created(new URI("/api/unsubscribe-keys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /unsubscribe-keys} : Updates an existing unsubscribeKeys.
     *
     * @param unsubscribeKeysDTO the unsubscribeKeysDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated unsubscribeKeysDTO,
     * or with status {@code 400 (Bad Request)} if the unsubscribeKeysDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the unsubscribeKeysDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/unsubscribe-keys")
    public ResponseEntity<UnsubscribeKeysDTO> updateUnsubscribeKeys(@Valid @RequestBody UnsubscribeKeysDTO unsubscribeKeysDTO) throws URISyntaxException {
        log.debug("REST request to update UnsubscribeKeys : {}", unsubscribeKeysDTO);
        if (unsubscribeKeysDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UnsubscribeKeysDTO result = unsubscribeKeysService.save(unsubscribeKeysDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, unsubscribeKeysDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /unsubscribe-keys} : get all the unsubscribeKeys.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of unsubscribeKeys in body.
     */
    @GetMapping("/unsubscribe-keys")
    public ResponseEntity<List<UnsubscribeKeysDTO>> getAllUnsubscribeKeys(Pageable pageable) {
        log.debug("REST request to get a page of UnsubscribeKeys");
        Page<UnsubscribeKeysDTO> page = unsubscribeKeysService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /unsubscribe-keys/:id} : get the "id" unsubscribeKeys.
     *
     * @param id the id of the unsubscribeKeysDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the unsubscribeKeysDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/unsubscribe-keys/{id}")
    public ResponseEntity<UnsubscribeKeysDTO> getUnsubscribeKeys(@PathVariable Long id) {
        log.debug("REST request to get UnsubscribeKeys : {}", id);
        Optional<UnsubscribeKeysDTO> unsubscribeKeysDTO = unsubscribeKeysService.findOne(id);
        return ResponseUtil.wrapOrNotFound(unsubscribeKeysDTO);
    }

    /**
     * {@code DELETE  /unsubscribe-keys/:id} : delete the "id" unsubscribeKeys.
     *
     * @param id the id of the unsubscribeKeysDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/unsubscribe-keys/{id}")
    public ResponseEntity<Void> deleteUnsubscribeKeys(@PathVariable Long id) {
        log.debug("REST request to delete UnsubscribeKeys : {}", id);
        unsubscribeKeysService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
