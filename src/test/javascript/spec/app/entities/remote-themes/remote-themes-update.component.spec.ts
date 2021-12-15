import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { RemoteThemesUpdateComponent } from 'app/entities/remote-themes/remote-themes-update.component';
import { RemoteThemesService } from 'app/entities/remote-themes/remote-themes.service';
import { RemoteThemes } from 'app/shared/model/remote-themes.model';

describe('Component Tests', () => {
  describe('RemoteThemes Management Update Component', () => {
    let comp: RemoteThemesUpdateComponent;
    let fixture: ComponentFixture<RemoteThemesUpdateComponent>;
    let service: RemoteThemesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [RemoteThemesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(RemoteThemesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RemoteThemesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RemoteThemesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RemoteThemes(123);
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
        const entity = new RemoteThemes();
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
