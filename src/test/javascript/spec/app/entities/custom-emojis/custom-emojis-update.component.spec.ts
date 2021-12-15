import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { CustomEmojisUpdateComponent } from 'app/entities/custom-emojis/custom-emojis-update.component';
import { CustomEmojisService } from 'app/entities/custom-emojis/custom-emojis.service';
import { CustomEmojis } from 'app/shared/model/custom-emojis.model';

describe('Component Tests', () => {
  describe('CustomEmojis Management Update Component', () => {
    let comp: CustomEmojisUpdateComponent;
    let fixture: ComponentFixture<CustomEmojisUpdateComponent>;
    let service: CustomEmojisService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [CustomEmojisUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CustomEmojisUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CustomEmojisUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CustomEmojisService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CustomEmojis(123);
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
        const entity = new CustomEmojis();
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
