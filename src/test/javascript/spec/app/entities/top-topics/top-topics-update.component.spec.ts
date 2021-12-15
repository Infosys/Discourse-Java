import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TopTopicsUpdateComponent } from 'app/entities/top-topics/top-topics-update.component';
import { TopTopicsService } from 'app/entities/top-topics/top-topics.service';
import { TopTopics } from 'app/shared/model/top-topics.model';

describe('Component Tests', () => {
  describe('TopTopics Management Update Component', () => {
    let comp: TopTopicsUpdateComponent;
    let fixture: ComponentFixture<TopTopicsUpdateComponent>;
    let service: TopTopicsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TopTopicsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TopTopicsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TopTopicsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TopTopicsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TopTopics(123);
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
        const entity = new TopTopics();
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
