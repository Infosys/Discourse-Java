import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { ThemeFieldsDetailComponent } from 'app/entities/theme-fields/theme-fields-detail.component';
import { ThemeFields } from 'app/shared/model/theme-fields.model';

describe('Component Tests', () => {
  describe('ThemeFields Management Detail Component', () => {
    let comp: ThemeFieldsDetailComponent;
    let fixture: ComponentFixture<ThemeFieldsDetailComponent>;
    const route = ({ data: of({ themeFields: new ThemeFields(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ThemeFieldsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ThemeFieldsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ThemeFieldsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load themeFields on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.themeFields).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
