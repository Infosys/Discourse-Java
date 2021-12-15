import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { BadgeGroupingsDetailComponent } from 'app/entities/badge-groupings/badge-groupings-detail.component';
import { BadgeGroupings } from 'app/shared/model/badge-groupings.model';

describe('Component Tests', () => {
  describe('BadgeGroupings Management Detail Component', () => {
    let comp: BadgeGroupingsDetailComponent;
    let fixture: ComponentFixture<BadgeGroupingsDetailComponent>;
    const route = ({ data: of({ badgeGroupings: new BadgeGroupings(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [BadgeGroupingsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BadgeGroupingsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BadgeGroupingsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load badgeGroupings on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.badgeGroupings).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
