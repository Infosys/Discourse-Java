import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { ArInternalMetadataDetailComponent } from 'app/entities/ar-internal-metadata/ar-internal-metadata-detail.component';
import { ArInternalMetadata } from 'app/shared/model/ar-internal-metadata.model';

describe('Component Tests', () => {
  describe('ArInternalMetadata Management Detail Component', () => {
    let comp: ArInternalMetadataDetailComponent;
    let fixture: ComponentFixture<ArInternalMetadataDetailComponent>;
    const route = ({ data: of({ arInternalMetadata: new ArInternalMetadata(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ArInternalMetadataDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ArInternalMetadataDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ArInternalMetadataDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load arInternalMetadata on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.arInternalMetadata).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
