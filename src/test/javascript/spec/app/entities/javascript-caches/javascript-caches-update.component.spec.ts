import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { JavascriptCachesUpdateComponent } from 'app/entities/javascript-caches/javascript-caches-update.component';
import { JavascriptCachesService } from 'app/entities/javascript-caches/javascript-caches.service';
import { JavascriptCaches } from 'app/shared/model/javascript-caches.model';

describe('Component Tests', () => {
  describe('JavascriptCaches Management Update Component', () => {
    let comp: JavascriptCachesUpdateComponent;
    let fixture: ComponentFixture<JavascriptCachesUpdateComponent>;
    let service: JavascriptCachesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [JavascriptCachesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(JavascriptCachesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JavascriptCachesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JavascriptCachesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new JavascriptCaches(123);
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
        const entity = new JavascriptCaches();
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
