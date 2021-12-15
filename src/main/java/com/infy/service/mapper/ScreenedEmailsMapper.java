/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.ScreenedEmailsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ScreenedEmails} and its DTO {@link ScreenedEmailsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ScreenedEmailsMapper extends EntityMapper<ScreenedEmailsDTO, ScreenedEmails> {



    default ScreenedEmails fromId(Long id) {
        if (id == null) {
            return null;
        }
        ScreenedEmails screenedEmails = new ScreenedEmails();
        screenedEmails.setId(id);
        return screenedEmails;
    }
}
