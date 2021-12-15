import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ICategory } from 'app/shared/model/category/category.model';
import { ActivatedRoute, Router } from '@angular/router';
import { PostService } from 'app/shared/services/post.service';
import { ITopicOrPost } from 'app/shared/model/topic-or-post.model';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { IRichTextEditorValues } from 'app/shared/model/rich-text-editor-values.model';
import { IRichTextEditorConfig } from 'app/shared/model/rich-text-editor-config.model';
import { RichTextEditorType } from 'app/shared/model/enumerations/rich-text-editor-type.model';
import { TopicService } from 'app/shared/services/topic.service';
import { NewTopicPostPrivateMessageService } from 'app/shared/services/new-topic-post-private-message.service';
import { ITopicResponse } from 'app/shared/model/topic/topic-response.model';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatSelect } from '@angular/material/select';
import { forkJoin, Observable, Subscription } from 'rxjs';
import { IUser } from 'app/shared/model/user/user.model';
import { GetUserService } from 'app/shared/services/get-user.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { setToArray } from '../../shared/util/set-to-array';
import { NgxSpinnerService } from 'ngx-spinner';
@Component({
  selector: 'jhi-view-topic-discussion',
  templateUrl: './view-topic-discussion.component.html',
  styleUrls: ['./view-topic-discussion.component.scss'],
})
export class ViewTopicDiscussionComponent implements OnInit, OnDestroy {
  currentlyLoggedInUser!: IUser;
  currentlyLoggedInUserServiceSubscription: Subscription;
  topic!: ITopicOrPost;
  topicPrimaryPost!: ITopicOrPost;
  topicId!: string;
  repliesJson: any = {};
  category!: ICategory;
  tags: string[] = [];

  posts: ITopicOrPost[] = [];
  showEditor = false;
  richTextEditorConfig!: IRichTextEditorConfig;
  parentValueJsonForNestedPosts = {
    topic: 'TOPIC',
    post: 'POST',
  };
  submitEditorContentConfig!: {
    type: 'EDIT' | 'REPLY';
    replyToIdOrEditId: string;
    replyToOrEdit: 'TOPIC' | 'POST';
    metaData?: any;
  };

  sorting!: FormGroup;
  sortingTypes!: any[];
  showCheckboxesForHiding = false;
  showCheckboxesForUnhiding = false;
  hideUnhidePostsIdsSet = new Set();
  showTickCloseForAdmin = false;
  postToDeleteId!: string;

