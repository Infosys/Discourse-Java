import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { InvitesUpdateComponent } from 'app/entities/invites/invites-update.component';
import { InvitesService } from 'app/entities/invites/invites.service';
import { Invites } from 'app/shared/model/invites.model';

describe('Component Tests', () => {
  describe('Invites Management Update Component', () => {
    let comp: InvitesUpdateComponent;
    let fixture: ComponentFixture<InvitesUpdateComponent>;
    let service: InvitesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [InvitesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(InvitesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InvitesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InvitesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Invites(123);
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
        const entity = new Invites();
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
