import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { PollsUpdateComponent } from 'app/entities/polls/polls-update.component';
import { PollsService } from 'app/entities/polls/polls.service';
import { Polls } from 'app/shared/model/polls.model';

describe('Component Tests', () => {
  describe('Polls Management Update Component', () => {
    let comp: PollsUpdateComponent;
    let fixture: ComponentFixture<PollsUpdateComponent>;
    let service: PollsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [PollsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PollsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PollsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PollsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Polls(123);
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
        const entity = new Polls();
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
