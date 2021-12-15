import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { StylesheetCacheUpdateComponent } from 'app/entities/stylesheet-cache/stylesheet-cache-update.component';
import { StylesheetCacheService } from 'app/entities/stylesheet-cache/stylesheet-cache.service';
import { StylesheetCache } from 'app/shared/model/stylesheet-cache.model';

describe('Component Tests', () => {
  describe('StylesheetCache Management Update Component', () => {
    let comp: StylesheetCacheUpdateComponent;
    let fixture: ComponentFixture<StylesheetCacheUpdateComponent>;
    let service: StylesheetCacheService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [StylesheetCacheUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(StylesheetCacheUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StylesheetCacheUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StylesheetCacheService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new StylesheetCache(123);
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
        const entity = new StylesheetCache();
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
