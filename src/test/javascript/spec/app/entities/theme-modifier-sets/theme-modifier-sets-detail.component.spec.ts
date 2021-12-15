import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { ThemeModifierSetsDetailComponent } from 'app/entities/theme-modifier-sets/theme-modifier-sets-detail.component';
import { ThemeModifierSets } from 'app/shared/model/theme-modifier-sets.model';

describe('Component Tests', () => {
  describe('ThemeModifierSets Management Detail Component', () => {
    let comp: ThemeModifierSetsDetailComponent;
    let fixture: ComponentFixture<ThemeModifierSetsDetailComponent>;
    const route = ({ data: of({ themeModifierSets: new ThemeModifierSets(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ThemeModifierSetsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ThemeModifierSetsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ThemeModifierSetsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load themeModifierSets on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.themeModifierSets).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
