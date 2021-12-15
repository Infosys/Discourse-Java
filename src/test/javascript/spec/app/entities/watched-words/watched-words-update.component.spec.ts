import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { WatchedWordsUpdateComponent } from 'app/entities/watched-words/watched-words-update.component';
import { WatchedWordsService } from 'app/entities/watched-words/watched-words.service';
import { WatchedWords } from 'app/shared/model/watched-words.model';

describe('Component Tests', () => {
  describe('WatchedWords Management Update Component', () => {
    let comp: WatchedWordsUpdateComponent;
    let fixture: ComponentFixture<WatchedWordsUpdateComponent>;
    let service: WatchedWordsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [WatchedWordsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(WatchedWordsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WatchedWordsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WatchedWordsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new WatchedWords(123);
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
        const entity = new WatchedWords();
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
