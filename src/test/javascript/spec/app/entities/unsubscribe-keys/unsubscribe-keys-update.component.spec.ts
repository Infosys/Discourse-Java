import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UnsubscribeKeysUpdateComponent } from 'app/entities/unsubscribe-keys/unsubscribe-keys-update.component';
import { UnsubscribeKeysService } from 'app/entities/unsubscribe-keys/unsubscribe-keys.service';
import { UnsubscribeKeys } from 'app/shared/model/unsubscribe-keys.model';

describe('Component Tests', () => {
  describe('UnsubscribeKeys Management Update Component', () => {
    let comp: UnsubscribeKeysUpdateComponent;
    let fixture: ComponentFixture<UnsubscribeKeysUpdateComponent>;
    let service: UnsubscribeKeysService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UnsubscribeKeysUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UnsubscribeKeysUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UnsubscribeKeysUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UnsubscribeKeysService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UnsubscribeKeys(123);
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
        const entity = new UnsubscribeKeys();
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
