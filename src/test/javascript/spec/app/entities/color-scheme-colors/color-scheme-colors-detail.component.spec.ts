import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { ColorSchemeColorsDetailComponent } from 'app/entities/color-scheme-colors/color-scheme-colors-detail.component';
import { ColorSchemeColors } from 'app/shared/model/color-scheme-colors.model';

describe('Component Tests', () => {
  describe('ColorSchemeColors Management Detail Component', () => {
    let comp: ColorSchemeColorsDetailComponent;
    let fixture: ComponentFixture<ColorSchemeColorsDetailComponent>;
    const route = ({ data: of({ colorSchemeColors: new ColorSchemeColors(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ColorSchemeColorsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ColorSchemeColorsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ColorSchemeColorsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load colorSchemeColors on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.colorSchemeColors).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
