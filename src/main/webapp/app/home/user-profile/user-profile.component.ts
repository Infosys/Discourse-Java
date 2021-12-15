import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ICategory } from 'app/shared/model/category/category.model';
import { ITopicOrPost } from 'app/shared/model/topic-or-post.model';
import { IUser } from 'app/shared/model/user/user.model';
import { AppUtilServices } from 'app/shared/services/app-util.services';
import { GetCategoriesService } from 'app/shared/services/get-categories.service';
import { GetUserService } from 'app/shared/services/get-user.service';
import { PostService } from 'app/shared/services/post.service';
import { TopicService } from 'app/shared/services/topic.service';
import { UserService } from 'app/shared/services/user.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { Subscription } from 'rxjs';

@Component({
  selector: 'jhi-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss'],
})
export class UserProfileComponent implements OnInit, OnDestroy {
  username: any = 'Jagriti Bhandari';
  ripcolor: any;
  topics: any;
  currentlyLoggedInUser!: IUser;
  currentlyLoggedInUserServiceSubscription: Subscription;
  topicList: any[] = [];
  postList: any[] = [];
  posts: any[] = [];
  categoriesJson = {};
  categoryList: ICategory[] = [];
  getCategoryListServiceSubscription: Subscription;
  likedPost: any[] = [];
  hiddenPost: any[] = [];
  selectedIndex: any = 0;
  displayOptions: any = ['Topics', 'Replies', 'Likes', 'Votes'];
  displayColumn = ['TOPIC', '', 'POSTS', 'CREATED AT'];
  constructor(
    private codeCp: AppUtilServices,
    private router: Router,
    private postService: PostService,
    private topicService: TopicService,
    private getCategoriesService: GetCategoriesService,
    private userService: UserService,
    private getUserService: GetUserService,
    private loader: NgxSpinnerService
  ) {
    this.getCategoryListServiceSubscription = this.getCategoriesService.categoryList.subscribe((categories: ICategory[]) => {
      this.categoryList = categories;
    });
    this.currentlyLoggedInUserServiceSubscription = this.getUserService.currentlyLoggedInUser.subscribe(user => {
      this.currentlyLoggedInUser = user;
    });
  }

  ngOnInit(): void {
    this.ripcolor = this.codeCp.ripcolor;
    this.codeCp.middleHeight();
    this.loadAllTopics();
  }

  viewTopicDiscussion(id: number): void {
    this.router.navigateByUrl(`topic/${id}`);
  }

  loadAllTopics(): void {
    this.loader.show();
    this.userService.getTopicsByUser().subscribe(
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

  loadAllPosts(): void {
    this.loader.show();
    this.userService.getPostsByUser().subscribe(
      (res: HttpResponse<ITopicOrPost[]>) => {
        this.posts = res.body!;
        this.loader.hide();
        // console.error(this.posts);
      },
      (err: HttpErrorResponse) => {
        console.error(err);
        this.loader.hide();
      }
    );
  }

  loadAllLikes(): void {
    this.loader.show();
    this.userService.getLikesByUser().subscribe(
      (res: HttpResponse<ITopicOrPost[]>) => {
        this.likedPost = res.body!;
        this.loader.hide();
        // console.error(this.posts);
      },
      (err: HttpErrorResponse) => {
        console.error(err);
        this.loader.hide();
      }
    );
  }

  loadAllHiddenPosts(): void {
    this.loader.show();
    this.userService.getHiddenPostsByUser().subscribe(
      (res: HttpResponse<ITopicOrPost[]>) => {
        this.hiddenPost = res.body!;
        this.loader.hide();
        // console.error(this.posts);
      },
      (err: HttpErrorResponse) => {
        console.error(err);
        this.loader.hide();
      }
    );
  }

  setActiveCls(eventObj: any, index: any): void {
    this.codeCp.setActiveClass(eventObj);
    this.selectedIndex = index;
    if (this.selectedIndex === 1) {
      this.loadAllPosts();
    }
  }
  showActivity(index: number): void {
    this.selectedIndex = index;
    if (index === 0) {
      this.loadAllTopics();
    }
    if (index === 1) {
      this.loadAllPosts();
    }
    if (index === 2) {
      this.loadAllLikes();
    }
    if (index === 3) {
      this.loadAllHiddenPosts();
    }
  }
  setActiveClsi(eventObj: any): void {
    this.codeCp.setActiveClass(eventObj);
  }

  ngOnDestroy(): void {
    this.getCategoryListServiceSubscription.unsubscribe();
    this.currentlyLoggedInUserServiceSubscription.unsubscribe();
  }
}
