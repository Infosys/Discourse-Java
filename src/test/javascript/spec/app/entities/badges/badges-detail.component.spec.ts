import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { BadgesDetailComponent } from 'app/entities/badges/badges-detail.component';
import { Badges } from 'app/shared/model/badges.model';

describe('Component Tests', () => {
  describe('Badges Management Detail Component', () => {
    let comp: BadgesDetailComponent;
    let fixture: ComponentFixture<BadgesDetailComponent>;
    const route = ({ data: of({ badges: new Badges(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [BadgesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BadgesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BadgesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load badges on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.badges).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
