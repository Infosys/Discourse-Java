import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UploadsDetailComponent } from 'app/entities/uploads/uploads-detail.component';
import { Uploads } from 'app/shared/model/uploads.model';

describe('Component Tests', () => {
  describe('Uploads Management Detail Component', () => {
    let comp: UploadsDetailComponent;
    let fixture: ComponentFixture<UploadsDetailComponent>;
    const route = ({ data: of({ uploads: new Uploads(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UploadsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UploadsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UploadsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load uploads on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.uploads).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
