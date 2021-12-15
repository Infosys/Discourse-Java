import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { BookmarksUpdateComponent } from 'app/entities/bookmarks/bookmarks-update.component';
import { BookmarksService } from 'app/entities/bookmarks/bookmarks.service';
import { Bookmarks } from 'app/shared/model/bookmarks.model';

describe('Component Tests', () => {
  describe('Bookmarks Management Update Component', () => {
    let comp: BookmarksUpdateComponent;
    let fixture: ComponentFixture<BookmarksUpdateComponent>;
    let service: BookmarksService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [BookmarksUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BookmarksUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BookmarksUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BookmarksService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Bookmarks(123);
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
        const entity = new Bookmarks();
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
