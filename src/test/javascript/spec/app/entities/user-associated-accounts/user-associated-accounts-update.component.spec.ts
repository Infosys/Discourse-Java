import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserAssociatedAccountsUpdateComponent } from 'app/entities/user-associated-accounts/user-associated-accounts-update.component';
import { UserAssociatedAccountsService } from 'app/entities/user-associated-accounts/user-associated-accounts.service';
import { UserAssociatedAccounts } from 'app/shared/model/user-associated-accounts.model';

describe('Component Tests', () => {
  describe('UserAssociatedAccounts Management Update Component', () => {
    let comp: UserAssociatedAccountsUpdateComponent;
    let fixture: ComponentFixture<UserAssociatedAccountsUpdateComponent>;
    let service: UserAssociatedAccountsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserAssociatedAccountsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserAssociatedAccountsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserAssociatedAccountsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserAssociatedAccountsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserAssociatedAccounts(123);
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
        const entity = new UserAssociatedAccounts();
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
