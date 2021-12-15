import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TopTopicsDetailComponent } from 'app/entities/top-topics/top-topics-detail.component';
import { TopTopics } from 'app/shared/model/top-topics.model';

describe('Component Tests', () => {
  describe('TopTopics Management Detail Component', () => {
    let comp: TopTopicsDetailComponent;
    let fixture: ComponentFixture<TopTopicsDetailComponent>;
    const route = ({ data: of({ topTopics: new TopTopics(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TopTopicsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TopTopicsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TopTopicsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load topTopics on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.topTopics).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
