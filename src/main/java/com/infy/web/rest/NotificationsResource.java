/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.NotificationsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.NotificationsDTO;

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
 * REST controller for managing {@link com.infy.domain.Notifications}.
 */
@RestController
@RequestMapping("/api")
public class NotificationsResource {

    private final Logger log = LoggerFactory.getLogger(NotificationsResource.class);

    private static final String ENTITY_NAME = "notifications";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NotificationsService notificationsService;

    public NotificationsResource(NotificationsService notificationsService) {
        this.notificationsService = notificationsService;
    }

//    /**
//     * {@code POST  /notifications} : Create a new notifications.
//     *
//     * @param notificationsDTO the notificationsDTO to create.
//     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new notificationsDTO, or with status {@code 400 (Bad Request)} if the notifications has already an ID.
//     * @throws URISyntaxException if the Location URI syntax is incorrect.
//     */
//    @PostMapping("/notifications")
//    public ResponseEntity<NotificationsDTO> createNotifications(@Valid @RequestBody NotificationsDTO notificationsDTO) throws URISyntaxException {
//        log.debug("REST request to save Notifications : {}", notificationsDTO);
//        if (notificationsDTO.getId() != null) {
//            throw new BadRequestAlertException("A new notifications cannot already have an ID", ENTITY_NAME, "idexists");
//        }
//        NotificationsDTO result = notificationsService.save(notificationsDTO);
//        return ResponseEntity.created(new URI("/api/notifications/" + result.getId()))
//            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
//            .body(result);
//    }
//
//    /**
//     * {@code PUT  /notifications} : Updates an existing notifications.
//     *
//     * @param notificationsDTO the notificationsDTO to update.
//     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notificationsDTO,
//     * or with status {@code 400 (Bad Request)} if the notificationsDTO is not valid,
//     * or with status {@code 500 (Internal Server Error)} if the notificationsDTO couldn't be updated.
//     * @throws URISyntaxException if the Location URI syntax is incorrect.
//     */
//    @PutMapping("/notifications")
//    public ResponseEntity<NotificationsDTO> updateNotifications(@Valid @RequestBody NotificationsDTO notificationsDTO) throws URISyntaxException {
//        log.debug("REST request to update Notifications : {}", notificationsDTO);
//        if (notificationsDTO.getId() == null) {
//            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
//        }
//        NotificationsDTO result = notificationsService.save(notificationsDTO);
//        return ResponseEntity.ok()
//            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, notificationsDTO.getId().toString()))
//            .body(result);
//    }
//
//    /**
//     * {@code GET  /notifications} : get all the notifications.
//     *
//     * @param pageable the pagination information.
//     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of notifications in body.
//     */
//    @GetMapping("/notifications")
//    public ResponseEntity<List<NotificationsDTO>> getAllNotifications(Pageable pageable) {
//        log.debug("REST request to get a page of Notifications");
//        Page<NotificationsDTO> page = notificationsService.findAll(pageable);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
//        return ResponseEntity.ok().headers(headers).body(page.getContent());
//    }
//
//    /**
//     * {@code GET  /notifications/:id} : get the "id" notifications.
//     *
//     * @param id the id of the notificationsDTO to retrieve.
//     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the notificationsDTO, or with status {@code 404 (Not Found)}.
//     */
//    @GetMapping("/notifications/{id}")
//    public ResponseEntity<NotificationsDTO> getNotifications(@PathVariable Long id) {
//        log.debug("REST request to get Notifications : {}", id);
//        Optional<NotificationsDTO> notificationsDTO = notificationsService.findOne(id);
//        return ResponseUtil.wrapOrNotFound(notificationsDTO);
//    }
//
//    /**
//     * {@code DELETE  /notifications/:id} : delete the "id" notifications.
//     *
//     * @param id the id of the notificationsDTO to delete.
//     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
//     */
//    @DeleteMapping("/notifications/{id}")
//    public ResponseEntity<Void> deleteNotifications(@PathVariable Long id) {
//        log.debug("REST request to delete Notifications : {}", id);
//        notificationsService.delete(id);
//        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
//    }
}
