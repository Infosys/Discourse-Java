import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SERVER_API_URL } from 'app/app.constants';
import { Observable } from 'rxjs';
import { IAnnouncement } from '../model/announcment.model';

interface IAnnouncementRequest {
  raw: String;
  title: String;
}

type EntityArrayAnnouncementResponse = HttpResponse<IAnnouncement[]>;
type EntityAnnouncementResponse = HttpResponse<IAnnouncement>;

@Injectable({
  providedIn: 'root',
})
export class AnnouncementService {
  // public api
  private getAnnouncementsResourceUrl = SERVER_API_URL + 'public/announcements';

  // private apis
  private createAnnouncementResourceUrl = SERVER_API_URL + 'api/announcements';

  constructor(protected http: HttpClient) {}

  getAnnouncements(): Observable<EntityArrayAnnouncementResponse> {
    return this.http.get<IAnnouncement[]>(this.getAnnouncementsResourceUrl, { observe: 'response' });
  }

  createAnnouncement(announcement: IAnnouncementRequest): Observable<EntityAnnouncementResponse> {
    return this.http.post<IAnnouncement>(this.createAnnouncementResourceUrl, announcement, { observe: 'response' });
  }

  getAnnouncementById(id: string): Observable<EntityAnnouncementResponse> {
    return this.http.get<IAnnouncement>(`${this.getAnnouncementsResourceUrl}/${id}`, { observe: 'response' });
  }
}
