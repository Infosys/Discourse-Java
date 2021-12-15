import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { DiscourseTestModule } from '../../../test.module';
import { BadgePostsDetailComponent } from 'app/entities/badge-posts/badge-posts-detail.component';
import { BadgePosts } from 'app/shared/model/badge-posts.model';

describe('Component Tests', () => {
  describe('BadgePosts Management Detail Component', () => {
    let comp: BadgePostsDetailComponent;
    let fixture: ComponentFixture<BadgePostsDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ badgePosts: new BadgePosts(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [BadgePostsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BadgePostsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BadgePostsDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load badgePosts on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.badgePosts).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
