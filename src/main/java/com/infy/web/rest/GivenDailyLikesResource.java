/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.GivenDailyLikesService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.GivenDailyLikesDTO;

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
 * REST controller for managing {@link com.infy.domain.GivenDailyLikes}.
 */
@RestController
@RequestMapping("/api")
public class GivenDailyLikesResource {

    private final Logger log = LoggerFactory.getLogger(GivenDailyLikesResource.class);

    private static final String ENTITY_NAME = "givenDailyLikes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GivenDailyLikesService givenDailyLikesService;

    public GivenDailyLikesResource(GivenDailyLikesService givenDailyLikesService) {
        this.givenDailyLikesService = givenDailyLikesService;
    }

    /**
     * {@code POST  /given-daily-likes} : Create a new givenDailyLikes.
     *
     * @param givenDailyLikesDTO the givenDailyLikesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new givenDailyLikesDTO, or with status {@code 400 (Bad Request)} if the givenDailyLikes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/given-daily-likes")
    public ResponseEntity<GivenDailyLikesDTO> createGivenDailyLikes(@Valid @RequestBody GivenDailyLikesDTO givenDailyLikesDTO) throws URISyntaxException {
        log.debug("REST request to save GivenDailyLikes : {}", givenDailyLikesDTO);
        if (givenDailyLikesDTO.getId() != null) {
            throw new BadRequestAlertException("A new givenDailyLikes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GivenDailyLikesDTO result = givenDailyLikesService.save(givenDailyLikesDTO);
        return ResponseEntity.created(new URI("/api/given-daily-likes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /given-daily-likes} : Updates an existing givenDailyLikes.
     *
     * @param givenDailyLikesDTO the givenDailyLikesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated givenDailyLikesDTO,
     * or with status {@code 400 (Bad Request)} if the givenDailyLikesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the givenDailyLikesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/given-daily-likes")
    public ResponseEntity<GivenDailyLikesDTO> updateGivenDailyLikes(@Valid @RequestBody GivenDailyLikesDTO givenDailyLikesDTO) throws URISyntaxException {
        log.debug("REST request to update GivenDailyLikes : {}", givenDailyLikesDTO);
        if (givenDailyLikesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GivenDailyLikesDTO result = givenDailyLikesService.save(givenDailyLikesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, givenDailyLikesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /given-daily-likes} : get all the givenDailyLikes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of givenDailyLikes in body.
     */
    @GetMapping("/given-daily-likes")
    public ResponseEntity<List<GivenDailyLikesDTO>> getAllGivenDailyLikes(Pageable pageable) {
        log.debug("REST request to get a page of GivenDailyLikes");
        Page<GivenDailyLikesDTO> page = givenDailyLikesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /given-daily-likes/:id} : get the "id" givenDailyLikes.
     *
     * @param id the id of the givenDailyLikesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the givenDailyLikesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/given-daily-likes/{id}")
    public ResponseEntity<GivenDailyLikesDTO> getGivenDailyLikes(@PathVariable Long id) {
        log.debug("REST request to get GivenDailyLikes : {}", id);
        Optional<GivenDailyLikesDTO> givenDailyLikesDTO = givenDailyLikesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(givenDailyLikesDTO);
    }

    /**
     * {@code DELETE  /given-daily-likes/:id} : delete the "id" givenDailyLikes.
     *
     * @param id the id of the givenDailyLikesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/given-daily-likes/{id}")
    public ResponseEntity<Void> deleteGivenDailyLikes(@PathVariable Long id) {
        log.debug("REST request to delete GivenDailyLikes : {}", id);
        givenDailyLikesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
