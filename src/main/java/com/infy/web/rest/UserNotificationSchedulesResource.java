/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.UserNotificationSchedulesService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.UserNotificationSchedulesDTO;

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
 * REST controller for managing {@link com.infy.domain.UserNotificationSchedules}.
 */
@RestController
@RequestMapping("/api")
public class UserNotificationSchedulesResource {

    private final Logger log = LoggerFactory.getLogger(UserNotificationSchedulesResource.class);

    private static final String ENTITY_NAME = "userNotificationSchedules";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserNotificationSchedulesService userNotificationSchedulesService;

    public UserNotificationSchedulesResource(UserNotificationSchedulesService userNotificationSchedulesService) {
        this.userNotificationSchedulesService = userNotificationSchedulesService;
    }

    /**
     * {@code POST  /user-notification-schedules} : Create a new userNotificationSchedules.
     *
     * @param userNotificationSchedulesDTO the userNotificationSchedulesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userNotificationSchedulesDTO, or with status {@code 400 (Bad Request)} if the userNotificationSchedules has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-notification-schedules")
    public ResponseEntity<UserNotificationSchedulesDTO> createUserNotificationSchedules(@Valid @RequestBody UserNotificationSchedulesDTO userNotificationSchedulesDTO) throws URISyntaxException {
        log.debug("REST request to save UserNotificationSchedules : {}", userNotificationSchedulesDTO);
        if (userNotificationSchedulesDTO.getId() != null) {
            throw new BadRequestAlertException("A new userNotificationSchedules cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserNotificationSchedulesDTO result = userNotificationSchedulesService.save(userNotificationSchedulesDTO);
        return ResponseEntity.created(new URI("/api/user-notification-schedules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-notification-schedules} : Updates an existing userNotificationSchedules.
     *
     * @param userNotificationSchedulesDTO the userNotificationSchedulesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userNotificationSchedulesDTO,
     * or with status {@code 400 (Bad Request)} if the userNotificationSchedulesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userNotificationSchedulesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-notification-schedules")
    public ResponseEntity<UserNotificationSchedulesDTO> updateUserNotificationSchedules(@Valid @RequestBody UserNotificationSchedulesDTO userNotificationSchedulesDTO) throws URISyntaxException {
        log.debug("REST request to update UserNotificationSchedules : {}", userNotificationSchedulesDTO);
        if (userNotificationSchedulesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserNotificationSchedulesDTO result = userNotificationSchedulesService.save(userNotificationSchedulesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userNotificationSchedulesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-notification-schedules} : get all the userNotificationSchedules.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userNotificationSchedules in body.
     */
    @GetMapping("/user-notification-schedules")
    public ResponseEntity<List<UserNotificationSchedulesDTO>> getAllUserNotificationSchedules(Pageable pageable) {
        log.debug("REST request to get a page of UserNotificationSchedules");
        Page<UserNotificationSchedulesDTO> page = userNotificationSchedulesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-notification-schedules/:id} : get the "id" userNotificationSchedules.
     *
     * @param id the id of the userNotificationSchedulesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userNotificationSchedulesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-notification-schedules/{id}")
    public ResponseEntity<UserNotificationSchedulesDTO> getUserNotificationSchedules(@PathVariable Long id) {
        log.debug("REST request to get UserNotificationSchedules : {}", id);
        Optional<UserNotificationSchedulesDTO> userNotificationSchedulesDTO = userNotificationSchedulesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userNotificationSchedulesDTO);
    }

    /**
     * {@code DELETE  /user-notification-schedules/:id} : delete the "id" userNotificationSchedules.
     *
     * @param id the id of the userNotificationSchedulesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-notification-schedules/{id}")
    public ResponseEntity<Void> deleteUserNotificationSchedules(@PathVariable Long id) {
        log.debug("REST request to delete UserNotificationSchedules : {}", id);
        userNotificationSchedulesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
