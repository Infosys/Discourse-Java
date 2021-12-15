import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { WebHooksUpdateComponent } from 'app/entities/web-hooks/web-hooks-update.component';
import { WebHooksService } from 'app/entities/web-hooks/web-hooks.service';
import { WebHooks } from 'app/shared/model/web-hooks.model';

describe('Component Tests', () => {
  describe('WebHooks Management Update Component', () => {
    let comp: WebHooksUpdateComponent;
    let fixture: ComponentFixture<WebHooksUpdateComponent>;
    let service: WebHooksService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [WebHooksUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(WebHooksUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WebHooksUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WebHooksService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new WebHooks(123);
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
        const entity = new WebHooks();
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
