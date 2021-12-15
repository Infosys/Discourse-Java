import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { PostTimingsDetailComponent } from 'app/entities/post-timings/post-timings-detail.component';
import { PostTimings } from 'app/shared/model/post-timings.model';

describe('Component Tests', () => {
  describe('PostTimings Management Detail Component', () => {
    let comp: PostTimingsDetailComponent;
    let fixture: ComponentFixture<PostTimingsDetailComponent>;
    const route = ({ data: of({ postTimings: new PostTimings(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [PostTimingsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PostTimingsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PostTimingsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load postTimings on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.postTimings).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
