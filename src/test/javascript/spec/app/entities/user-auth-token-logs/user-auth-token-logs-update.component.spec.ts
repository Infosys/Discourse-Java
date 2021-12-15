import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserAuthTokenLogsUpdateComponent } from 'app/entities/user-auth-token-logs/user-auth-token-logs-update.component';
import { UserAuthTokenLogsService } from 'app/entities/user-auth-token-logs/user-auth-token-logs.service';
import { UserAuthTokenLogs } from 'app/shared/model/user-auth-token-logs.model';

describe('Component Tests', () => {
  describe('UserAuthTokenLogs Management Update Component', () => {
    let comp: UserAuthTokenLogsUpdateComponent;
    let fixture: ComponentFixture<UserAuthTokenLogsUpdateComponent>;
    let service: UserAuthTokenLogsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserAuthTokenLogsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserAuthTokenLogsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserAuthTokenLogsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserAuthTokenLogsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserAuthTokenLogs(123);
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
        const entity = new UserAuthTokenLogs();
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
