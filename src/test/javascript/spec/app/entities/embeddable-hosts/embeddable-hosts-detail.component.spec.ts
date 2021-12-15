import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { EmbeddableHostsDetailComponent } from 'app/entities/embeddable-hosts/embeddable-hosts-detail.component';
import { EmbeddableHosts } from 'app/shared/model/embeddable-hosts.model';

describe('Component Tests', () => {
  describe('EmbeddableHosts Management Detail Component', () => {
    let comp: EmbeddableHostsDetailComponent;
    let fixture: ComponentFixture<EmbeddableHostsDetailComponent>;
    const route = ({ data: of({ embeddableHosts: new EmbeddableHosts(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [EmbeddableHostsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EmbeddableHostsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmbeddableHostsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load embeddableHosts on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.embeddableHosts).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
