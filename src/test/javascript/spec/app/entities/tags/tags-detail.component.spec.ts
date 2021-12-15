import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TagsDetailComponent } from 'app/entities/tags/tags-detail.component';
import { Tags } from 'app/shared/model/tags.model';

describe('Component Tests', () => {
  describe('Tags Management Detail Component', () => {
    let comp: TagsDetailComponent;
    let fixture: ComponentFixture<TagsDetailComponent>;
    const route = ({ data: of({ tags: new Tags(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TagsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TagsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TagsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tags on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tags).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
