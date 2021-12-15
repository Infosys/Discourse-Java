import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { GivenDailyLikesUpdateComponent } from 'app/entities/given-daily-likes/given-daily-likes-update.component';
import { GivenDailyLikesService } from 'app/entities/given-daily-likes/given-daily-likes.service';
import { GivenDailyLikes } from 'app/shared/model/given-daily-likes.model';

describe('Component Tests', () => {
  describe('GivenDailyLikes Management Update Component', () => {
    let comp: GivenDailyLikesUpdateComponent;
    let fixture: ComponentFixture<GivenDailyLikesUpdateComponent>;
    let service: GivenDailyLikesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [GivenDailyLikesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(GivenDailyLikesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GivenDailyLikesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GivenDailyLikesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GivenDailyLikes(123);
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
        const entity = new GivenDailyLikes();
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
