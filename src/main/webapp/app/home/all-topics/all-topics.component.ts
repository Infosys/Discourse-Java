import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';

import { FormBuilder, FormGroup } from '@angular/forms';
import { Subscription } from 'rxjs';
import { ICategory } from 'app/shared/model/category/category.model';
import { Router } from '@angular/router';
import { GetCategoriesService } from 'app/shared/services/get-categories.service';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { TopicService } from 'app/shared/services/topic.service';
import { IRichTextEditorValues } from 'app/shared/model/rich-text-editor-values.model';
import { ITopicOrPost } from 'app/shared/model/topic-or-post.model';
import { SortHomePageRequestHeader } from '../../shared/model/enumerations/sort-home-page-request-header.model';
import { NewTopicPostPrivateMessageService } from 'app/shared/services/new-topic-post-private-message.service';
import { MatSelect } from '@angular/material/select';
import { GetUserService } from 'app/shared/services/get-user.service';
import { AnnouncementService } from 'app/shared/services/announcement.service';
import { IUser } from 'app/shared/model/user/user.model';
import { IAnnouncement } from 'app/shared/model/announcment.model';
import { ViewportChangeDetector } from 'app/shared/services/viewport-change-detector';
import { NgxSpinnerService } from 'ngx-spinner';

enum PageContent {
  topics = 'TOPICS',
  categories = 'CATEGORIES',
  announcements = 'ANNOUNCEMENTS',
}

@Component({
  selector: 'jhi-all-topics',
  templateUrl: './all-topics.component.html',
  styleUrls: ['./all-topics.component.scss'],
})
export class AllTopicsComponent implements OnInit, OnDestroy {
  isViewXsOrSm = false;
  categorySelected!: string;
  showEditor = false;
  selectedCategory = 'Uncategorized';
  topicsType = { sort: [SortHomePageRequestHeader.LATEST] };
  sortHomePageRequestHeader = {
    latest: SortHomePageRequestHeader.LATEST,
    top: SortHomePageRequestHeader.TOP,
  };
  categoryTagFormGroup!: FormGroup;
  categoryList: ICategory[] = [];
  // tagList: string[] = ['no tag', 'art', 'music', 'general'];
  getCategoryListServiceSubscription: Subscription;
  topicList: any[] = [];
  displayColumn = ['TOPIC', '', 'POSTS', 'CREATED AT'];
  pageContent = 'TOPICS';
  viewportChangeDetectorSubscription: Subscription;
  @ViewChild('categorySelector') categorySelector!: MatSelect;
  @ViewChild('tagsSelector') tagsSelector!: MatSelect;

  currentlyLoggedInUser!: IUser;
  getUserServiceSubscription: Subscription;
  announcements: IAnnouncement[] = [];

  constructor(
    private router: Router,
    private getCategoriesService: GetCategoriesService,
    private formBuilder: FormBuilder,
    private topicService: TopicService,
    private newTopicPostPrivateMessageService: NewTopicPostPrivateMessageService,
    private getUserService: GetUserService,
    private announcementService: AnnouncementService,
    private viewportChangeDetector: ViewportChangeDetector,
    private loader: NgxSpinnerService
  ) {
    this.viewportChangeDetectorSubscription = this.viewportChangeDetector
      .detectScreenSize({ xs: true, sm: true })
      .subscribe(isViewXsOrSm => {
        this.isViewXsOrSm = isViewXsOrSm!;
      });
    this.getCategoryListServiceSubscription = this.getCategoriesService.categoryList.subscribe((categories: ICategory[]) => {
      this.categoryList = categories;
    });
    this.getUserServiceSubscription = this.getUserService.currentlyLoggedInUser.subscribe(user => {
      this.currentlyLoggedInUser = user;
    });
  }

  ngOnInit(): void {
    this.initialiseFormGroup();
    this.loadAllTopics();
  }

  openMatSelect(i: number): void {
    if (i === 0) {
      this.categorySelector.open();
    }
    if (i === 1) {
      this.tagsSelector.open();
    }
  }

  submitEditorContent(content: IRichTextEditorValues): void {
    if (content.categoryId !== null) {
      this.createTopic(content);
    } else {
      this.createAnnouncement(content);
    }
  }

