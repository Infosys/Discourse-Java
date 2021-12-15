/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.repository;

import com.infy.domain.UserActions;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UserActions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserActionsRepository extends JpaRepository<UserActions, Long> {

	List<UserActions> findByUserIdAndActionTypeAndTargetPostId(String userId,Integer actionType,Long postId);

	Page<UserActions> findByUserIdAndActionType(String userId,Integer actionType,Pageable pageable);
}
