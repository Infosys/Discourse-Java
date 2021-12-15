import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { PermalinksUpdateComponent } from 'app/entities/permalinks/permalinks-update.component';
import { PermalinksService } from 'app/entities/permalinks/permalinks.service';
import { Permalinks } from 'app/shared/model/permalinks.model';

describe('Component Tests', () => {
  describe('Permalinks Management Update Component', () => {
    let comp: PermalinksUpdateComponent;
    let fixture: ComponentFixture<PermalinksUpdateComponent>;
    let service: PermalinksService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [PermalinksUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PermalinksUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PermalinksUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PermalinksService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Permalinks(123);
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
        const entity = new Permalinks();
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
