import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TopicInvitesUpdateComponent } from 'app/entities/topic-invites/topic-invites-update.component';
import { TopicInvitesService } from 'app/entities/topic-invites/topic-invites.service';
import { TopicInvites } from 'app/shared/model/topic-invites.model';

describe('Component Tests', () => {
  describe('TopicInvites Management Update Component', () => {
    let comp: TopicInvitesUpdateComponent;
    let fixture: ComponentFixture<TopicInvitesUpdateComponent>;
    let service: TopicInvitesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TopicInvitesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TopicInvitesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TopicInvitesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TopicInvitesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TopicInvites(123);
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
        const entity = new TopicInvites();
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
