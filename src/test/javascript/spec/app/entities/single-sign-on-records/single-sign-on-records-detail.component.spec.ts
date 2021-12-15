import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { SingleSignOnRecordsDetailComponent } from 'app/entities/single-sign-on-records/single-sign-on-records-detail.component';
import { SingleSignOnRecords } from 'app/shared/model/single-sign-on-records.model';

describe('Component Tests', () => {
  describe('SingleSignOnRecords Management Detail Component', () => {
    let comp: SingleSignOnRecordsDetailComponent;
    let fixture: ComponentFixture<SingleSignOnRecordsDetailComponent>;
    const route = ({ data: of({ singleSignOnRecords: new SingleSignOnRecords(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [SingleSignOnRecordsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SingleSignOnRecordsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SingleSignOnRecordsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load singleSignOnRecords on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.singleSignOnRecords).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
