/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.DevelopersService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.DevelopersDTO;

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
 * REST controller for managing {@link com.infy.domain.Developers}.
 */
@RestController
@RequestMapping("/api")
public class DevelopersResource {

    private final Logger log = LoggerFactory.getLogger(DevelopersResource.class);

    private static final String ENTITY_NAME = "developers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DevelopersService developersService;

    public DevelopersResource(DevelopersService developersService) {
        this.developersService = developersService;
    }

    /**
     * {@code POST  /developers} : Create a new developers.
     *
     * @param developersDTO the developersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new developersDTO, or with status {@code 400 (Bad Request)} if the developers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/developers")
    public ResponseEntity<DevelopersDTO> createDevelopers(@Valid @RequestBody DevelopersDTO developersDTO) throws URISyntaxException {
        log.debug("REST request to save Developers : {}", developersDTO);
        if (developersDTO.getId() != null) {
            throw new BadRequestAlertException("A new developers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DevelopersDTO result = developersService.save(developersDTO);
        return ResponseEntity.created(new URI("/api/developers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /developers} : Updates an existing developers.
     *
     * @param developersDTO the developersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated developersDTO,
     * or with status {@code 400 (Bad Request)} if the developersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the developersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/developers")
    public ResponseEntity<DevelopersDTO> updateDevelopers(@Valid @RequestBody DevelopersDTO developersDTO) throws URISyntaxException {
        log.debug("REST request to update Developers : {}", developersDTO);
        if (developersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DevelopersDTO result = developersService.save(developersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, developersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /developers} : get all the developers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of developers in body.
     */
    @GetMapping("/developers")
    public ResponseEntity<List<DevelopersDTO>> getAllDevelopers(Pageable pageable) {
        log.debug("REST request to get a page of Developers");
        Page<DevelopersDTO> page = developersService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /developers/:id} : get the "id" developers.
     *
     * @param id the id of the developersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the developersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/developers/{id}")
    public ResponseEntity<DevelopersDTO> getDevelopers(@PathVariable Long id) {
        log.debug("REST request to get Developers : {}", id);
        Optional<DevelopersDTO> developersDTO = developersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(developersDTO);
    }

    /**
     * {@code DELETE  /developers/:id} : delete the "id" developers.
     *
     * @param id the id of the developersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/developers/{id}")
    public ResponseEntity<Void> deleteDevelopers(@PathVariable Long id) {
        log.debug("REST request to delete Developers : {}", id);
        developersService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
