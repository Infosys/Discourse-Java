/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.TranslationOverridesService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.TranslationOverridesDTO;

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
 * REST controller for managing {@link com.infy.domain.TranslationOverrides}.
 */
@RestController
@RequestMapping("/api")
public class TranslationOverridesResource {

    private final Logger log = LoggerFactory.getLogger(TranslationOverridesResource.class);

    private static final String ENTITY_NAME = "translationOverrides";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TranslationOverridesService translationOverridesService;

    public TranslationOverridesResource(TranslationOverridesService translationOverridesService) {
        this.translationOverridesService = translationOverridesService;
    }

    /**
     * {@code POST  /translation-overrides} : Create a new translationOverrides.
     *
     * @param translationOverridesDTO the translationOverridesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new translationOverridesDTO, or with status {@code 400 (Bad Request)} if the translationOverrides has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/translation-overrides")
    public ResponseEntity<TranslationOverridesDTO> createTranslationOverrides(@Valid @RequestBody TranslationOverridesDTO translationOverridesDTO) throws URISyntaxException {
        log.debug("REST request to save TranslationOverrides : {}", translationOverridesDTO);
        if (translationOverridesDTO.getId() != null) {
            throw new BadRequestAlertException("A new translationOverrides cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TranslationOverridesDTO result = translationOverridesService.save(translationOverridesDTO);
        return ResponseEntity.created(new URI("/api/translation-overrides/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /translation-overrides} : Updates an existing translationOverrides.
     *
     * @param translationOverridesDTO the translationOverridesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated translationOverridesDTO,
     * or with status {@code 400 (Bad Request)} if the translationOverridesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the translationOverridesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/translation-overrides")
    public ResponseEntity<TranslationOverridesDTO> updateTranslationOverrides(@Valid @RequestBody TranslationOverridesDTO translationOverridesDTO) throws URISyntaxException {
        log.debug("REST request to update TranslationOverrides : {}", translationOverridesDTO);
        if (translationOverridesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TranslationOverridesDTO result = translationOverridesService.save(translationOverridesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, translationOverridesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /translation-overrides} : get all the translationOverrides.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of translationOverrides in body.
     */
    @GetMapping("/translation-overrides")
    public ResponseEntity<List<TranslationOverridesDTO>> getAllTranslationOverrides(Pageable pageable) {
        log.debug("REST request to get a page of TranslationOverrides");
        Page<TranslationOverridesDTO> page = translationOverridesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /translation-overrides/:id} : get the "id" translationOverrides.
     *
     * @param id the id of the translationOverridesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the translationOverridesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/translation-overrides/{id}")
    public ResponseEntity<TranslationOverridesDTO> getTranslationOverrides(@PathVariable Long id) {
        log.debug("REST request to get TranslationOverrides : {}", id);
        Optional<TranslationOverridesDTO> translationOverridesDTO = translationOverridesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(translationOverridesDTO);
    }

    /**
     * {@code DELETE  /translation-overrides/:id} : delete the "id" translationOverrides.
     *
     * @param id the id of the translationOverridesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/translation-overrides/{id}")
    public ResponseEntity<Void> deleteTranslationOverrides(@PathVariable Long id) {
        log.debug("REST request to delete TranslationOverrides : {}", id);
        translationOverridesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
