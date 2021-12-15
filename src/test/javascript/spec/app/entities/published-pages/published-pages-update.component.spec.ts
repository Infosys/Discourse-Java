import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { PublishedPagesUpdateComponent } from 'app/entities/published-pages/published-pages-update.component';
import { PublishedPagesService } from 'app/entities/published-pages/published-pages.service';
import { PublishedPages } from 'app/shared/model/published-pages.model';

describe('Component Tests', () => {
  describe('PublishedPages Management Update Component', () => {
    let comp: PublishedPagesUpdateComponent;
    let fixture: ComponentFixture<PublishedPagesUpdateComponent>;
    let service: PublishedPagesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [PublishedPagesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PublishedPagesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PublishedPagesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PublishedPagesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PublishedPages(123);
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
        const entity = new PublishedPages();
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
