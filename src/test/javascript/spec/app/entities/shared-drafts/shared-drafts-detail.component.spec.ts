import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { SharedDraftsDetailComponent } from 'app/entities/shared-drafts/shared-drafts-detail.component';
import { SharedDrafts } from 'app/shared/model/shared-drafts.model';

describe('Component Tests', () => {
  describe('SharedDrafts Management Detail Component', () => {
    let comp: SharedDraftsDetailComponent;
    let fixture: ComponentFixture<SharedDraftsDetailComponent>;
    const route = ({ data: of({ sharedDrafts: new SharedDrafts(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [SharedDraftsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SharedDraftsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SharedDraftsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load sharedDrafts on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sharedDrafts).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
