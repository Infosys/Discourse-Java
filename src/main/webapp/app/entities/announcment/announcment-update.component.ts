import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IAnnouncment, Announcment } from 'app/shared/model/announcment.model';
import { AnnouncmentService } from './announcment.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-announcment-update',
  templateUrl: './announcment-update.component.html',
})
export class AnnouncmentUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    title: [],
    raw: [],
    deletedBy: [],
    deletedAt: [],
    status: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected announcmentService: AnnouncmentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ announcment }) => {
      if (!announcment.id) {
        const today = moment().startOf('day');
        announcment.deletedAt = today;
      }

      this.updateForm(announcment);
    });
  }

  updateForm(announcment: IAnnouncment): void {
    this.editForm.patchValue({
      id: announcment.id,
      title: announcment.title,
      raw: announcment.raw,
      deletedBy: announcment.deletedBy,
      deletedAt: announcment.deletedAt ? announcment.deletedAt.format(DATE_TIME_FORMAT) : null,
      status: announcment.status,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('discourseApp.error', { message: err.message })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const announcment = this.createFromForm();
    if (announcment.id !== undefined) {
      this.subscribeToSaveResponse(this.announcmentService.update(announcment));
    } else {
      this.subscribeToSaveResponse(this.announcmentService.create(announcment));
    }
  }

  private createFromForm(): IAnnouncment {
    return {
      ...new Announcment(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      raw: this.editForm.get(['raw'])!.value,
      deletedBy: this.editForm.get(['deletedBy'])!.value,
      deletedAt: this.editForm.get(['deletedAt'])!.value ? moment(this.editForm.get(['deletedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      status: this.editForm.get(['status'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnnouncment>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