  @ViewChild('sortingSelector') sortingSelector!: MatSelect;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private postService: PostService,
    private topicService: TopicService,
    private newTopicPostPrivateMessageService: NewTopicPostPrivateMessageService,
    private formBuilder: FormBuilder,
    private getUserService: GetUserService,
    private modalService: NgbModal,
    private loader: NgxSpinnerService
  ) {
    this.currentlyLoggedInUserServiceSubscription = this.getUserService.currentlyLoggedInUser.subscribe(user => {
      this.currentlyLoggedInUser = user;
    });
  }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      if (params['id']) {
        this.loader.show();
        this.topicId = params['id'];
        this.initialiseSort();
        this.loadTopic();
      } else {
        this.router.navigateByUrl('/');
      }
    });
    this.repliesJson = {};
  }

  sort(): void {
    this.repliesJson = {};
    this.loadPosts();
  }

  initialiseSort(): void {
    this.sorting = this.formBuilder.group({
      sorting: ['default'],
    });
    this.sortingTypes = [
      {
        type: 'Default',
        value: 'default',
      },
      {
        type: 'Replies',
        value: 'replyCount,desc',
      },
      {
        type: 'Likes',
        value: 'likeCount,desc',
      },
    ];
  }

  openSortingMatSelect(): void {
    this.sortingSelector.open();
  }

  intialiseTitleEditAndCategory(): void {
    this.richTextEditorConfig = {
      editorType: RichTextEditorType.EDIT_TOPIC_DISABLE_RTE,
      submitButtonText: 'Save',
      values: {
        title: this.topic.name!,
        categoryId: this.topic.category_id!,
        tags: this.topic.tags!,
      },
    };
    this.submitEditorContentConfig = {
      type: 'EDIT',
      replyToIdOrEditId: this.topicId,
      replyToOrEdit: 'TOPIC',
    };
    this.showEditor = true;
  }

  initialiseTopicPrimaryPostEdit(): void {
    this.richTextEditorConfig = {
      editorType: RichTextEditorType.REPLY,
      submitButtonText: 'Save',
      values: {
        htmlContent: this.topicPrimaryPost.cooked!,
      },
    };
    this.submitEditorContentConfig = {
      type: 'EDIT',
      replyToIdOrEditId: this.topicPrimaryPost.id!,
      replyToOrEdit: 'POST',
      metaData: {
        primaryPost: true,
      },
    };
    this.showEditor = true;
  }

  initialisePostEdit(index: number, id: string, parent: string, parentId: string): void {
    let htmlContent;
    if (parent === this.parentValueJsonForNestedPosts.topic) {
      htmlContent = this.posts[index].cooked;
    } else {
      htmlContent = this.repliesJson[parentId].replies[index].cooked;
    }
    this.richTextEditorConfig = {
      editorType: RichTextEditorType.REPLY,
      submitButtonText: 'Save',
      values: {
        htmlContent,
      },
    };
    this.submitEditorContentConfig = {
      type: 'EDIT',
      replyToIdOrEditId: id,
      replyToOrEdit: 'POST',
      metaData: {
        primaryPost: false,
        index,
        parent,
        parentId,
      },
    };
    this.showEditor = true;
  }

  initialiseCreatePostForTopic(): void {
    this.richTextEditorConfig = {
      editorType: RichTextEditorType.REPLY,
      submitButtonText: 'Reply',
      values: {
        htmlContent: '',
      },
    };
    this.submitEditorContentConfig = {
      type: 'REPLY',
      replyToIdOrEditId: this.topicId,
      replyToOrEdit: 'TOPIC',
    };
    this.showEditor = true;
  }

  initialiseReplyToPost(index: number, postId: string, parent: string, parentId: string): void {
    this.richTextEditorConfig = {
      editorType: RichTextEditorType.REPLY,
      submitButtonText: 'Reply',
      values: {
        htmlContent: '',
      },
    };
    this.submitEditorContentConfig = {
      type: 'REPLY',
      replyToIdOrEditId: postId,
      replyToOrEdit: 'POST',
      metaData: {
        index,
        parent,
        parentId,
      },
    };
    this.showEditor = true;
  }

  loadTopic(): void {
    this.topicService.getTopicById(this.topicId).subscribe(
      (res: HttpResponse<ITopicResponse>) => {
        this.topic = res.body!.topic!;
        this.topicPrimaryPost = res.body!.posts!;
        this.tags = this.topic.tags?.split(',')!;
        this.loadPosts();
      },
      (err: HttpErrorResponse) => {
        console.error(err);
        this.router.navigateByUrl('/');
      }
    );
  }

  loadPosts(closeEditor?: boolean): void {
    this.loader.show();
    const reqOptions = {};
    if (this.sorting.controls['sorting'].value !== 'default') {
      reqOptions['sort'] = [this.sorting.controls['sorting'].value];
    }
    this.postService.getPostByTopic(this.topicId, reqOptions).subscribe(
      (res: HttpResponse<ITopicOrPost[]>) => {
        this.posts = res.body!;
        if (closeEditor) {
          this.hideEditor();
        }
        this.loader.hide();
      },
      (err: HttpErrorResponse) => {
        console.error(err);
        this.loader.hide();
      }
    );
  }

  viewReplies(id: string, newReply?: boolean): void {
    if (this.repliesJson[id] && this.repliesJson[id].showReplies && !newReply) {
      this.repliesJson[id].showReplies = false;
    } else {
      this.repliesJson[id] = {
        showReplies: false,
        replies: [],
      };
      this.loadReplies(String(id), newReply);
    }
  }

  loadReplies(postId: string, closeEditor?: boolean): void {
    this.loader.show();
    this.postService.getRepliesById(postId).subscribe(
      (res: HttpResponse<ITopicOrPost[]>) => {
        this.repliesJson[postId].showReplies = true;
        this.repliesJson[postId].replies = res.body!;
        if (closeEditor) {
          this.hideEditor();
        }
        this.loader.hide();
      },
      (err: HttpErrorResponse) => {
        console.error(err);
        this.loader.hide();
      }
    );
  }

  likeUnlikePost(userSameAsPostUserFlag: boolean, postId: string, newLikeStatus: boolean, index?: number, parentId?: string): void {
    if (!userSameAsPostUserFlag) {
      (newLikeStatus ? this.postService.likePost(postId) : this.postService.unlikePost(postId)).subscribe(
        () => {
          if (typeof index === 'undefined') {
            this.topicPrimaryPost.current_user_like = newLikeStatus;
            newLikeStatus ? (this.topicPrimaryPost.like_count! += 1) : (this.topicPrimaryPost.like_count! -= 1);
          } else if (typeof index === 'number' && typeof parentId !== 'undefined') {
            if (parentId === '-1') {
              this.posts[index].current_user_like = newLikeStatus;
              newLikeStatus ? (this.posts[index].like_count! += 1) : (this.posts[index].like_count! -= 1);
              if (
                this.repliesJson[this.posts[index].reply_to_post_number!] &&
                this.repliesJson[this.posts[index].reply_to_post_number!].replies
              ) {
                for (let i = 0; i < this.repliesJson[this.posts[index].reply_to_post_number!].replies.length; i++) {
                  if (this.repliesJson[this.posts[index].reply_to_post_number!].replies[i].id === postId) {
                    this.repliesJson[this.posts[index].reply_to_post_number!].replies[i].current_user_like = newLikeStatus;
                    newLikeStatus
                      ? (this.repliesJson[this.posts[index].reply_to_post_number!].replies[i].like_count += 1)
                      : (this.repliesJson[this.posts[index].reply_to_post_number!].replies[i].like_count -= 1);
                    break;
                  }
                }
              }
            } else if (parentId !== '-1') {
              this.repliesJson[parentId].replies[index].current_user_like = newLikeStatus;
              newLikeStatus
                ? (this.repliesJson[parentId].replies[index].like_count += 1)
                : (this.repliesJson[parentId].replies[index].like_count -= 1);
              for (let i = 0; i < this.posts.length; i++) {
                if (this.posts[i].id === postId) {
                  this.posts[i].current_user_like = newLikeStatus;
                  newLikeStatus ? (this.posts[i].like_count! += 1) : (this.posts[i].like_count! -= 1);
                  break;
                }
              }
            }
          }
          newLikeStatus ? (this.topic.like_count! += 1) : (this.topic.like_count! -= 1);
        },
        (err: HttpErrorResponse) => {
          console.error(err);
        }
      );
    }
  }

  submitEditorContent(editorValues: IRichTextEditorValues): void {
    if (this.submitEditorContentConfig.type === 'REPLY') {
      const newPost = {};
      newPost['raw'] = editorValues.htmlContent;
      newPost['topic_id'] = this.topicId;
      if (this.submitEditorContentConfig.replyToOrEdit === 'POST') {
        newPost['reply_to_post'] = String(this.submitEditorContentConfig.replyToIdOrEditId);
      }
      this.loader.show();
      this.newTopicPostPrivateMessageService.createTopicPostPrivateMessage(newPost).subscribe(
        () => {
          if (this.submitEditorContentConfig.replyToOrEdit === 'POST') {
            const metaData = this.submitEditorContentConfig.metaData;
            if (metaData.parent === this.parentValueJsonForNestedPosts.topic) {
              this.posts[metaData.index].reply_count! += 1;
            } else {
              this.repliesJson[metaData.parentId].replies[metaData.index].reply_count += 1;
            }
            this.viewReplies(this.submitEditorContentConfig.replyToIdOrEditId, true);
          }
          if (this.submitEditorContentConfig.replyToOrEdit === 'TOPIC') {
            this.loadPosts(true);
          }
          this.topic.post_number! += 1;
          this.hideEditor();
          this.loader.hide();
        },
        (err: HttpErrorResponse) => {
          console.error(err);
          this.loader.hide();
        }
      );
    }
    if (this.submitEditorContentConfig.type === 'EDIT') {
      if (this.submitEditorContentConfig.replyToOrEdit === 'TOPIC') {
        const newDetails = {
          category_id: editorValues.categoryId,
          title: editorValues.title,
          tags: editorValues.tags,
        };
        this.loader.show();
        this.topicService.updateTopic(this.topicId, newDetails).subscribe(
          () => {
            this.topic.name = newDetails.title!;
            this.topic.category_id = newDetails.category_id!;
            this.topic.tags = newDetails.tags!;
            this.tags = newDetails.tags?.split(',')!;
            this.hideEditor();
            this.loader.hide();
          },
          (err: HttpErrorResponse) => {
            console.error(err);
            this.loader.hide();
          }
        );
      }
      if (this.submitEditorContentConfig.replyToOrEdit === 'POST') {
        const newDetails = {
          post: {
            raw: editorValues.htmlContent,
          },
        };
        this.loader.show();
        this.postService.updatePost(this.submitEditorContentConfig.replyToIdOrEditId, newDetails).subscribe(
          () => {
            if (this.submitEditorContentConfig.metaData.primaryPost === true) {
              this.topicPrimaryPost.cooked = newDetails.post.raw!;
            } else {
              if (this.submitEditorContentConfig.metaData.parent === this.parentValueJsonForNestedPosts.topic) {
                this.posts[this.submitEditorContentConfig.metaData.index].cooked = newDetails.post.raw!;
              } else {
                this.repliesJson[this.submitEditorContentConfig.metaData.parentId].replies[
                  this.submitEditorContentConfig.metaData.index
                ].cooked = newDetails.post.raw;
              }
            }
            this.hideEditor();
            this.loader.hide();
          },
          (err: HttpErrorResponse) => {
            console.error(err);
            this.loader.hide();
          }
        );
      }
    }
  }

  hideEditor(): void {
    this.showEditor = false;
  }

  initialiseHideUnhidePosts(hideFlag: boolean): void {
    // hideFlag = true for hiding posts
    // hideFlag = false for unhiding posts
    this.hideUnhidePostsIdsSet.clear();
    this.showTickCloseForAdmin = true;
    this.showCheckboxesForHiding = hideFlag ? true : false;
    this.showCheckboxesForUnhiding = hideFlag ? false : true;
    this.modalService.dismissAll();
  }

  selectHideUnhideChange(isChecked: boolean, postId: string): void {
    isChecked ? this.hideUnhidePostsIdsSet.add(postId) : this.hideUnhidePostsIdsSet.delete(postId);
  }

  cancelHideUnhide(): void {
    this.hideUnhidePostsIdsSet.clear();
    this.showCheckboxesForHiding = false;
    this.showCheckboxesForUnhiding = false;
    this.showTickCloseForAdmin = false;
  }

  hideUnhidePosts(): void {
    this.loader.show();
    const postsToHideUnhideArray: Observable<HttpResponse<any>>[] = [];
    const arrayOfIds = setToArray(this.hideUnhidePostsIdsSet);
    const hideFlag = this.showCheckboxesForHiding && !this.showCheckboxesForUnhiding;
    for (let i = 0; i < arrayOfIds.length; i++) {
      postsToHideUnhideArray.push(hideFlag ? this.postService.hidePost(arrayOfIds[i]) : this.postService.unhidePost(arrayOfIds[i]));
    }

    forkJoin(postsToHideUnhideArray).subscribe(
      () => {
        this.loadPosts();
        this.repliesJson = {};
      },
      (err: HttpErrorResponse) => {
        console.error(err);
        this.loader.hide();
      }
    );
  }

  deletePost(): void {
    this.loader.show();
    this.postService.deletePost(this.postToDeleteId).subscribe(
      () => {
        this.modalService.dismissAll();
        this.loadPosts();
        this.repliesJson = {};
      },
      (err: HttpErrorResponse) => {
        console.error(err);
        this.loader.hide();
      }
    );
  }

  openModal(content: any, postId?: string): void {
    if (typeof postId === 'undefined') {
      this.modalService.open(content, { windowClass: 'web_custom_modal list-modal-auto-height' });
    } else {
      this.postToDeleteId = postId;
      this.modalService.open(content, { windowClass: 'web_custom_modal DSA_modal-sm' });
    }
  }

  ngOnDestroy(): void {
    this.currentlyLoggedInUserServiceSubscription.unsubscribe();
  }
}
