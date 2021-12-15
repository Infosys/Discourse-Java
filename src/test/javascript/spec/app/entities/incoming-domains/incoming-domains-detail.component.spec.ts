import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { IncomingDomainsDetailComponent } from 'app/entities/incoming-domains/incoming-domains-detail.component';
import { IncomingDomains } from 'app/shared/model/incoming-domains.model';

describe('Component Tests', () => {
  describe('IncomingDomains Management Detail Component', () => {
    let comp: IncomingDomainsDetailComponent;
    let fixture: ComponentFixture<IncomingDomainsDetailComponent>;
    const route = ({ data: of({ incomingDomains: new IncomingDomains(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [IncomingDomainsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(IncomingDomainsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IncomingDomainsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load incomingDomains on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.incomingDomains).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
