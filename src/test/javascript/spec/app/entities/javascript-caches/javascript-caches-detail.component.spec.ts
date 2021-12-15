import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { JavascriptCachesDetailComponent } from 'app/entities/javascript-caches/javascript-caches-detail.component';
import { JavascriptCaches } from 'app/shared/model/javascript-caches.model';

describe('Component Tests', () => {
  describe('JavascriptCaches Management Detail Component', () => {
    let comp: JavascriptCachesDetailComponent;
    let fixture: ComponentFixture<JavascriptCachesDetailComponent>;
    const route = ({ data: of({ javascriptCaches: new JavascriptCaches(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [JavascriptCachesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(JavascriptCachesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(JavascriptCachesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load javascriptCaches on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.javascriptCaches).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
