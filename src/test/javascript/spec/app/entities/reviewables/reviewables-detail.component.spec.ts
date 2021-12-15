import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { ReviewablesDetailComponent } from 'app/entities/reviewables/reviewables-detail.component';
import { Reviewables } from 'app/shared/model/reviewables.model';

describe('Component Tests', () => {
  describe('Reviewables Management Detail Component', () => {
    let comp: ReviewablesDetailComponent;
    let fixture: ComponentFixture<ReviewablesDetailComponent>;
    const route = ({ data: of({ reviewables: new Reviewables(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ReviewablesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ReviewablesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReviewablesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load reviewables on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.reviewables).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
