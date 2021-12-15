/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.RemoteThemesService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.RemoteThemesDTO;

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
 * REST controller for managing {@link com.infy.domain.RemoteThemes}.
 */
@RestController
@RequestMapping("/api")
public class RemoteThemesResource {

    private final Logger log = LoggerFactory.getLogger(RemoteThemesResource.class);

    private static final String ENTITY_NAME = "remoteThemes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RemoteThemesService remoteThemesService;

    public RemoteThemesResource(RemoteThemesService remoteThemesService) {
        this.remoteThemesService = remoteThemesService;
    }

    /**
     * {@code POST  /remote-themes} : Create a new remoteThemes.
     *
     * @param remoteThemesDTO the remoteThemesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new remoteThemesDTO, or with status {@code 400 (Bad Request)} if the remoteThemes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/remote-themes")
    public ResponseEntity<RemoteThemesDTO> createRemoteThemes(@Valid @RequestBody RemoteThemesDTO remoteThemesDTO) throws URISyntaxException {
        log.debug("REST request to save RemoteThemes : {}", remoteThemesDTO);
        if (remoteThemesDTO.getId() != null) {
            throw new BadRequestAlertException("A new remoteThemes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RemoteThemesDTO result = remoteThemesService.save(remoteThemesDTO);
        return ResponseEntity.created(new URI("/api/remote-themes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /remote-themes} : Updates an existing remoteThemes.
     *
     * @param remoteThemesDTO the remoteThemesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated remoteThemesDTO,
     * or with status {@code 400 (Bad Request)} if the remoteThemesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the remoteThemesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/remote-themes")
    public ResponseEntity<RemoteThemesDTO> updateRemoteThemes(@Valid @RequestBody RemoteThemesDTO remoteThemesDTO) throws URISyntaxException {
        log.debug("REST request to update RemoteThemes : {}", remoteThemesDTO);
        if (remoteThemesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RemoteThemesDTO result = remoteThemesService.save(remoteThemesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, remoteThemesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /remote-themes} : get all the remoteThemes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of remoteThemes in body.
     */
    @GetMapping("/remote-themes")
    public ResponseEntity<List<RemoteThemesDTO>> getAllRemoteThemes(Pageable pageable) {
        log.debug("REST request to get a page of RemoteThemes");
        Page<RemoteThemesDTO> page = remoteThemesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /remote-themes/:id} : get the "id" remoteThemes.
     *
     * @param id the id of the remoteThemesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the remoteThemesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/remote-themes/{id}")
    public ResponseEntity<RemoteThemesDTO> getRemoteThemes(@PathVariable Long id) {
        log.debug("REST request to get RemoteThemes : {}", id);
        Optional<RemoteThemesDTO> remoteThemesDTO = remoteThemesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(remoteThemesDTO);
    }

    /**
     * {@code DELETE  /remote-themes/:id} : delete the "id" remoteThemes.
     *
     * @param id the id of the remoteThemesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/remote-themes/{id}")
    public ResponseEntity<Void> deleteRemoteThemes(@PathVariable Long id) {
        log.debug("REST request to delete RemoteThemes : {}", id);
        remoteThemesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
