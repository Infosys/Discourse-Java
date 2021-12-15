import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { WebCrawlerRequestsUpdateComponent } from 'app/entities/web-crawler-requests/web-crawler-requests-update.component';
import { WebCrawlerRequestsService } from 'app/entities/web-crawler-requests/web-crawler-requests.service';
import { WebCrawlerRequests } from 'app/shared/model/web-crawler-requests.model';

describe('Component Tests', () => {
  describe('WebCrawlerRequests Management Update Component', () => {
    let comp: WebCrawlerRequestsUpdateComponent;
    let fixture: ComponentFixture<WebCrawlerRequestsUpdateComponent>;
    let service: WebCrawlerRequestsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [WebCrawlerRequestsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(WebCrawlerRequestsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WebCrawlerRequestsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WebCrawlerRequestsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new WebCrawlerRequests(123);
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
        const entity = new WebCrawlerRequests();
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
