import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TopicAllowedGroupsUpdateComponent } from 'app/entities/topic-allowed-groups/topic-allowed-groups-update.component';
import { TopicAllowedGroupsService } from 'app/entities/topic-allowed-groups/topic-allowed-groups.service';
import { TopicAllowedGroups } from 'app/shared/model/topic-allowed-groups.model';

describe('Component Tests', () => {
  describe('TopicAllowedGroups Management Update Component', () => {
    let comp: TopicAllowedGroupsUpdateComponent;
    let fixture: ComponentFixture<TopicAllowedGroupsUpdateComponent>;
    let service: TopicAllowedGroupsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TopicAllowedGroupsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TopicAllowedGroupsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TopicAllowedGroupsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TopicAllowedGroupsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TopicAllowedGroups(123);
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
        const entity = new TopicAllowedGroups();
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
