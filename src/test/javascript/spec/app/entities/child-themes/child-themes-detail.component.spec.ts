import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { ChildThemesDetailComponent } from 'app/entities/child-themes/child-themes-detail.component';
import { ChildThemes } from 'app/shared/model/child-themes.model';

describe('Component Tests', () => {
  describe('ChildThemes Management Detail Component', () => {
    let comp: ChildThemesDetailComponent;
    let fixture: ComponentFixture<ChildThemesDetailComponent>;
    const route = ({ data: of({ childThemes: new ChildThemes(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ChildThemesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ChildThemesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ChildThemesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load childThemes on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.childThemes).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
