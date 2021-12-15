import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { ThemeTranslationOverridesDetailComponent } from 'app/entities/theme-translation-overrides/theme-translation-overrides-detail.component';
import { ThemeTranslationOverrides } from 'app/shared/model/theme-translation-overrides.model';

describe('Component Tests', () => {
  describe('ThemeTranslationOverrides Management Detail Component', () => {
    let comp: ThemeTranslationOverridesDetailComponent;
    let fixture: ComponentFixture<ThemeTranslationOverridesDetailComponent>;
    const route = ({ data: of({ themeTranslationOverrides: new ThemeTranslationOverrides(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ThemeTranslationOverridesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ThemeTranslationOverridesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ThemeTranslationOverridesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load themeTranslationOverrides on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.themeTranslationOverrides).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
