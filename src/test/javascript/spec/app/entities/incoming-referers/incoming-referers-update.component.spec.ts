import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { IncomingReferersUpdateComponent } from 'app/entities/incoming-referers/incoming-referers-update.component';
import { IncomingReferersService } from 'app/entities/incoming-referers/incoming-referers.service';
import { IncomingReferers } from 'app/shared/model/incoming-referers.model';

describe('Component Tests', () => {
  describe('IncomingReferers Management Update Component', () => {
    let comp: IncomingReferersUpdateComponent;
    let fixture: ComponentFixture<IncomingReferersUpdateComponent>;
    let service: IncomingReferersService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [IncomingReferersUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(IncomingReferersUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IncomingReferersUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IncomingReferersService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new IncomingReferers(123);
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
        const entity = new IncomingReferers();
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
