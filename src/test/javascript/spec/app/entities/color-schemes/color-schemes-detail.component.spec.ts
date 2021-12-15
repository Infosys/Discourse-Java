import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { ColorSchemesDetailComponent } from 'app/entities/color-schemes/color-schemes-detail.component';
import { ColorSchemes } from 'app/shared/model/color-schemes.model';

describe('Component Tests', () => {
  describe('ColorSchemes Management Detail Component', () => {
    let comp: ColorSchemesDetailComponent;
    let fixture: ComponentFixture<ColorSchemesDetailComponent>;
    const route = ({ data: of({ colorSchemes: new ColorSchemes(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ColorSchemesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ColorSchemesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ColorSchemesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load colorSchemes on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.colorSchemes).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
