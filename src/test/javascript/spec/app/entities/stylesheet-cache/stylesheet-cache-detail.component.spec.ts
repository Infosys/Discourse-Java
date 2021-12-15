import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { StylesheetCacheDetailComponent } from 'app/entities/stylesheet-cache/stylesheet-cache-detail.component';
import { StylesheetCache } from 'app/shared/model/stylesheet-cache.model';

describe('Component Tests', () => {
  describe('StylesheetCache Management Detail Component', () => {
    let comp: StylesheetCacheDetailComponent;
    let fixture: ComponentFixture<StylesheetCacheDetailComponent>;
    const route = ({ data: of({ stylesheetCache: new StylesheetCache(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [StylesheetCacheDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(StylesheetCacheDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StylesheetCacheDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load stylesheetCache on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.stylesheetCache).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
