import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { PostUploadsUpdateComponent } from 'app/entities/post-uploads/post-uploads-update.component';
import { PostUploadsService } from 'app/entities/post-uploads/post-uploads.service';
import { PostUploads } from 'app/shared/model/post-uploads.model';

describe('Component Tests', () => {
  describe('PostUploads Management Update Component', () => {
    let comp: PostUploadsUpdateComponent;
    let fixture: ComponentFixture<PostUploadsUpdateComponent>;
    let service: PostUploadsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [PostUploadsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PostUploadsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PostUploadsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PostUploadsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PostUploads(123);
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
        const entity = new PostUploads();
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
