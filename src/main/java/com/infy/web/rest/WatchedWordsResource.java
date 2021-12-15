/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.WatchedWordsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.WatchedWordsDTO;

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
 * REST controller for managing {@link com.infy.domain.WatchedWords}.
 */
@RestController
@RequestMapping("/api")
public class WatchedWordsResource {

    private final Logger log = LoggerFactory.getLogger(WatchedWordsResource.class);

    private static final String ENTITY_NAME = "watchedWords";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WatchedWordsService watchedWordsService;

    public WatchedWordsResource(WatchedWordsService watchedWordsService) {
        this.watchedWordsService = watchedWordsService;
    }

    /**
     * {@code POST  /watched-words} : Create a new watchedWords.
     *
     * @param watchedWordsDTO the watchedWordsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new watchedWordsDTO, or with status {@code 400 (Bad Request)} if the watchedWords has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/watched-words")
    public ResponseEntity<WatchedWordsDTO> createWatchedWords(@Valid @RequestBody WatchedWordsDTO watchedWordsDTO) throws URISyntaxException {
        log.debug("REST request to save WatchedWords : {}", watchedWordsDTO);
        if (watchedWordsDTO.getId() != null) {
            throw new BadRequestAlertException("A new watchedWords cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WatchedWordsDTO result = watchedWordsService.save(watchedWordsDTO);
        return ResponseEntity.created(new URI("/api/watched-words/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /watched-words} : Updates an existing watchedWords.
     *
     * @param watchedWordsDTO the watchedWordsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated watchedWordsDTO,
     * or with status {@code 400 (Bad Request)} if the watchedWordsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the watchedWordsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/watched-words")
    public ResponseEntity<WatchedWordsDTO> updateWatchedWords(@Valid @RequestBody WatchedWordsDTO watchedWordsDTO) throws URISyntaxException {
        log.debug("REST request to update WatchedWords : {}", watchedWordsDTO);
        if (watchedWordsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WatchedWordsDTO result = watchedWordsService.save(watchedWordsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, watchedWordsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /watched-words} : get all the watchedWords.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of watchedWords in body.
     */
    @GetMapping("/watched-words")
    public ResponseEntity<List<WatchedWordsDTO>> getAllWatchedWords(Pageable pageable) {
        log.debug("REST request to get a page of WatchedWords");
        Page<WatchedWordsDTO> page = watchedWordsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /watched-words/:id} : get the "id" watchedWords.
     *
     * @param id the id of the watchedWordsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the watchedWordsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/watched-words/{id}")
    public ResponseEntity<WatchedWordsDTO> getWatchedWords(@PathVariable Long id) {
        log.debug("REST request to get WatchedWords : {}", id);
        Optional<WatchedWordsDTO> watchedWordsDTO = watchedWordsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(watchedWordsDTO);
    }

    /**
     * {@code DELETE  /watched-words/:id} : delete the "id" watchedWords.
     *
     * @param id the id of the watchedWordsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/watched-words/{id}")
    public ResponseEntity<Void> deleteWatchedWords(@PathVariable Long id) {
        log.debug("REST request to delete WatchedWords : {}", id);
        watchedWordsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
