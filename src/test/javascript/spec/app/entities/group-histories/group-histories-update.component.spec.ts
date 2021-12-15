import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { GroupHistoriesUpdateComponent } from 'app/entities/group-histories/group-histories-update.component';
import { GroupHistoriesService } from 'app/entities/group-histories/group-histories.service';
import { GroupHistories } from 'app/shared/model/group-histories.model';

describe('Component Tests', () => {
  describe('GroupHistories Management Update Component', () => {
    let comp: GroupHistoriesUpdateComponent;
    let fixture: ComponentFixture<GroupHistoriesUpdateComponent>;
    let service: GroupHistoriesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [GroupHistoriesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(GroupHistoriesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GroupHistoriesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GroupHistoriesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GroupHistories(123);
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
        const entity = new GroupHistories();
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
