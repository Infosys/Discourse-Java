import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITextClassification } from 'app/shared/model/text-classification.model';

@Component({
  selector: 'jhi-text-classification-detail',
  templateUrl: './text-classification-detail.component.html',
})
export class TextClassificationDetailComponent implements OnInit {
  textClassification: ITextClassification | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ textClassification }) => (this.textClassification = textClassification));
  }

  previousState(): void {
    window.history.back();
  }
}
