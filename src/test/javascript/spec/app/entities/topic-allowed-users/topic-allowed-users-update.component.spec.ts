import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TopicAllowedUsersUpdateComponent } from 'app/entities/topic-allowed-users/topic-allowed-users-update.component';
import { TopicAllowedUsersService } from 'app/entities/topic-allowed-users/topic-allowed-users.service';
import { TopicAllowedUsers } from 'app/shared/model/topic-allowed-users.model';

describe('Component Tests', () => {
  describe('TopicAllowedUsers Management Update Component', () => {
    let comp: TopicAllowedUsersUpdateComponent;
    let fixture: ComponentFixture<TopicAllowedUsersUpdateComponent>;
    let service: TopicAllowedUsersService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TopicAllowedUsersUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TopicAllowedUsersUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TopicAllowedUsersUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TopicAllowedUsersService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TopicAllowedUsers(123);
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
        const entity = new TopicAllowedUsers();
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
