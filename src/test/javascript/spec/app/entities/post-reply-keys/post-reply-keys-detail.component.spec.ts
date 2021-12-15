import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { PostReplyKeysDetailComponent } from 'app/entities/post-reply-keys/post-reply-keys-detail.component';
import { PostReplyKeys } from 'app/shared/model/post-reply-keys.model';

describe('Component Tests', () => {
  describe('PostReplyKeys Management Detail Component', () => {
    let comp: PostReplyKeysDetailComponent;
    let fixture: ComponentFixture<PostReplyKeysDetailComponent>;
    const route = ({ data: of({ postReplyKeys: new PostReplyKeys(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [PostReplyKeysDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PostReplyKeysDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PostReplyKeysDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load postReplyKeys on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.postReplyKeys).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
