import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { SiteSettingsDetailComponent } from 'app/entities/site-settings/site-settings-detail.component';
import { SiteSettings } from 'app/shared/model/site-settings.model';

describe('Component Tests', () => {
  describe('SiteSettings Management Detail Component', () => {
    let comp: SiteSettingsDetailComponent;
    let fixture: ComponentFixture<SiteSettingsDetailComponent>;
    const route = ({ data: of({ siteSettings: new SiteSettings(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [SiteSettingsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SiteSettingsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SiteSettingsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load siteSettings on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.siteSettings).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
