import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { EmailTokensUpdateComponent } from 'app/entities/email-tokens/email-tokens-update.component';
import { EmailTokensService } from 'app/entities/email-tokens/email-tokens.service';
import { EmailTokens } from 'app/shared/model/email-tokens.model';

describe('Component Tests', () => {
  describe('EmailTokens Management Update Component', () => {
    let comp: EmailTokensUpdateComponent;
    let fixture: ComponentFixture<EmailTokensUpdateComponent>;
    let service: EmailTokensService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [EmailTokensUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EmailTokensUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmailTokensUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmailTokensService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EmailTokens(123);
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
        const entity = new EmailTokens();
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
