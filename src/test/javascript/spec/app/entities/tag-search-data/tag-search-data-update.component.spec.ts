import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TagSearchDataUpdateComponent } from 'app/entities/tag-search-data/tag-search-data-update.component';
import { TagSearchDataService } from 'app/entities/tag-search-data/tag-search-data.service';
import { TagSearchData } from 'app/shared/model/tag-search-data.model';

describe('Component Tests', () => {
  describe('TagSearchData Management Update Component', () => {
    let comp: TagSearchDataUpdateComponent;
    let fixture: ComponentFixture<TagSearchDataUpdateComponent>;
    let service: TagSearchDataService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TagSearchDataUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TagSearchDataUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TagSearchDataUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TagSearchDataService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TagSearchData(123);
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
        const entity = new TagSearchData();
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
