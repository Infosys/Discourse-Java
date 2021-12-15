import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TextClassificationDetailComponent } from 'app/entities/text-classification/text-classification-detail.component';
import { TextClassification } from 'app/shared/model/text-classification.model';

describe('Component Tests', () => {
  describe('TextClassification Management Detail Component', () => {
    let comp: TextClassificationDetailComponent;
    let fixture: ComponentFixture<TextClassificationDetailComponent>;
    const route = ({ data: of({ textClassification: new TextClassification(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TextClassificationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TextClassificationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TextClassificationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load textClassification on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.textClassification).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
