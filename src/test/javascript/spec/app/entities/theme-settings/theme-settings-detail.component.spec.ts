import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { ThemeSettingsDetailComponent } from 'app/entities/theme-settings/theme-settings-detail.component';
import { ThemeSettings } from 'app/shared/model/theme-settings.model';

describe('Component Tests', () => {
  describe('ThemeSettings Management Detail Component', () => {
    let comp: ThemeSettingsDetailComponent;
    let fixture: ComponentFixture<ThemeSettingsDetailComponent>;
    const route = ({ data: of({ themeSettings: new ThemeSettings(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ThemeSettingsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ThemeSettingsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ThemeSettingsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load themeSettings on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.themeSettings).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
