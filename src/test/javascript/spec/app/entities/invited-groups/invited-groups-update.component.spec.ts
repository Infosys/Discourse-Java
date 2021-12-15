import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { InvitedGroupsUpdateComponent } from 'app/entities/invited-groups/invited-groups-update.component';
import { InvitedGroupsService } from 'app/entities/invited-groups/invited-groups.service';
import { InvitedGroups } from 'app/shared/model/invited-groups.model';

describe('Component Tests', () => {
  describe('InvitedGroups Management Update Component', () => {
    let comp: InvitedGroupsUpdateComponent;
    let fixture: ComponentFixture<InvitedGroupsUpdateComponent>;
    let service: InvitedGroupsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [InvitedGroupsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(InvitedGroupsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InvitedGroupsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InvitedGroupsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new InvitedGroups(123);
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
        const entity = new InvitedGroups();
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
