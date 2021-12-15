import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAnnouncment } from 'app/shared/model/announcment.model';
import { AnnouncmentService } from './announcment.service';

@Component({
  templateUrl: './announcment-delete-dialog.component.html',
})
export class AnnouncmentDeleteDialogComponent {
  announcment?: IAnnouncment;

  constructor(
    protected announcmentService: AnnouncmentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.announcmentService.delete(id).subscribe(() => {
      this.eventManager.broadcast('announcmentListModification');
      this.activeModal.close();
    });
  }
}
