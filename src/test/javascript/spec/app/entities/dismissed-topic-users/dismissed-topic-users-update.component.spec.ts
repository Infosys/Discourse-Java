import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { DismissedTopicUsersUpdateComponent } from 'app/entities/dismissed-topic-users/dismissed-topic-users-update.component';
import { DismissedTopicUsersService } from 'app/entities/dismissed-topic-users/dismissed-topic-users.service';
import { DismissedTopicUsers } from 'app/shared/model/dismissed-topic-users.model';

describe('Component Tests', () => {
  describe('DismissedTopicUsers Management Update Component', () => {
    let comp: DismissedTopicUsersUpdateComponent;
    let fixture: ComponentFixture<DismissedTopicUsersUpdateComponent>;
    let service: DismissedTopicUsersService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [DismissedTopicUsersUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DismissedTopicUsersUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DismissedTopicUsersUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DismissedTopicUsersService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DismissedTopicUsers(123);
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
        const entity = new DismissedTopicUsers();
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
