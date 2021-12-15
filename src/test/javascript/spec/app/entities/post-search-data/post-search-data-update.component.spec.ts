import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { PostSearchDataUpdateComponent } from 'app/entities/post-search-data/post-search-data-update.component';
import { PostSearchDataService } from 'app/entities/post-search-data/post-search-data.service';
import { PostSearchData } from 'app/shared/model/post-search-data.model';

describe('Component Tests', () => {
  describe('PostSearchData Management Update Component', () => {
    let comp: PostSearchDataUpdateComponent;
    let fixture: ComponentFixture<PostSearchDataUpdateComponent>;
    let service: PostSearchDataService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [PostSearchDataUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PostSearchDataUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PostSearchDataUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PostSearchDataService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PostSearchData(123);
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
        const entity = new PostSearchData();
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
