import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { AngularEditorConfig } from '@kolkov/angular-editor';
import { ICategory } from '../../shared/model/category/category.model';
import { GetCategoriesService } from 'app/shared/services/get-categories.service';
import { Subscription } from 'rxjs';
import { IRichTextEditorConfig } from '../../shared/model/rich-text-editor-config.model';
import { RichTextEditorType } from 'app/shared/model/enumerations/rich-text-editor-type.model';
import { IRichTextEditorValues } from 'app/shared/model/rich-text-editor-values.model';
import { ENTER, COMMA } from '@angular/cdk/keycodes';
import { MatChipInputEvent } from '@angular/material/chips';
import { GetUserService } from 'app/shared/services/get-user.service';
import { IUser } from 'app/shared/model/user/user.model';
import { SearchService } from 'app/shared/services/search.service';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ITopicOrPost } from 'app/shared/model/topic-or-post.model';
import { Router } from '@angular/router';

@Component({
  selector: 'jhi-angular-rich-text-editor',
  templateUrl: './angular-rich-text-editor.component.html',
  styleUrls: ['./angular-rich-text-editor.component.scss'],
})
export class AngularRichTextEditorComponent implements OnInit, OnDestroy {
  @Input() topicConfig!: IRichTextEditorConfig;

  @Input() showEditor = false;
  @Output() showEditorChange: EventEmitter<boolean> = new EventEmitter<boolean>();

  @Input() enableAnnouncement = false;

  @Output() onSubmitClick: EventEmitter<any> = new EventEmitter<any>();

  currentlyLoggedInUser!: IUser;
  tags: string[] = [];
  selectable = true;
  removable = true;
  addOnBlur = true;
  readonly separatorKeysCodes: number[] = [ENTER, COMMA];
  announcementFlag = false;
  searchResults: ITopicOrPost[] = [];
  showSearchResultBox = false;

  richTextEditorType = {
    TOPIC: RichTextEditorType.TOPIC,
    REPLY: RichTextEditorType.REPLY,
    EDIT_TOPIC_DISABLE_RTE: RichTextEditorType.EDIT_TOPIC_DISABLE_RTE,
  };

  editorConfig: AngularEditorConfig = {
    editable: true,
    spellcheck: true,
    height: '225px',
    minHeight: '225px',
    maxHeight: '225px',
    width: '100%',
    minWidth: '100%',
    translate: 'yes',
    enableToolbar: true,
    showToolbar: true,
    placeholder: 'Enter text here...',
    defaultParagraphSeparator: '',
    defaultFontName: '',
    defaultFontSize: '',
    fonts: [
      { class: 'arial', name: 'Arial' },
      { class: 'times-new-roman', name: 'Times New Roman' },
      { class: 'calibri', name: 'Calibri' },
      { class: 'comic-sans-ms', name: 'Comic Sans MS' },
    ],
    customClasses: [
      {
        name: 'quote',
        class: 'quote',
      },
      {
        name: 'redText',
        class: 'redText',
      },
      {
        name: 'titleText',
        class: 'titleText',
        tag: 'h1',
      },
    ],
    uploadUrl: 'https://forums.surveymaster.in/forums-filems/api/file/sync-upload?doc_type=FORUMS',
    uploadWithCredentials: false,
    sanitize: true,
    toolbarPosition: 'top',
    toolbarHiddenButtons: [['bold', 'italic'], ['fontSize']],
  };
  categories: ICategory[] = [];
  getCategoryJsonServiceSubscription: Subscription;
  getCategoryListServiceSubscription: Subscription;
  categoriesJson = {};
  categoryNameList: string[] = [];
  getUserServiceSubscription: Subscription;

