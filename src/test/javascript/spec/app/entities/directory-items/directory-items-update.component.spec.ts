import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { DirectoryItemsUpdateComponent } from 'app/entities/directory-items/directory-items-update.component';
import { DirectoryItemsService } from 'app/entities/directory-items/directory-items.service';
import { DirectoryItems } from 'app/shared/model/directory-items.model';

describe('Component Tests', () => {
  describe('DirectoryItems Management Update Component', () => {
    let comp: DirectoryItemsUpdateComponent;
    let fixture: ComponentFixture<DirectoryItemsUpdateComponent>;
    let service: DirectoryItemsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [DirectoryItemsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DirectoryItemsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DirectoryItemsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DirectoryItemsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DirectoryItems(123);
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
        const entity = new DirectoryItems();
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
