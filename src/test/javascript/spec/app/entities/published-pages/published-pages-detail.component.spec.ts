import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { PublishedPagesDetailComponent } from 'app/entities/published-pages/published-pages-detail.component';
import { PublishedPages } from 'app/shared/model/published-pages.model';

describe('Component Tests', () => {
  describe('PublishedPages Management Detail Component', () => {
    let comp: PublishedPagesDetailComponent;
    let fixture: ComponentFixture<PublishedPagesDetailComponent>;
    const route = ({ data: of({ publishedPages: new PublishedPages(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [PublishedPagesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PublishedPagesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PublishedPagesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load publishedPages on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.publishedPages).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