  createTopic(topic: IRichTextEditorValues): void {
    const newTopic = {
      raw: topic.htmlContent,
      title: topic.title,
      tags: topic.tags,
      category: topic.categoryId,
    };
    this.loader.show();
    this.newTopicPostPrivateMessageService.createTopicPostPrivateMessage(newTopic).subscribe(
      (res: HttpResponse<ITopicOrPost>) => {
        this.viewTopicDiscussion(res.body!.id!);
      },
      (err: HttpErrorResponse) => {
        console.error(err);
        this.loader.hide();
      }
    );
  }

  showEditorPanel(): void {
    this.showEditor = true;
  }

  viewTopicDiscussion(id: string): void {
    this.router.navigateByUrl(`topic/${id}`);
  }

  loadAllTopics(): void {
    this.loader.show();
    this.topicService.getAllTopics(this.topicsType).subscribe(
      (res: HttpResponse<ITopicOrPost[]>) => {
        this.topicList = res.body!;
        this.loader.hide();
      },
      (err: HttpErrorResponse) => {
        console.error(err);
        this.loader.hide();
      }
    );
  }

  viewCategories(): void {
    this.categoryTagFormGroup.patchValue({
      category: '-1',
    });
    this.changeTopicListOrder(0);
    this.pageContent = PageContent.categories;
  }

  categoryClicked(category: ICategory): void {
    if (category.id) {
      this.loadAllTopicsByCategory(category.id);

      this.categoryTagFormGroup.patchValue({
        category: category.id,
      });
      this.filterByCategory();
      this.changeTopicListOrder(0);
    }
  }

  loadAllTopicsByCategory(id: string): void {
    this.loader.show();
    this.topicService.getAllTopicsByCategory(id, this.topicsType).subscribe(
      (res: HttpResponse<ITopicOrPost[]>) => {
        this.topicList = res.body!;
        this.loader.hide();
      },
      (err: HttpErrorResponse) => {
        console.error(err);
        this.loader.hide();
      }
    );
  }

  changeTopicListOrder(type: number): void {
    // type = 0 is for latest topics
    // type = 1 is for top topics

    this.pageContent = PageContent.topics;
    this.topicsType.sort[0] = type === 1 ? SortHomePageRequestHeader.TOP : SortHomePageRequestHeader.LATEST;
    this.filterByCategory();
  }

  filterByCategory(): void {
    this.pageContent = PageContent.topics;
    this.categoryTagFormGroup.value.category === '-1'
      ? this.loadAllTopics()
      : this.loadAllTopicsByCategory(this.categoryTagFormGroup.value.category);
  }

  initialiseFormGroup(): void {
    this.categoryTagFormGroup = this.formBuilder.group({
      category: ['-1'],
      tags: ['no tag'],
    });
  }

  createAnnouncement(announcementFromEditor: IRichTextEditorValues): void {
    const announcement = {
      raw: announcementFromEditor.htmlContent!,
      title: announcementFromEditor.title!,
    };
    this.loader.show();
    this.announcementService.createAnnouncement(announcement).subscribe(
      (res: HttpResponse<IAnnouncement>) => {
        this.router.navigateByUrl(`announcement/${res.body?.id}`);
      },
      (err: HttpErrorResponse) => {
        console.error(err);
        this.loader.hide();
      }
    );
  }

  viewAnnouncements(): void {
    this.loadAnnouncements();
    this.pageContent = PageContent.announcements;
  }

  loadAnnouncements(): void {
    this.loader.show();
    this.announcementService.getAnnouncements().subscribe(
      (res: HttpResponse<IAnnouncement[]>) => {
        this.announcements = res.body!;
        this.loader.hide();
      },
      (err: HttpErrorResponse) => {
        console.error(err);
        this.loader.hide();
      }
    );
  }

  navigateToAnnouncement(index: number): void {
    this.router.navigateByUrl(`announcement/${this.announcements[index]?.id}`);
  }

  ngOnDestroy(): void {
    this.getCategoryListServiceSubscription.unsubscribe();
    this.getUserServiceSubscription.unsubscribe();
    this.viewportChangeDetectorSubscription.unsubscribe();
  }
}
