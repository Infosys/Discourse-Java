import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { PostStatsUpdateComponent } from 'app/entities/post-stats/post-stats-update.component';
import { PostStatsService } from 'app/entities/post-stats/post-stats.service';
import { PostStats } from 'app/shared/model/post-stats.model';

describe('Component Tests', () => {
  describe('PostStats Management Update Component', () => {
    let comp: PostStatsUpdateComponent;
    let fixture: ComponentFixture<PostStatsUpdateComponent>;
    let service: PostStatsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [PostStatsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PostStatsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PostStatsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PostStatsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PostStats(123);
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
        const entity = new PostStats();
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
