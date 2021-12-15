import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { SchemaMigrationsUpdateComponent } from 'app/entities/schema-migrations/schema-migrations-update.component';
import { SchemaMigrationsService } from 'app/entities/schema-migrations/schema-migrations.service';
import { SchemaMigrations } from 'app/shared/model/schema-migrations.model';

describe('Component Tests', () => {
  describe('SchemaMigrations Management Update Component', () => {
    let comp: SchemaMigrationsUpdateComponent;
    let fixture: ComponentFixture<SchemaMigrationsUpdateComponent>;
    let service: SchemaMigrationsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [SchemaMigrationsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SchemaMigrationsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SchemaMigrationsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SchemaMigrationsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SchemaMigrations(123);
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
        const entity = new SchemaMigrations();
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
