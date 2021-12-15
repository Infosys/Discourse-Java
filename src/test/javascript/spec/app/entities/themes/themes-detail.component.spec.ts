import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { ThemesDetailComponent } from 'app/entities/themes/themes-detail.component';
import { Themes } from 'app/shared/model/themes.model';

describe('Component Tests', () => {
  describe('Themes Management Detail Component', () => {
    let comp: ThemesDetailComponent;
    let fixture: ComponentFixture<ThemesDetailComponent>;
    const route = ({ data: of({ themes: new Themes(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ThemesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ThemesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ThemesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load themes on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.themes).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
