import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserUploadsUpdateComponent } from 'app/entities/user-uploads/user-uploads-update.component';
import { UserUploadsService } from 'app/entities/user-uploads/user-uploads.service';
import { UserUploads } from 'app/shared/model/user-uploads.model';

describe('Component Tests', () => {
  describe('UserUploads Management Update Component', () => {
    let comp: UserUploadsUpdateComponent;
    let fixture: ComponentFixture<UserUploadsUpdateComponent>;
    let service: UserUploadsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserUploadsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserUploadsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserUploadsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserUploadsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserUploads(123);
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
        const entity = new UserUploads();
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
