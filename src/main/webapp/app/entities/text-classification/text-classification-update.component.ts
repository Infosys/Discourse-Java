import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITextClassification, TextClassification } from 'app/shared/model/text-classification.model';
import { TextClassificationService } from './text-classification.service';

@Component({
  selector: 'jhi-text-classification-update',
  templateUrl: './text-classification-update.component.html',
})
export class TextClassificationUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    contentId: [],
    toxicity: [],
    severeToxicity: [],
    obscene: [],
    threat: [],
    insult: [],
    identityHate: [],
    type: [],
  });

  constructor(
    protected textClassificationService: TextClassificationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ textClassification }) => {
      this.updateForm(textClassification);
    });
  }

  updateForm(textClassification: ITextClassification): void {
    this.editForm.patchValue({
      id: textClassification.id,
      contentId: textClassification.contentId,
      toxicity: textClassification.toxicity,
      severeToxicity: textClassification.severeToxicity,
      obscene: textClassification.obscene,
      threat: textClassification.threat,
      insult: textClassification.insult,
      identityHate: textClassification.identityHate,
      type: textClassification.type,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const textClassification = this.createFromForm();
    if (textClassification.id !== undefined) {
      this.subscribeToSaveResponse(this.textClassificationService.update(textClassification));
    } else {
      this.subscribeToSaveResponse(this.textClassificationService.create(textClassification));
    }
  }

  private createFromForm(): ITextClassification {
    return {
      ...new TextClassification(),
      id: this.editForm.get(['id'])!.value,
      contentId: this.editForm.get(['contentId'])!.value,
      toxicity: this.editForm.get(['toxicity'])!.value,
      severeToxicity: this.editForm.get(['severeToxicity'])!.value,
      obscene: this.editForm.get(['obscene'])!.value,
      threat: this.editForm.get(['threat'])!.value,
      insult: this.editForm.get(['insult'])!.value,
      identityHate: this.editForm.get(['identityHate'])!.value,
      type: this.editForm.get(['type'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITextClassification>>): void {
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
