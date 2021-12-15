import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { IncomingLinksUpdateComponent } from 'app/entities/incoming-links/incoming-links-update.component';
import { IncomingLinksService } from 'app/entities/incoming-links/incoming-links.service';
import { IncomingLinks } from 'app/shared/model/incoming-links.model';

describe('Component Tests', () => {
  describe('IncomingLinks Management Update Component', () => {
    let comp: IncomingLinksUpdateComponent;
    let fixture: ComponentFixture<IncomingLinksUpdateComponent>;
    let service: IncomingLinksService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [IncomingLinksUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(IncomingLinksUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IncomingLinksUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IncomingLinksService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new IncomingLinks(123);
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
        const entity = new IncomingLinks();
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
