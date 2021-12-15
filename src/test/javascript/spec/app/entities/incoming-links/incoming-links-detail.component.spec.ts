import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { IncomingLinksDetailComponent } from 'app/entities/incoming-links/incoming-links-detail.component';
import { IncomingLinks } from 'app/shared/model/incoming-links.model';

describe('Component Tests', () => {
  describe('IncomingLinks Management Detail Component', () => {
    let comp: IncomingLinksDetailComponent;
    let fixture: ComponentFixture<IncomingLinksDetailComponent>;
    const route = ({ data: of({ incomingLinks: new IncomingLinks(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [IncomingLinksDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(IncomingLinksDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IncomingLinksDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load incomingLinks on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.incomingLinks).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
