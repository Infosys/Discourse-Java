import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { PostDetailsUpdateComponent } from 'app/entities/post-details/post-details-update.component';
import { PostDetailsService } from 'app/entities/post-details/post-details.service';
import { PostDetails } from 'app/shared/model/post-details.model';

describe('Component Tests', () => {
  describe('PostDetails Management Update Component', () => {
    let comp: PostDetailsUpdateComponent;
    let fixture: ComponentFixture<PostDetailsUpdateComponent>;
    let service: PostDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [PostDetailsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PostDetailsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PostDetailsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PostDetailsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PostDetails(123);
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
        const entity = new PostDetails();
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