  constructor(
    private getCategoriesService: GetCategoriesService,
    private getUserService: GetUserService,
    private searchService: SearchService,
    private router: Router
  ) {
    this.getCategoryJsonServiceSubscription = this.getCategoriesService.categoriesJson.subscribe(json => {
      this.categoriesJson = json;
    });
    this.getCategoryListServiceSubscription = this.getCategoriesService.categoryList.subscribe((list: ICategory[]) => {
      this.categories = list;
    });
    this.getUserServiceSubscription = this.getUserService.currentlyLoggedInUser.subscribe(user => {
      this.currentlyLoggedInUser = user;
    });
  }

  ngOnInit(): void {
    this.topicConfig = {
      editorType: this.topicConfig?.editorType ? this.topicConfig.editorType : RichTextEditorType.TOPIC,
      values: {
        title: this.topicConfig?.values?.title ? this.topicConfig.values?.title : '',
        categoryId: this.topicConfig?.values?.categoryId ? this.topicConfig.values?.categoryId : '-1',
        tags: this.topicConfig?.values?.tags ? this.topicConfig.values?.tags : '',
        htmlContent: this.topicConfig?.values?.htmlContent ? this.topicConfig.values?.htmlContent : '',
      },
      submitButtonText: this.topicConfig?.submitButtonText ? this.topicConfig.submitButtonText : 'Create',
      heading: this.topicConfig?.heading ? this.topicConfig.heading : '',
    };
  }

  closeEditor(): void {
    this.showEditorChange.emit(false);
  }

  submit(): void {
    if (!this.announcementFlag) {
      if (this.topicConfig.editorType === RichTextEditorType.EDIT_TOPIC_DISABLE_RTE || RichTextEditorType.TOPIC) {
        const topic: IRichTextEditorValues = {};
        topic['title'] = this.topicConfig.values!.title!;
        topic['htmlContent'] = this.topicConfig.values!.htmlContent!;
        topic['tags'] = this.topicConfig.values!.tags!;
        topic['categoryId'] = this.topicConfig.values!.categoryId!;
        this.onSubmitClick.emit(topic);
      } else if (this.topicConfig.editorType === RichTextEditorType.REPLY) {
        const post: IRichTextEditorValues = {};
        post['htmlContent'] = this.topicConfig.values!.htmlContent!;
        this.onSubmitClick.emit(post);
      }
    } else {
      if (this.currentlyLoggedInUser.admin && this.enableAnnouncement) {
        const announcement: IRichTextEditorValues = {};
        announcement['title'] = this.topicConfig.values!.title!;
        announcement['htmlContent'] = this.topicConfig.values!.htmlContent!;
        announcement['categoryId'] = null;
        this.onSubmitClick.emit(announcement);
      }
    }
  }

  add(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;
    if ((value || '').trim()) {
      this.tags.push(value.trim());
    }
    if (input) {
      input.value = '';
    }
  }

  remove(tag: string): void {
    const index = this.tags.indexOf(tag);

    if (index >= 0) {
      this.tags.splice(index, 1);
    }
  }

  search(): void {
    if (
      this.topicConfig.values!.title!.length > 3 &&
      (this.topicConfig.editorType === RichTextEditorType.TOPIC ||
        this.topicConfig.editorType === RichTextEditorType.EDIT_TOPIC_DISABLE_RTE)
    ) {
      this.searchService.searchTopicByTitle(this.topicConfig.values!.title!).subscribe(
        (res: HttpResponse<ITopicOrPost[]>) => {
          this.searchResults = res.body!;
          if (this.searchResults.length > 0) {
            this.showSearchResults();
          }
        },
        (err: HttpErrorResponse) => {
          console.error(err);
        }
      );
    } else {
      this.searchResults = [];
    }
  }

  navigateToTopic(topicId: string): void {
    this.hideSearchResults();
    this.router.navigateByUrl(`topic/${topicId}`);
  }

  hideSearchResults(): void {
    this.showSearchResultBox = false;
  }

  showSearchResults(): void {
    this.showSearchResultBox = true;
  }

  ngOnDestroy(): void {
    this.getCategoryJsonServiceSubscription.unsubscribe();
    this.getCategoryListServiceSubscription.unsubscribe();
    this.getUserServiceSubscription.unsubscribe();
  }
}
