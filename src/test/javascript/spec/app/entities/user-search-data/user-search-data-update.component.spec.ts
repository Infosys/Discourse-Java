import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserSearchDataUpdateComponent } from 'app/entities/user-search-data/user-search-data-update.component';
import { UserSearchDataService } from 'app/entities/user-search-data/user-search-data.service';
import { UserSearchData } from 'app/shared/model/user-search-data.model';

describe('Component Tests', () => {
  describe('UserSearchData Management Update Component', () => {
    let comp: UserSearchDataUpdateComponent;
    let fixture: ComponentFixture<UserSearchDataUpdateComponent>;
    let service: UserSearchDataService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserSearchDataUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserSearchDataUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserSearchDataUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserSearchDataService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserSearchData(123);
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
        const entity = new UserSearchData();
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
