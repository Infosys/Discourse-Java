import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { SchemaMigrationDetailsUpdateComponent } from 'app/entities/schema-migration-details/schema-migration-details-update.component';
import { SchemaMigrationDetailsService } from 'app/entities/schema-migration-details/schema-migration-details.service';
import { SchemaMigrationDetails } from 'app/shared/model/schema-migration-details.model';

describe('Component Tests', () => {
  describe('SchemaMigrationDetails Management Update Component', () => {
    let comp: SchemaMigrationDetailsUpdateComponent;
    let fixture: ComponentFixture<SchemaMigrationDetailsUpdateComponent>;
    let service: SchemaMigrationDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [SchemaMigrationDetailsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SchemaMigrationDetailsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SchemaMigrationDetailsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SchemaMigrationDetailsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SchemaMigrationDetails(123);
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
        const entity = new SchemaMigrationDetails();
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
