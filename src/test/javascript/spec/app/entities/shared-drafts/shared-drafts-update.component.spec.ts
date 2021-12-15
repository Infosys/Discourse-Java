import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { SharedDraftsUpdateComponent } from 'app/entities/shared-drafts/shared-drafts-update.component';
import { SharedDraftsService } from 'app/entities/shared-drafts/shared-drafts.service';
import { SharedDrafts } from 'app/shared/model/shared-drafts.model';

describe('Component Tests', () => {
  describe('SharedDrafts Management Update Component', () => {
    let comp: SharedDraftsUpdateComponent;
    let fixture: ComponentFixture<SharedDraftsUpdateComponent>;
    let service: SharedDraftsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [SharedDraftsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SharedDraftsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SharedDraftsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SharedDraftsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SharedDrafts(123);
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
        const entity = new SharedDrafts();
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
