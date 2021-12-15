import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { GivenDailyLikesDetailComponent } from 'app/entities/given-daily-likes/given-daily-likes-detail.component';
import { GivenDailyLikes } from 'app/shared/model/given-daily-likes.model';

describe('Component Tests', () => {
  describe('GivenDailyLikes Management Detail Component', () => {
    let comp: GivenDailyLikesDetailComponent;
    let fixture: ComponentFixture<GivenDailyLikesDetailComponent>;
    const route = ({ data: of({ givenDailyLikes: new GivenDailyLikes(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [GivenDailyLikesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(GivenDailyLikesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GivenDailyLikesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load givenDailyLikes on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.givenDailyLikes).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
