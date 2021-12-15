import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { PostReplyKeysUpdateComponent } from 'app/entities/post-reply-keys/post-reply-keys-update.component';
import { PostReplyKeysService } from 'app/entities/post-reply-keys/post-reply-keys.service';
import { PostReplyKeys } from 'app/shared/model/post-reply-keys.model';

describe('Component Tests', () => {
  describe('PostReplyKeys Management Update Component', () => {
    let comp: PostReplyKeysUpdateComponent;
    let fixture: ComponentFixture<PostReplyKeysUpdateComponent>;
    let service: PostReplyKeysService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [PostReplyKeysUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PostReplyKeysUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PostReplyKeysUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PostReplyKeysService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PostReplyKeys(123);
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
        const entity = new PostReplyKeys();
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
