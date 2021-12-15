import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { PostCustomFieldsDetailComponent } from 'app/entities/post-custom-fields/post-custom-fields-detail.component';
import { PostCustomFields } from 'app/shared/model/post-custom-fields.model';

describe('Component Tests', () => {
  describe('PostCustomFields Management Detail Component', () => {
    let comp: PostCustomFieldsDetailComponent;
    let fixture: ComponentFixture<PostCustomFieldsDetailComponent>;
    const route = ({ data: of({ postCustomFields: new PostCustomFields(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [PostCustomFieldsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PostCustomFieldsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PostCustomFieldsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load postCustomFields on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.postCustomFields).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
