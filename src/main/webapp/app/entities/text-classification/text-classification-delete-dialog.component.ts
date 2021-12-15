import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITextClassification } from 'app/shared/model/text-classification.model';
import { TextClassificationService } from './text-classification.service';

@Component({
  templateUrl: './text-classification-delete-dialog.component.html',
})
export class TextClassificationDeleteDialogComponent {
  textClassification?: ITextClassification;

  constructor(
    protected textClassificationService: TextClassificationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.textClassificationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('textClassificationListModification');
      this.activeModal.close();
    });
  }
}
