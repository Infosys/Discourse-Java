import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { InvitesDetailComponent } from 'app/entities/invites/invites-detail.component';
import { Invites } from 'app/shared/model/invites.model';

describe('Component Tests', () => {
  describe('Invites Management Detail Component', () => {
    let comp: InvitesDetailComponent;
    let fixture: ComponentFixture<InvitesDetailComponent>;
    const route = ({ data: of({ invites: new Invites(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [InvitesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(InvitesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InvitesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load invites on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.invites).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
