import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { LinkedTopicsDetailComponent } from 'app/entities/linked-topics/linked-topics-detail.component';
import { LinkedTopics } from 'app/shared/model/linked-topics.model';

describe('Component Tests', () => {
  describe('LinkedTopics Management Detail Component', () => {
    let comp: LinkedTopicsDetailComponent;
    let fixture: ComponentFixture<LinkedTopicsDetailComponent>;
    const route = ({ data: of({ linkedTopics: new LinkedTopics(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [LinkedTopicsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(LinkedTopicsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LinkedTopicsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load linkedTopics on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.linkedTopics).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
