import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { SchemaMigrationDetailsDetailComponent } from 'app/entities/schema-migration-details/schema-migration-details-detail.component';
import { SchemaMigrationDetails } from 'app/shared/model/schema-migration-details.model';

describe('Component Tests', () => {
  describe('SchemaMigrationDetails Management Detail Component', () => {
    let comp: SchemaMigrationDetailsDetailComponent;
    let fixture: ComponentFixture<SchemaMigrationDetailsDetailComponent>;
    const route = ({ data: of({ schemaMigrationDetails: new SchemaMigrationDetails(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [SchemaMigrationDetailsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SchemaMigrationDetailsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SchemaMigrationDetailsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load schemaMigrationDetails on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.schemaMigrationDetails).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
