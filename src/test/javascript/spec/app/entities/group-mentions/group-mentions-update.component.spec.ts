import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { GroupMentionsUpdateComponent } from 'app/entities/group-mentions/group-mentions-update.component';
import { GroupMentionsService } from 'app/entities/group-mentions/group-mentions.service';
import { GroupMentions } from 'app/shared/model/group-mentions.model';

describe('Component Tests', () => {
  describe('GroupMentions Management Update Component', () => {
    let comp: GroupMentionsUpdateComponent;
    let fixture: ComponentFixture<GroupMentionsUpdateComponent>;
    let service: GroupMentionsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [GroupMentionsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(GroupMentionsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GroupMentionsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GroupMentionsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GroupMentions(123);
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
        const entity = new GroupMentions();
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
