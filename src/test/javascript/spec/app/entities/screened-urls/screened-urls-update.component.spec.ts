import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { ScreenedUrlsUpdateComponent } from 'app/entities/screened-urls/screened-urls-update.component';
import { ScreenedUrlsService } from 'app/entities/screened-urls/screened-urls.service';
import { ScreenedUrls } from 'app/shared/model/screened-urls.model';

describe('Component Tests', () => {
  describe('ScreenedUrls Management Update Component', () => {
    let comp: ScreenedUrlsUpdateComponent;
    let fixture: ComponentFixture<ScreenedUrlsUpdateComponent>;
    let service: ScreenedUrlsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ScreenedUrlsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ScreenedUrlsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ScreenedUrlsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ScreenedUrlsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ScreenedUrls(123);
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
        const entity = new ScreenedUrls();
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
