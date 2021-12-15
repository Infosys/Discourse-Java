import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { DraftsDetailComponent } from 'app/entities/drafts/drafts-detail.component';
import { Drafts } from 'app/shared/model/drafts.model';

describe('Component Tests', () => {
  describe('Drafts Management Detail Component', () => {
    let comp: DraftsDetailComponent;
    let fixture: ComponentFixture<DraftsDetailComponent>;
    const route = ({ data: of({ drafts: new Drafts(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [DraftsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DraftsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DraftsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load drafts on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.drafts).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
