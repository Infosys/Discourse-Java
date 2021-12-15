import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { Oauth2UserInfosUpdateComponent } from 'app/entities/oauth-2-user-infos/oauth-2-user-infos-update.component';
import { Oauth2UserInfosService } from 'app/entities/oauth-2-user-infos/oauth-2-user-infos.service';
import { Oauth2UserInfos } from 'app/shared/model/oauth-2-user-infos.model';

describe('Component Tests', () => {
  describe('Oauth2UserInfos Management Update Component', () => {
    let comp: Oauth2UserInfosUpdateComponent;
    let fixture: ComponentFixture<Oauth2UserInfosUpdateComponent>;
    let service: Oauth2UserInfosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [Oauth2UserInfosUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(Oauth2UserInfosUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(Oauth2UserInfosUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(Oauth2UserInfosService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Oauth2UserInfos(123);
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
        const entity = new Oauth2UserInfos();
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
