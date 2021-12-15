import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TranslationOverridesDetailComponent } from 'app/entities/translation-overrides/translation-overrides-detail.component';
import { TranslationOverrides } from 'app/shared/model/translation-overrides.model';

describe('Component Tests', () => {
  describe('TranslationOverrides Management Detail Component', () => {
    let comp: TranslationOverridesDetailComponent;
    let fixture: ComponentFixture<TranslationOverridesDetailComponent>;
    const route = ({ data: of({ translationOverrides: new TranslationOverrides(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TranslationOverridesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TranslationOverridesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TranslationOverridesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load translationOverrides on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.translationOverrides).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
