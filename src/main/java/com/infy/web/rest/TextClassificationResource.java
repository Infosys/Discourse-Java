/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.TextClassificationService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.TextClassificationDTO;

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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.infy.domain.TextClassification}.
 */
@RestController
@RequestMapping("/api")
public class TextClassificationResource {

    private final Logger log = LoggerFactory.getLogger(TextClassificationResource.class);

    private static final String ENTITY_NAME = "textClassification";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TextClassificationService textClassificationService;

    public TextClassificationResource(TextClassificationService textClassificationService) {
        this.textClassificationService = textClassificationService;
    }

    /**
     * {@code POST  /text-classifications} : Create a new textClassification.
     *
     * @param textClassificationDTO the textClassificationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new textClassificationDTO, or with status {@code 400 (Bad Request)} if the textClassification has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/text-classifications")
    public ResponseEntity<TextClassificationDTO> createTextClassification(@RequestBody TextClassificationDTO textClassificationDTO) throws URISyntaxException {
        log.debug("REST request to save TextClassification : {}", textClassificationDTO);
        if (textClassificationDTO.getId() != null) {
            throw new BadRequestAlertException("A new textClassification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TextClassificationDTO result = textClassificationService.save(textClassificationDTO);
        return ResponseEntity.created(new URI("/api/text-classifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /text-classifications} : Updates an existing textClassification.
     *
     * @param textClassificationDTO the textClassificationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated textClassificationDTO,
     * or with status {@code 400 (Bad Request)} if the textClassificationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the textClassificationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/text-classifications")
    public ResponseEntity<TextClassificationDTO> updateTextClassification(@RequestBody TextClassificationDTO textClassificationDTO) throws URISyntaxException {
        log.debug("REST request to update TextClassification : {}", textClassificationDTO);
        if (textClassificationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TextClassificationDTO result = textClassificationService.save(textClassificationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, textClassificationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /text-classifications} : get all the textClassifications.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of textClassifications in body.
     */
    @GetMapping("/text-classifications")
    public ResponseEntity<List<TextClassificationDTO>> getAllTextClassifications(Pageable pageable) {
        log.debug("REST request to get a page of TextClassifications");
        Page<TextClassificationDTO> page = textClassificationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /text-classifications/:id} : get the "id" textClassification.
     *
     * @param id the id of the textClassificationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the textClassificationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/text-classifications/{id}")
    public ResponseEntity<TextClassificationDTO> getTextClassification(@PathVariable Long id) {
        log.debug("REST request to get TextClassification : {}", id);
        Optional<TextClassificationDTO> textClassificationDTO = textClassificationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(textClassificationDTO);
    }

    /**
     * {@code DELETE  /text-classifications/:id} : delete the "id" textClassification.
     *
     * @param id the id of the textClassificationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/text-classifications/{id}")
    public ResponseEntity<Void> deleteTextClassification(@PathVariable Long id) {
        log.debug("REST request to delete TextClassification : {}", id);
        textClassificationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
