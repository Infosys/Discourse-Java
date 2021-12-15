import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { BadgeTypesDetailComponent } from 'app/entities/badge-types/badge-types-detail.component';
import { BadgeTypes } from 'app/shared/model/badge-types.model';

describe('Component Tests', () => {
  describe('BadgeTypes Management Detail Component', () => {
    let comp: BadgeTypesDetailComponent;
    let fixture: ComponentFixture<BadgeTypesDetailComponent>;
    const route = ({ data: of({ badgeTypes: new BadgeTypes(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [BadgeTypesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BadgeTypesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BadgeTypesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load badgeTypes on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.badgeTypes).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
