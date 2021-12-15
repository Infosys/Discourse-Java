import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { OptimizedImagesUpdateComponent } from 'app/entities/optimized-images/optimized-images-update.component';
import { OptimizedImagesService } from 'app/entities/optimized-images/optimized-images.service';
import { OptimizedImages } from 'app/shared/model/optimized-images.model';

describe('Component Tests', () => {
  describe('OptimizedImages Management Update Component', () => {
    let comp: OptimizedImagesUpdateComponent;
    let fixture: ComponentFixture<OptimizedImagesUpdateComponent>;
    let service: OptimizedImagesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [OptimizedImagesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(OptimizedImagesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OptimizedImagesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OptimizedImagesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OptimizedImages(123);
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
        const entity = new OptimizedImages();
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
