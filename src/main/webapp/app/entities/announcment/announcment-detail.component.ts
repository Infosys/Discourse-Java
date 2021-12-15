import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAnnouncment } from 'app/shared/model/announcment.model';

@Component({
  selector: 'jhi-announcment-detail',
  templateUrl: './announcment-detail.component.html',
})
export class AnnouncmentDetailComponent implements OnInit {
  announcment: IAnnouncment | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ announcment }) => (this.announcment = announcment));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
