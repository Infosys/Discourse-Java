import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TagSearchDataDetailComponent } from 'app/entities/tag-search-data/tag-search-data-detail.component';
import { TagSearchData } from 'app/shared/model/tag-search-data.model';

describe('Component Tests', () => {
  describe('TagSearchData Management Detail Component', () => {
    let comp: TagSearchDataDetailComponent;
    let fixture: ComponentFixture<TagSearchDataDetailComponent>;
    const route = ({ data: of({ tagSearchData: new TagSearchData(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TagSearchDataDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TagSearchDataDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TagSearchDataDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tagSearchData on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tagSearchData).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
