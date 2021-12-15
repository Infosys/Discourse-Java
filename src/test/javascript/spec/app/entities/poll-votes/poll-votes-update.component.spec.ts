import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { PollVotesUpdateComponent } from 'app/entities/poll-votes/poll-votes-update.component';
import { PollVotesService } from 'app/entities/poll-votes/poll-votes.service';
import { PollVotes } from 'app/shared/model/poll-votes.model';

describe('Component Tests', () => {
  describe('PollVotes Management Update Component', () => {
    let comp: PollVotesUpdateComponent;
    let fixture: ComponentFixture<PollVotesUpdateComponent>;
    let service: PollVotesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [PollVotesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PollVotesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PollVotesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PollVotesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PollVotes(123);
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
        const entity = new PollVotes();
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
