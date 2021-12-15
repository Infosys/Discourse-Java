import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { PostTimingsUpdateComponent } from 'app/entities/post-timings/post-timings-update.component';
import { PostTimingsService } from 'app/entities/post-timings/post-timings.service';
import { PostTimings } from 'app/shared/model/post-timings.model';

describe('Component Tests', () => {
  describe('PostTimings Management Update Component', () => {
    let comp: PostTimingsUpdateComponent;
    let fixture: ComponentFixture<PostTimingsUpdateComponent>;
    let service: PostTimingsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [PostTimingsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PostTimingsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PostTimingsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PostTimingsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PostTimings(123);
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
        const entity = new PostTimings();
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
