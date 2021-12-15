import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { IAnnouncement } from 'app/shared/model/announcment.model';
import { AnnouncementService } from 'app/shared/services/announcement.service';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'jhi-view-announcement',
  templateUrl: './view-announcement.component.html',
  styleUrls: ['./view-announcement.component.scss'],
})
export class ViewAnnouncementComponent implements OnInit {
  announcementId!: string;
  announcement!: IAnnouncement;

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private announcementService: AnnouncementService,
    private loader: NgxSpinnerService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      if (params['id']) {
        this.loader.show();
        this.announcementId = params['id'];
        this.loadAnnouncement();
      } else {
        this.router.navigateByUrl('/');
      }
    });
  }

  loadAnnouncement(): void {
    this.announcementService.getAnnouncementById(this.announcementId).subscribe(
      (res: HttpResponse<IAnnouncement>) => {
        this.announcement = res.body!;
        this.loader.hide();
      },
      (err: HttpErrorResponse) => {
        console.error(err);
        this.router.navigateByUrl('/');
      }
    );
  }
}
