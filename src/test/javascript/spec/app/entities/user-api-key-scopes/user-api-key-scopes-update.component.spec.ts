import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserApiKeyScopesUpdateComponent } from 'app/entities/user-api-key-scopes/user-api-key-scopes-update.component';
import { UserApiKeyScopesService } from 'app/entities/user-api-key-scopes/user-api-key-scopes.service';
import { UserApiKeyScopes } from 'app/shared/model/user-api-key-scopes.model';

describe('Component Tests', () => {
  describe('UserApiKeyScopes Management Update Component', () => {
    let comp: UserApiKeyScopesUpdateComponent;
    let fixture: ComponentFixture<UserApiKeyScopesUpdateComponent>;
    let service: UserApiKeyScopesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserApiKeyScopesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserApiKeyScopesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserApiKeyScopesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserApiKeyScopesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserApiKeyScopes(123);
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
        const entity = new UserApiKeyScopes();
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
