import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { SearchLogsDetailComponent } from 'app/entities/search-logs/search-logs-detail.component';
import { SearchLogs } from 'app/shared/model/search-logs.model';

describe('Component Tests', () => {
  describe('SearchLogs Management Detail Component', () => {
    let comp: SearchLogsDetailComponent;
    let fixture: ComponentFixture<SearchLogsDetailComponent>;
    const route = ({ data: of({ searchLogs: new SearchLogs(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [SearchLogsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SearchLogsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SearchLogsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load searchLogs on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.searchLogs).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
