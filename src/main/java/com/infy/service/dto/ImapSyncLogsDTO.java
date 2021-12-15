/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.infy.domain.ImapSyncLogs} entity.
 */
public class ImapSyncLogsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer level;

    @NotNull
    private String message;

    private Long groupId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImapSyncLogsDTO)) {
            return false;
        }

        return id != null && id.equals(((ImapSyncLogsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ImapSyncLogsDTO{" +
            "id=" + getId() +
            ", level=" + getLevel() +
            ", message='" + getMessage() + "'" +
            ", groupId=" + getGroupId() +
            "}";
    }
}
