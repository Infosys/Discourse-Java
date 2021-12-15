import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TopicsDetailComponent } from 'app/entities/topics/topics-detail.component';
import { Topics } from 'app/shared/model/topics.model';

describe('Component Tests', () => {
  describe('Topics Management Detail Component', () => {
    let comp: TopicsDetailComponent;
    let fixture: ComponentFixture<TopicsDetailComponent>;
    const route = ({ data: of({ topics: new Topics(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TopicsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TopicsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TopicsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load topics on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.topics).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
