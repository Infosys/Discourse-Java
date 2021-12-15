/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.dto;

import java.io.Serializable;
import com.infy.domain.enumeration.TextClassificationType;

/**
 * A DTO for the {@link com.infy.domain.TextClassification} entity.
 */
public class TextClassificationDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    private Long contentId;

    private Double toxicity;

    private Double severeToxicity;

    private Double obscene;

    private Double threat;

    private Double insult;

    private Double identityHate;

    private TextClassificationType type;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public Double getToxicity() {
        return toxicity;
    }

    public void setToxicity(Double toxicity) {
        this.toxicity = toxicity;
    }

    public Double getSevereToxicity() {
        return severeToxicity;
    }

    public void setSevereToxicity(Double severeToxicity) {
        this.severeToxicity = severeToxicity;
    }

    public Double getObscene() {
        return obscene;
    }

    public void setObscene(Double obscene) {
        this.obscene = obscene;
    }

    public Double getThreat() {
        return threat;
    }

    public void setThreat(Double threat) {
        this.threat = threat;
    }

    public Double getInsult() {
        return insult;
    }

    public void setInsult(Double insult) {
        this.insult = insult;
    }

    public Double getIdentityHate() {
        return identityHate;
    }

    public void setIdentityHate(Double identityHate) {
        this.identityHate = identityHate;
    }

    public TextClassificationType getType() {
        return type;
    }

    public void setType(TextClassificationType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TextClassificationDTO)) {
            return false;
        }

        return id != null && id.equals(((TextClassificationDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TextClassificationDTO{" +
            "id=" + getId() +
            ", contentId=" + getContentId() +
            ", toxicity=" + getToxicity() +
            ", severeToxicity=" + getSevereToxicity() +
            ", obscene=" + getObscene() +
            ", threat=" + getThreat() +
            ", insult=" + getInsult() +
            ", identityHate=" + getIdentityHate() +
            ", type='" + getType() + "'" +
            "}";
    }
}
