import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { PermalinksDetailComponent } from 'app/entities/permalinks/permalinks-detail.component';
import { Permalinks } from 'app/shared/model/permalinks.model';

describe('Component Tests', () => {
  describe('Permalinks Management Detail Component', () => {
    let comp: PermalinksDetailComponent;
    let fixture: ComponentFixture<PermalinksDetailComponent>;
    const route = ({ data: of({ permalinks: new Permalinks(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [PermalinksDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PermalinksDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PermalinksDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load permalinks on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.permalinks).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
