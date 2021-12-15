import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SERVER_API_URL } from 'app/app.constants';
import { Observable } from 'rxjs';
import { ITopicOrPost } from '../model/topic-or-post.model';
import { ITopicResponse } from '../model/topic/topic-response.model';
import { createRequestOption } from '../util/request-util';

type EntityResponseTypeTopicResponse = HttpResponse<ITopicResponse>;
// type EntityResponseTypeTopic = HttpResponse<ITopicOrPost>;
type EntityArrayResponseTypeTopic = HttpResponse<ITopicOrPost[]>;

@Injectable({
  providedIn: 'root',
})
export class TopicService {
  private topicResourceUrl = SERVER_API_URL + 'api/topics';
  private topicsByCategoryResourceUrl = SERVER_API_URL + 'api/topicsByCategory';

  constructor(protected http: HttpClient) {}

  getAllTopics(req?: any): Observable<EntityArrayResponseTypeTopic> {
    const options = createRequestOption(req);
    return this.http.get<ITopicOrPost[]>(this.topicResourceUrl, { params: options, observe: 'response' });
  }

  getTopicById(id: string): Observable<EntityResponseTypeTopicResponse> {
    return this.http.get<ITopicResponse>(`${this.topicResourceUrl}/${id}`, { observe: 'response' });
  }

  updateTopic(id: string, newDetails: any): Observable<EntityResponseTypeTopicResponse> {
    return this.http.put<ITopicResponse>(`${this.topicResourceUrl}/${id}`, newDetails, { observe: 'response' });
  }

  getAllTopicsByCategory(id: string, req?: any): Observable<EntityArrayResponseTypeTopic> {
    if (typeof req === 'undefined') {
      req = {};
    }
    req['categoryId'] = id;
    const options = createRequestOption(req);
    return this.http.get<ITopicOrPost[]>(this.topicsByCategoryResourceUrl, { params: options, observe: 'response' });
  }
}
