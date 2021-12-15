import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SERVER_API_URL } from 'app/app.constants';
import { Observable } from 'rxjs';
import { ITopicOrPost } from '../model/topic-or-post.model';

type EntityArrayTopicOrPostResponse = HttpResponse<ITopicOrPost[]>;
@Injectable({
  providedIn: 'root',
})
export class SearchService {
  private searchTopicByTitleResourceUrl = SERVER_API_URL + 'api/search-topics-title';
  private searchTopicByTagsResourceUrl = SERVER_API_URL + 'api/search-topics-tags';

  constructor(private http: HttpClient) {}

  searchTopicByTitle(title: string): Observable<EntityArrayTopicOrPostResponse> {
    return this.http.get<ITopicOrPost[]>(`${this.searchTopicByTitleResourceUrl}/${title}`, { observe: 'response' });
  }

  searchTopicByTags(tags: string): Observable<EntityArrayTopicOrPostResponse> {
    return this.http.get<ITopicOrPost[]>(`${this.searchTopicByTagsResourceUrl}/${tags}`, { observe: 'response' });
  }
}
