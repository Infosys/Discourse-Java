/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.EmailLogsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmailLogs} and its DTO {@link EmailLogsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EmailLogsMapper extends EntityMapper<EmailLogsDTO, EmailLogs> {



    default EmailLogs fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmailLogs emailLogs = new EmailLogs();
        emailLogs.setId(id);
        return emailLogs;
    }
}
