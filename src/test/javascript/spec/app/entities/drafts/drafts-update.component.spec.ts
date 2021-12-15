import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { DraftsUpdateComponent } from 'app/entities/drafts/drafts-update.component';
import { DraftsService } from 'app/entities/drafts/drafts.service';
import { Drafts } from 'app/shared/model/drafts.model';

describe('Component Tests', () => {
  describe('Drafts Management Update Component', () => {
    let comp: DraftsUpdateComponent;
    let fixture: ComponentFixture<DraftsUpdateComponent>;
    let service: DraftsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [DraftsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DraftsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DraftsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DraftsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Drafts(123);
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
        const entity = new Drafts();
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
