import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserHistoriesUpdateComponent } from 'app/entities/user-histories/user-histories-update.component';
import { UserHistoriesService } from 'app/entities/user-histories/user-histories.service';
import { UserHistories } from 'app/shared/model/user-histories.model';

describe('Component Tests', () => {
  describe('UserHistories Management Update Component', () => {
    let comp: UserHistoriesUpdateComponent;
    let fixture: ComponentFixture<UserHistoriesUpdateComponent>;
    let service: UserHistoriesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserHistoriesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserHistoriesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserHistoriesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserHistoriesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserHistories(123);
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
        const entity = new UserHistories();
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
