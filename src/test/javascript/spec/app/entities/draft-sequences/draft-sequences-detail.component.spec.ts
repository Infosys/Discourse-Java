import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { DraftSequencesDetailComponent } from 'app/entities/draft-sequences/draft-sequences-detail.component';
import { DraftSequences } from 'app/shared/model/draft-sequences.model';

describe('Component Tests', () => {
  describe('DraftSequences Management Detail Component', () => {
    let comp: DraftSequencesDetailComponent;
    let fixture: ComponentFixture<DraftSequencesDetailComponent>;
    const route = ({ data: of({ draftSequences: new DraftSequences(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [DraftSequencesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DraftSequencesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DraftSequencesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load draftSequences on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.draftSequences).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
