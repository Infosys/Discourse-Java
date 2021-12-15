import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { ApplicationRequestsDetailComponent } from 'app/entities/application-requests/application-requests-detail.component';
import { ApplicationRequests } from 'app/shared/model/application-requests.model';

describe('Component Tests', () => {
  describe('ApplicationRequests Management Detail Component', () => {
    let comp: ApplicationRequestsDetailComponent;
    let fixture: ComponentFixture<ApplicationRequestsDetailComponent>;
    const route = ({ data: of({ applicationRequests: new ApplicationRequests(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ApplicationRequestsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ApplicationRequestsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ApplicationRequestsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load applicationRequests on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.applicationRequests).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
