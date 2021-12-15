/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.EmbeddableHostsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.EmbeddableHostsDTO;

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
 * REST controller for managing {@link com.infy.domain.EmbeddableHosts}.
 */
@RestController
@RequestMapping("/api")
public class EmbeddableHostsResource {

    private final Logger log = LoggerFactory.getLogger(EmbeddableHostsResource.class);

    private static final String ENTITY_NAME = "embeddableHosts";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmbeddableHostsService embeddableHostsService;

    public EmbeddableHostsResource(EmbeddableHostsService embeddableHostsService) {
        this.embeddableHostsService = embeddableHostsService;
    }

    /**
     * {@code POST  /embeddable-hosts} : Create a new embeddableHosts.
     *
     * @param embeddableHostsDTO the embeddableHostsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new embeddableHostsDTO, or with status {@code 400 (Bad Request)} if the embeddableHosts has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/embeddable-hosts")
    public ResponseEntity<EmbeddableHostsDTO> createEmbeddableHosts(@Valid @RequestBody EmbeddableHostsDTO embeddableHostsDTO) throws URISyntaxException {
        log.debug("REST request to save EmbeddableHosts : {}", embeddableHostsDTO);
        if (embeddableHostsDTO.getId() != null) {
            throw new BadRequestAlertException("A new embeddableHosts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmbeddableHostsDTO result = embeddableHostsService.save(embeddableHostsDTO);
        return ResponseEntity.created(new URI("/api/embeddable-hosts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /embeddable-hosts} : Updates an existing embeddableHosts.
     *
     * @param embeddableHostsDTO the embeddableHostsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated embeddableHostsDTO,
     * or with status {@code 400 (Bad Request)} if the embeddableHostsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the embeddableHostsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/embeddable-hosts")
    public ResponseEntity<EmbeddableHostsDTO> updateEmbeddableHosts(@Valid @RequestBody EmbeddableHostsDTO embeddableHostsDTO) throws URISyntaxException {
        log.debug("REST request to update EmbeddableHosts : {}", embeddableHostsDTO);
        if (embeddableHostsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmbeddableHostsDTO result = embeddableHostsService.save(embeddableHostsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, embeddableHostsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /embeddable-hosts} : get all the embeddableHosts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of embeddableHosts in body.
     */
    @GetMapping("/embeddable-hosts")
    public ResponseEntity<List<EmbeddableHostsDTO>> getAllEmbeddableHosts(Pageable pageable) {
        log.debug("REST request to get a page of EmbeddableHosts");
        Page<EmbeddableHostsDTO> page = embeddableHostsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /embeddable-hosts/:id} : get the "id" embeddableHosts.
     *
     * @param id the id of the embeddableHostsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the embeddableHostsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/embeddable-hosts/{id}")
    public ResponseEntity<EmbeddableHostsDTO> getEmbeddableHosts(@PathVariable Long id) {
        log.debug("REST request to get EmbeddableHosts : {}", id);
        Optional<EmbeddableHostsDTO> embeddableHostsDTO = embeddableHostsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(embeddableHostsDTO);
    }

    /**
     * {@code DELETE  /embeddable-hosts/:id} : delete the "id" embeddableHosts.
     *
     * @param id the id of the embeddableHostsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/embeddable-hosts/{id}")
    public ResponseEntity<Void> deleteEmbeddableHosts(@PathVariable Long id) {
        log.debug("REST request to delete EmbeddableHosts : {}", id);
        embeddableHostsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
