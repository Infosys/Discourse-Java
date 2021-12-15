/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.UploadsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.UploadsDTO;

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
 * REST controller for managing {@link com.infy.domain.Uploads}.
 */
@RestController
@RequestMapping("/api")
public class UploadsResource {

    private final Logger log = LoggerFactory.getLogger(UploadsResource.class);

    private static final String ENTITY_NAME = "uploads";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UploadsService uploadsService;

    public UploadsResource(UploadsService uploadsService) {
        this.uploadsService = uploadsService;
    }

    /**
     * {@code POST  /uploads} : Create a new uploads.
     *
     * @param uploadsDTO the uploadsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new uploadsDTO, or with status {@code 400 (Bad Request)} if the uploads has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/uploads")
    public ResponseEntity<UploadsDTO> createUploads(@Valid @RequestBody UploadsDTO uploadsDTO) throws URISyntaxException {
        log.debug("REST request to save Uploads : {}", uploadsDTO);
        if (uploadsDTO.getId() != null) {
            throw new BadRequestAlertException("A new uploads cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UploadsDTO result = uploadsService.save(uploadsDTO);
        return ResponseEntity.created(new URI("/api/uploads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /uploads} : Updates an existing uploads.
     *
     * @param uploadsDTO the uploadsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uploadsDTO,
     * or with status {@code 400 (Bad Request)} if the uploadsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the uploadsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/uploads")
    public ResponseEntity<UploadsDTO> updateUploads(@Valid @RequestBody UploadsDTO uploadsDTO) throws URISyntaxException {
        log.debug("REST request to update Uploads : {}", uploadsDTO);
        if (uploadsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UploadsDTO result = uploadsService.save(uploadsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, uploadsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /uploads} : get all the uploads.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of uploads in body.
     */
    @GetMapping("/uploads")
    public ResponseEntity<List<UploadsDTO>> getAllUploads(Pageable pageable) {
        log.debug("REST request to get a page of Uploads");
        Page<UploadsDTO> page = uploadsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /uploads/:id} : get the "id" uploads.
     *
     * @param id the id of the uploadsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the uploadsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/uploads/{id}")
    public ResponseEntity<UploadsDTO> getUploads(@PathVariable Long id) {
        log.debug("REST request to get Uploads : {}", id);
        Optional<UploadsDTO> uploadsDTO = uploadsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(uploadsDTO);
    }

    /**
     * {@code DELETE  /uploads/:id} : delete the "id" uploads.
     *
     * @param id the id of the uploadsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/uploads/{id}")
    public ResponseEntity<Void> deleteUploads(@PathVariable Long id) {
        log.debug("REST request to delete Uploads : {}", id);
        uploadsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
