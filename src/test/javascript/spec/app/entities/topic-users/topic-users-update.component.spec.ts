import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TopicUsersUpdateComponent } from 'app/entities/topic-users/topic-users-update.component';
import { TopicUsersService } from 'app/entities/topic-users/topic-users.service';
import { TopicUsers } from 'app/shared/model/topic-users.model';

describe('Component Tests', () => {
  describe('TopicUsers Management Update Component', () => {
    let comp: TopicUsersUpdateComponent;
    let fixture: ComponentFixture<TopicUsersUpdateComponent>;
    let service: TopicUsersService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TopicUsersUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TopicUsersUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TopicUsersUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TopicUsersService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TopicUsers(123);
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
        const entity = new TopicUsers();
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
