import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { ScreenedIpAddressesUpdateComponent } from 'app/entities/screened-ip-addresses/screened-ip-addresses-update.component';
import { ScreenedIpAddressesService } from 'app/entities/screened-ip-addresses/screened-ip-addresses.service';
import { ScreenedIpAddresses } from 'app/shared/model/screened-ip-addresses.model';

describe('Component Tests', () => {
  describe('ScreenedIpAddresses Management Update Component', () => {
    let comp: ScreenedIpAddressesUpdateComponent;
    let fixture: ComponentFixture<ScreenedIpAddressesUpdateComponent>;
    let service: ScreenedIpAddressesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ScreenedIpAddressesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ScreenedIpAddressesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ScreenedIpAddressesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ScreenedIpAddressesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ScreenedIpAddresses(123);
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
        const entity = new ScreenedIpAddresses();
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
