import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TopicCustomFieldsUpdateComponent } from 'app/entities/topic-custom-fields/topic-custom-fields-update.component';
import { TopicCustomFieldsService } from 'app/entities/topic-custom-fields/topic-custom-fields.service';
import { TopicCustomFields } from 'app/shared/model/topic-custom-fields.model';

describe('Component Tests', () => {
  describe('TopicCustomFields Management Update Component', () => {
    let comp: TopicCustomFieldsUpdateComponent;
    let fixture: ComponentFixture<TopicCustomFieldsUpdateComponent>;
    let service: TopicCustomFieldsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TopicCustomFieldsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TopicCustomFieldsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TopicCustomFieldsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TopicCustomFieldsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TopicCustomFields(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new TopicCustomFields();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
