import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TopicGroupsUpdateComponent } from 'app/entities/topic-groups/topic-groups-update.component';
import { TopicGroupsService } from 'app/entities/topic-groups/topic-groups.service';
import { TopicGroups } from 'app/shared/model/topic-groups.model';

describe('Component Tests', () => {
  describe('TopicGroups Management Update Component', () => {
    let comp: TopicGroupsUpdateComponent;
    let fixture: ComponentFixture<TopicGroupsUpdateComponent>;
    let service: TopicGroupsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TopicGroupsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TopicGroupsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TopicGroupsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TopicGroupsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TopicGroups(123);
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
        const entity = new TopicGroups();
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
