import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { IncomingDomainsUpdateComponent } from 'app/entities/incoming-domains/incoming-domains-update.component';
import { IncomingDomainsService } from 'app/entities/incoming-domains/incoming-domains.service';
import { IncomingDomains } from 'app/shared/model/incoming-domains.model';

describe('Component Tests', () => {
  describe('IncomingDomains Management Update Component', () => {
    let comp: IncomingDomainsUpdateComponent;
    let fixture: ComponentFixture<IncomingDomainsUpdateComponent>;
    let service: IncomingDomainsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [IncomingDomainsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(IncomingDomainsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IncomingDomainsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IncomingDomainsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new IncomingDomains(123);
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
        const entity = new IncomingDomains();
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
