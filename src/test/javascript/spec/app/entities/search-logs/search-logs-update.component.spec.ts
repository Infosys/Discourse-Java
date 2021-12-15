import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { SearchLogsUpdateComponent } from 'app/entities/search-logs/search-logs-update.component';
import { SearchLogsService } from 'app/entities/search-logs/search-logs.service';
import { SearchLogs } from 'app/shared/model/search-logs.model';

describe('Component Tests', () => {
  describe('SearchLogs Management Update Component', () => {
    let comp: SearchLogsUpdateComponent;
    let fixture: ComponentFixture<SearchLogsUpdateComponent>;
    let service: SearchLogsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [SearchLogsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SearchLogsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SearchLogsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SearchLogsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SearchLogs(123);
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
        const entity = new SearchLogs();
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
