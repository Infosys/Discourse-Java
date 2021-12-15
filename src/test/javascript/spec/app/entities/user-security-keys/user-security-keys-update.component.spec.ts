import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserSecurityKeysUpdateComponent } from 'app/entities/user-security-keys/user-security-keys-update.component';
import { UserSecurityKeysService } from 'app/entities/user-security-keys/user-security-keys.service';
import { UserSecurityKeys } from 'app/shared/model/user-security-keys.model';

describe('Component Tests', () => {
  describe('UserSecurityKeys Management Update Component', () => {
    let comp: UserSecurityKeysUpdateComponent;
    let fixture: ComponentFixture<UserSecurityKeysUpdateComponent>;
    let service: UserSecurityKeysService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserSecurityKeysUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserSecurityKeysUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserSecurityKeysUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserSecurityKeysService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserSecurityKeys(123);
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
        const entity = new UserSecurityKeys();
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
