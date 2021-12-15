import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserApiKeysUpdateComponent } from 'app/entities/user-api-keys/user-api-keys-update.component';
import { UserApiKeysService } from 'app/entities/user-api-keys/user-api-keys.service';
import { UserApiKeys } from 'app/shared/model/user-api-keys.model';

describe('Component Tests', () => {
  describe('UserApiKeys Management Update Component', () => {
    let comp: UserApiKeysUpdateComponent;
    let fixture: ComponentFixture<UserApiKeysUpdateComponent>;
    let service: UserApiKeysService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserApiKeysUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserApiKeysUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserApiKeysUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserApiKeysService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserApiKeys(123);
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
        const entity = new UserApiKeys();
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
