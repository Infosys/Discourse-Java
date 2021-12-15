import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { IncomingReferersDetailComponent } from 'app/entities/incoming-referers/incoming-referers-detail.component';
import { IncomingReferers } from 'app/shared/model/incoming-referers.model';

describe('Component Tests', () => {
  describe('IncomingReferers Management Detail Component', () => {
    let comp: IncomingReferersDetailComponent;
    let fixture: ComponentFixture<IncomingReferersDetailComponent>;
    const route = ({ data: of({ incomingReferers: new IncomingReferers(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [IncomingReferersDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(IncomingReferersDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IncomingReferersDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load incomingReferers on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.incomingReferers).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
