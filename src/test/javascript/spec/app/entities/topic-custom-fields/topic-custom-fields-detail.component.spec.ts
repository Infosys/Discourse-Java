import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TopicCustomFieldsDetailComponent } from 'app/entities/topic-custom-fields/topic-custom-fields-detail.component';
import { TopicCustomFields } from 'app/shared/model/topic-custom-fields.model';

describe('Component Tests', () => {
  describe('TopicCustomFields Management Detail Component', () => {
    let comp: TopicCustomFieldsDetailComponent;
    let fixture: ComponentFixture<TopicCustomFieldsDetailComponent>;
    const route = ({ data: of({ topicCustomFields: new TopicCustomFields(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TopicCustomFieldsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TopicCustomFieldsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TopicCustomFieldsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load topicCustomFields on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.topicCustomFields).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
