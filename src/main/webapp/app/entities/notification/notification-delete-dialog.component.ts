import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INotification } from 'app/shared/model/notification.model';
import { NotificationService } from './notification.service';

@Component({
  templateUrl: './notification-delete-dialog.component.html',
})
export class NotificationDeleteDialogComponent {
  notification?: INotification;

  constructor(
    protected notificationService: NotificationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.notificationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('notificationListModification');
      this.activeModal.close();
    });
  }
}
