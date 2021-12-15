import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { GroupRequestsUpdateComponent } from 'app/entities/group-requests/group-requests-update.component';
import { GroupRequestsService } from 'app/entities/group-requests/group-requests.service';
import { GroupRequests } from 'app/shared/model/group-requests.model';

describe('Component Tests', () => {
  describe('GroupRequests Management Update Component', () => {
    let comp: GroupRequestsUpdateComponent;
    let fixture: ComponentFixture<GroupRequestsUpdateComponent>;
    let service: GroupRequestsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [GroupRequestsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(GroupRequestsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GroupRequestsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GroupRequestsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GroupRequests(123);
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
        const entity = new GroupRequests();
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
