import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { SchemaMigrationsDetailComponent } from 'app/entities/schema-migrations/schema-migrations-detail.component';
import { SchemaMigrations } from 'app/shared/model/schema-migrations.model';

describe('Component Tests', () => {
  describe('SchemaMigrations Management Detail Component', () => {
    let comp: SchemaMigrationsDetailComponent;
    let fixture: ComponentFixture<SchemaMigrationsDetailComponent>;
    const route = ({ data: of({ schemaMigrations: new SchemaMigrations(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [SchemaMigrationsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SchemaMigrationsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SchemaMigrationsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load schemaMigrations on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.schemaMigrations).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
