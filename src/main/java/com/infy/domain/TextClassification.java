/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.domain;


import javax.persistence.*;

import java.io.Serializable;

import com.infy.domain.enumeration.TextClassificationType;

/**
 * A TextClassification.
 */
@Entity
@Table(name = "text_classification")
public class TextClassification extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "content_id")
    private Long contentId;

    @Column(name = "toxicity")
    private Double toxicity;

    @Column(name = "severe_toxicity")
    private Double severeToxicity;

    @Column(name = "obscene")
    private Double obscene;

    @Column(name = "threat")
    private Double threat;

    @Column(name = "insult")
    private Double insult;

    @Column(name = "identity_hate")
    private Double identityHate;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TextClassificationType type;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContentId() {
        return contentId;
    }

    public TextClassification contentId(Long contentId) {
        this.contentId = contentId;
        return this;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public Double getToxicity() {
        return toxicity;
    }

    public TextClassification toxicity(Double toxicity) {
        this.toxicity = toxicity;
        return this;
    }

    public void setToxicity(Double toxicity) {
        this.toxicity = toxicity;
    }

    public Double getSevereToxicity() {
        return severeToxicity;
    }

    public TextClassification severeToxicity(Double severeToxicity) {
        this.severeToxicity = severeToxicity;
        return this;
    }

    public void setSevereToxicity(Double severeToxicity) {
        this.severeToxicity = severeToxicity;
    }

    public Double getObscene() {
        return obscene;
    }

    public TextClassification obscene(Double obscene) {
        this.obscene = obscene;
        return this;
    }

    public void setObscene(Double obscene) {
        this.obscene = obscene;
    }

    public Double getThreat() {
        return threat;
    }

    public TextClassification threat(Double threat) {
        this.threat = threat;
        return this;
    }

    public void setThreat(Double threat) {
        this.threat = threat;
    }

    public Double getInsult() {
        return insult;
    }

    public TextClassification insult(Double insult) {
        this.insult = insult;
        return this;
    }

    public void setInsult(Double insult) {
        this.insult = insult;
    }

    public Double getIdentityHate() {
        return identityHate;
    }

    public TextClassification identityHate(Double identityHate) {
        this.identityHate = identityHate;
        return this;
    }

    public void setIdentityHate(Double identityHate) {
        this.identityHate = identityHate;
    }

    public TextClassificationType getType() {
        return type;
    }

    public TextClassification type(TextClassificationType type) {
        this.type = type;
        return this;
    }

    public void setType(TextClassificationType type) {
        this.type = type;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TextClassification)) {
            return false;
        }
        return id != null && id.equals(((TextClassification) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TextClassification{" +
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
