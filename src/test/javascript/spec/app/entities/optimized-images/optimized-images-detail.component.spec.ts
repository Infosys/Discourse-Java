import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { OptimizedImagesDetailComponent } from 'app/entities/optimized-images/optimized-images-detail.component';
import { OptimizedImages } from 'app/shared/model/optimized-images.model';

describe('Component Tests', () => {
  describe('OptimizedImages Management Detail Component', () => {
    let comp: OptimizedImagesDetailComponent;
    let fixture: ComponentFixture<OptimizedImagesDetailComponent>;
    const route = ({ data: of({ optimizedImages: new OptimizedImages(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [OptimizedImagesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(OptimizedImagesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OptimizedImagesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load optimizedImages on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.optimizedImages).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
