import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { SkippedEmailLogsUpdateComponent } from 'app/entities/skipped-email-logs/skipped-email-logs-update.component';
import { SkippedEmailLogsService } from 'app/entities/skipped-email-logs/skipped-email-logs.service';
import { SkippedEmailLogs } from 'app/shared/model/skipped-email-logs.model';

describe('Component Tests', () => {
  describe('SkippedEmailLogs Management Update Component', () => {
    let comp: SkippedEmailLogsUpdateComponent;
    let fixture: ComponentFixture<SkippedEmailLogsUpdateComponent>;
    let service: SkippedEmailLogsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [SkippedEmailLogsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SkippedEmailLogsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SkippedEmailLogsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SkippedEmailLogsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SkippedEmailLogs(123);
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
        const entity = new SkippedEmailLogs();
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
