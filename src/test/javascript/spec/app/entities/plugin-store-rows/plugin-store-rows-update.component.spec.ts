import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { PluginStoreRowsUpdateComponent } from 'app/entities/plugin-store-rows/plugin-store-rows-update.component';
import { PluginStoreRowsService } from 'app/entities/plugin-store-rows/plugin-store-rows.service';
import { PluginStoreRows } from 'app/shared/model/plugin-store-rows.model';

describe('Component Tests', () => {
  describe('PluginStoreRows Management Update Component', () => {
    let comp: PluginStoreRowsUpdateComponent;
    let fixture: ComponentFixture<PluginStoreRowsUpdateComponent>;
    let service: PluginStoreRowsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [PluginStoreRowsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PluginStoreRowsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PluginStoreRowsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PluginStoreRowsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PluginStoreRows(123);
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
        const entity = new PluginStoreRows();
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
