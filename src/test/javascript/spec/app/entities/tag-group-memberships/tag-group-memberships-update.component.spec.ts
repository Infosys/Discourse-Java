import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TagGroupMembershipsUpdateComponent } from 'app/entities/tag-group-memberships/tag-group-memberships-update.component';
import { TagGroupMembershipsService } from 'app/entities/tag-group-memberships/tag-group-memberships.service';
import { TagGroupMemberships } from 'app/shared/model/tag-group-memberships.model';

describe('Component Tests', () => {
  describe('TagGroupMemberships Management Update Component', () => {
    let comp: TagGroupMembershipsUpdateComponent;
    let fixture: ComponentFixture<TagGroupMembershipsUpdateComponent>;
    let service: TagGroupMembershipsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TagGroupMembershipsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TagGroupMembershipsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TagGroupMembershipsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TagGroupMembershipsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TagGroupMemberships(123);
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
        const entity = new TagGroupMemberships();
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
