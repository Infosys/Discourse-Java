/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.SiteSettingsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.SiteSettingsDTO;

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
 * REST controller for managing {@link com.infy.domain.SiteSettings}.
 */
@RestController
@RequestMapping("/api")
public class SiteSettingsResource {

    private final Logger log = LoggerFactory.getLogger(SiteSettingsResource.class);

    private static final String ENTITY_NAME = "siteSettings";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SiteSettingsService siteSettingsService;

    public SiteSettingsResource(SiteSettingsService siteSettingsService) {
        this.siteSettingsService = siteSettingsService;
    }

    /**
     * {@code POST  /site-settings} : Create a new siteSettings.
     *
     * @param siteSettingsDTO the siteSettingsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new siteSettingsDTO, or with status {@code 400 (Bad Request)} if the siteSettings has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/site-settings")
    public ResponseEntity<SiteSettingsDTO> createSiteSettings(@Valid @RequestBody SiteSettingsDTO siteSettingsDTO) throws URISyntaxException {
        log.debug("REST request to save SiteSettings : {}", siteSettingsDTO);
        if (siteSettingsDTO.getId() != null) {
            throw new BadRequestAlertException("A new siteSettings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SiteSettingsDTO result = siteSettingsService.save(siteSettingsDTO);
        return ResponseEntity.created(new URI("/api/site-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /site-settings} : Updates an existing siteSettings.
     *
     * @param siteSettingsDTO the siteSettingsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated siteSettingsDTO,
     * or with status {@code 400 (Bad Request)} if the siteSettingsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the siteSettingsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/site-settings")
    public ResponseEntity<SiteSettingsDTO> updateSiteSettings(@Valid @RequestBody SiteSettingsDTO siteSettingsDTO) throws URISyntaxException {
        log.debug("REST request to update SiteSettings : {}", siteSettingsDTO);
        if (siteSettingsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SiteSettingsDTO result = siteSettingsService.save(siteSettingsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, siteSettingsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /site-settings} : get all the siteSettings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of siteSettings in body.
     */
    @GetMapping("/site-settings")
    public ResponseEntity<List<SiteSettingsDTO>> getAllSiteSettings(Pageable pageable) {
        log.debug("REST request to get a page of SiteSettings");
        Page<SiteSettingsDTO> page = siteSettingsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /site-settings/:id} : get the "id" siteSettings.
     *
     * @param id the id of the siteSettingsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the siteSettingsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/site-settings/{id}")
    public ResponseEntity<SiteSettingsDTO> getSiteSettings(@PathVariable Long id) {
        log.debug("REST request to get SiteSettings : {}", id);
        Optional<SiteSettingsDTO> siteSettingsDTO = siteSettingsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(siteSettingsDTO);
    }

    /**
     * {@code DELETE  /site-settings/:id} : delete the "id" siteSettings.
     *
     * @param id the id of the siteSettingsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/site-settings/{id}")
    public ResponseEntity<Void> deleteSiteSettings(@PathVariable Long id) {
        log.debug("REST request to delete SiteSettings : {}", id);
        siteSettingsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
