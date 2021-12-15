import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { ArInternalMetadataUpdateComponent } from 'app/entities/ar-internal-metadata/ar-internal-metadata-update.component';
import { ArInternalMetadataService } from 'app/entities/ar-internal-metadata/ar-internal-metadata.service';
import { ArInternalMetadata } from 'app/shared/model/ar-internal-metadata.model';

describe('Component Tests', () => {
  describe('ArInternalMetadata Management Update Component', () => {
    let comp: ArInternalMetadataUpdateComponent;
    let fixture: ComponentFixture<ArInternalMetadataUpdateComponent>;
    let service: ArInternalMetadataService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ArInternalMetadataUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ArInternalMetadataUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ArInternalMetadataUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ArInternalMetadataService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ArInternalMetadata(123);
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
        const entity = new ArInternalMetadata();
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
