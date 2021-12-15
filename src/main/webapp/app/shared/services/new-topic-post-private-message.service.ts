import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SERVER_API_URL } from 'app/app.constants';
import { Observable } from 'rxjs';
import { ITopicOrPost } from '../model/topic-or-post.model';

type EntityResponseTypeTopic = HttpResponse<ITopicOrPost>;

@Injectable({
  providedIn: 'root',
})
export class NewTopicPostPrivateMessageService {
  private createTopicPostPrivateMessageResourceUrl = SERVER_API_URL + 'api/message';

  constructor(protected http: HttpClient) {}

  createTopicPostPrivateMessage(topicPostPrivateMesasgeJson: any): Observable<EntityResponseTypeTopic> {
    return this.http.post<ITopicOrPost>(this.createTopicPostPrivateMessageResourceUrl, topicPostPrivateMesasgeJson, {
      observe: 'response',
    });
  }
}
