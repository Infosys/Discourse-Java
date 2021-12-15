import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { ApiKeysUpdateComponent } from 'app/entities/api-keys/api-keys-update.component';
import { ApiKeysService } from 'app/entities/api-keys/api-keys.service';
import { ApiKeys } from 'app/shared/model/api-keys.model';

describe('Component Tests', () => {
  describe('ApiKeys Management Update Component', () => {
    let comp: ApiKeysUpdateComponent;
    let fixture: ComponentFixture<ApiKeysUpdateComponent>;
    let service: ApiKeysService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ApiKeysUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ApiKeysUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApiKeysUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApiKeysService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ApiKeys(123);
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
        const entity = new ApiKeys();
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
