import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { PollOptionsUpdateComponent } from 'app/entities/poll-options/poll-options-update.component';
import { PollOptionsService } from 'app/entities/poll-options/poll-options.service';
import { PollOptions } from 'app/shared/model/poll-options.model';

describe('Component Tests', () => {
  describe('PollOptions Management Update Component', () => {
    let comp: PollOptionsUpdateComponent;
    let fixture: ComponentFixture<PollOptionsUpdateComponent>;
    let service: PollOptionsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [PollOptionsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PollOptionsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PollOptionsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PollOptionsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PollOptions(123);
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
        const entity = new PollOptions();
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
