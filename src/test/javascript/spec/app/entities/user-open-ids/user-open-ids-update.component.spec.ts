import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserOpenIdsUpdateComponent } from 'app/entities/user-open-ids/user-open-ids-update.component';
import { UserOpenIdsService } from 'app/entities/user-open-ids/user-open-ids.service';
import { UserOpenIds } from 'app/shared/model/user-open-ids.model';

describe('Component Tests', () => {
  describe('UserOpenIds Management Update Component', () => {
    let comp: UserOpenIdsUpdateComponent;
    let fixture: ComponentFixture<UserOpenIdsUpdateComponent>;
    let service: UserOpenIdsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserOpenIdsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserOpenIdsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserOpenIdsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserOpenIdsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserOpenIds(123);
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
        const entity = new UserOpenIds();
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
