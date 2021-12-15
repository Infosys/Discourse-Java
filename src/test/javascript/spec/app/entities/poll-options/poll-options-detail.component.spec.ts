import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { PollOptionsDetailComponent } from 'app/entities/poll-options/poll-options-detail.component';
import { PollOptions } from 'app/shared/model/poll-options.model';

describe('Component Tests', () => {
  describe('PollOptions Management Detail Component', () => {
    let comp: PollOptionsDetailComponent;
    let fixture: ComponentFixture<PollOptionsDetailComponent>;
    const route = ({ data: of({ pollOptions: new PollOptions(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [PollOptionsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PollOptionsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PollOptionsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pollOptions on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pollOptions).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
