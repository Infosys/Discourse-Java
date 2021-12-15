import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { LinkedTopicsUpdateComponent } from 'app/entities/linked-topics/linked-topics-update.component';
import { LinkedTopicsService } from 'app/entities/linked-topics/linked-topics.service';
import { LinkedTopics } from 'app/shared/model/linked-topics.model';

describe('Component Tests', () => {
  describe('LinkedTopics Management Update Component', () => {
    let comp: LinkedTopicsUpdateComponent;
    let fixture: ComponentFixture<LinkedTopicsUpdateComponent>;
    let service: LinkedTopicsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [LinkedTopicsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(LinkedTopicsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LinkedTopicsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LinkedTopicsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LinkedTopics(123);
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
        const entity = new LinkedTopics();
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
