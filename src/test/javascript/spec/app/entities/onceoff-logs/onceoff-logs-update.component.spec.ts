import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { OnceoffLogsUpdateComponent } from 'app/entities/onceoff-logs/onceoff-logs-update.component';
import { OnceoffLogsService } from 'app/entities/onceoff-logs/onceoff-logs.service';
import { OnceoffLogs } from 'app/shared/model/onceoff-logs.model';

describe('Component Tests', () => {
  describe('OnceoffLogs Management Update Component', () => {
    let comp: OnceoffLogsUpdateComponent;
    let fixture: ComponentFixture<OnceoffLogsUpdateComponent>;
    let service: OnceoffLogsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [OnceoffLogsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(OnceoffLogsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OnceoffLogsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OnceoffLogsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OnceoffLogs(123);
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
        const entity = new OnceoffLogs();
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
