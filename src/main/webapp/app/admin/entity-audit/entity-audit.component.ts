import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService } from 'ng-jhipster';

import { EntityAuditService } from './entity-audit.service';
import { EntityAuditEvent } from './entity-audit-event.model';
import { EntityAuditModalComponent } from './entity-audit-modal.component';

@Component({
  selector: 'jhi-entity-audit',
  templateUrl: './entity-audit.component.html',
  styles: [
    `
      .code {
        background: #dcdada;
        padding: 10px;
      }
    `,
  ],
})
export class EntityAuditComponent implements OnInit {
  audits: EntityAuditEvent[] = [];
  entities: string[] = [];
  selectedEntity?: string;
  limits = [25, 50, 100, 200];
  selectedLimit = this.limits[0];
  loading = false;
  filterEntityId = '';
  orderProp?: string;
  reverse = false;

  constructor(private modalService: NgbModal, private service: EntityAuditService, private alertService: JhiAlertService) {}

  ngOnInit(): void {
    this.service.getAllAudited().subscribe(entities => {
      this.entities = entities;
    });
  }

  loadChanges(): void {
    if (!this.selectedEntity) {
      return;
    }
    this.loading = true;
    this.service.findByEntity(this.selectedEntity, this.selectedLimit).subscribe(
      res => {
        const data = res.body || [];
        this.audits = data.map((it: EntityAuditEvent) => {
          it.entityValue = JSON.parse(it.entityValue || '');
          return it;
        });
        this.loading = false;
      },
      () => (this.loading = false)
    );
  }

  trackId(index: number, item: EntityAuditEvent): string {
    return item.id!;
  }

  openChange(audit: EntityAuditEvent): void {
    if (!audit.commitVersion || audit.commitVersion < 2) {
      this.alertService.warning(
        'There is no previous version available for this entry.\n' + 'This is the first audit entry captured for this object.'
      );
    } else {
      const modalRef = this.modalService.open(EntityAuditModalComponent);
      modalRef.componentInstance.openChange(audit);
    }
  }
}
