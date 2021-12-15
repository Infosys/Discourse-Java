import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserStatsUpdateComponent } from 'app/entities/user-stats/user-stats-update.component';
import { UserStatsService } from 'app/entities/user-stats/user-stats.service';
import { UserStats } from 'app/shared/model/user-stats.model';

describe('Component Tests', () => {
  describe('UserStats Management Update Component', () => {
    let comp: UserStatsUpdateComponent;
    let fixture: ComponentFixture<UserStatsUpdateComponent>;
    let service: UserStatsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserStatsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserStatsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserStatsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserStatsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserStats(123);
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
        const entity = new UserStats();
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
