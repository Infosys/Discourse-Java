import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { DevelopersDetailComponent } from 'app/entities/developers/developers-detail.component';
import { Developers } from 'app/shared/model/developers.model';

describe('Component Tests', () => {
  describe('Developers Management Detail Component', () => {
    let comp: DevelopersDetailComponent;
    let fixture: ComponentFixture<DevelopersDetailComponent>;
    const route = ({ data: of({ developers: new Developers(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [DevelopersDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DevelopersDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DevelopersDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load developers on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.developers).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
