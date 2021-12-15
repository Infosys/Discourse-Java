import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserIpAddressHistoriesUpdateComponent } from 'app/entities/user-ip-address-histories/user-ip-address-histories-update.component';
import { UserIpAddressHistoriesService } from 'app/entities/user-ip-address-histories/user-ip-address-histories.service';
import { UserIpAddressHistories } from 'app/shared/model/user-ip-address-histories.model';

describe('Component Tests', () => {
  describe('UserIpAddressHistories Management Update Component', () => {
    let comp: UserIpAddressHistoriesUpdateComponent;
    let fixture: ComponentFixture<UserIpAddressHistoriesUpdateComponent>;
    let service: UserIpAddressHistoriesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserIpAddressHistoriesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserIpAddressHistoriesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserIpAddressHistoriesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserIpAddressHistoriesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserIpAddressHistories(123);
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
        const entity = new UserIpAddressHistories();
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
