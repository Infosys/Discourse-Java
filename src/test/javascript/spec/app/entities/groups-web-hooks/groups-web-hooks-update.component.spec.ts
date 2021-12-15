import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { GroupsWebHooksUpdateComponent } from 'app/entities/groups-web-hooks/groups-web-hooks-update.component';
import { GroupsWebHooksService } from 'app/entities/groups-web-hooks/groups-web-hooks.service';
import { GroupsWebHooks } from 'app/shared/model/groups-web-hooks.model';

describe('Component Tests', () => {
  describe('GroupsWebHooks Management Update Component', () => {
    let comp: GroupsWebHooksUpdateComponent;
    let fixture: ComponentFixture<GroupsWebHooksUpdateComponent>;
    let service: GroupsWebHooksService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [GroupsWebHooksUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(GroupsWebHooksUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GroupsWebHooksUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GroupsWebHooksService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GroupsWebHooks(123);
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
        const entity = new GroupsWebHooks();
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
