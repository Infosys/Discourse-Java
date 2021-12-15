import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { ApiKeyScopesUpdateComponent } from 'app/entities/api-key-scopes/api-key-scopes-update.component';
import { ApiKeyScopesService } from 'app/entities/api-key-scopes/api-key-scopes.service';
import { ApiKeyScopes } from 'app/shared/model/api-key-scopes.model';

describe('Component Tests', () => {
  describe('ApiKeyScopes Management Update Component', () => {
    let comp: ApiKeyScopesUpdateComponent;
    let fixture: ComponentFixture<ApiKeyScopesUpdateComponent>;
    let service: ApiKeyScopesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ApiKeyScopesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ApiKeyScopesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApiKeyScopesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApiKeyScopesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ApiKeyScopes(123);
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
        const entity = new ApiKeyScopes();
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
