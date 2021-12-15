import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TopicSearchDataUpdateComponent } from 'app/entities/topic-search-data/topic-search-data-update.component';
import { TopicSearchDataService } from 'app/entities/topic-search-data/topic-search-data.service';
import { TopicSearchData } from 'app/shared/model/topic-search-data.model';

describe('Component Tests', () => {
  describe('TopicSearchData Management Update Component', () => {
    let comp: TopicSearchDataUpdateComponent;
    let fixture: ComponentFixture<TopicSearchDataUpdateComponent>;
    let service: TopicSearchDataService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TopicSearchDataUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TopicSearchDataUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TopicSearchDataUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TopicSearchDataService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TopicSearchData(123);
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
        const entity = new TopicSearchData();
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
