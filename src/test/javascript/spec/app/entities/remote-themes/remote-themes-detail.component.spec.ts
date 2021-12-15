import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { RemoteThemesDetailComponent } from 'app/entities/remote-themes/remote-themes-detail.component';
import { RemoteThemes } from 'app/shared/model/remote-themes.model';

describe('Component Tests', () => {
  describe('RemoteThemes Management Detail Component', () => {
    let comp: RemoteThemesDetailComponent;
    let fixture: ComponentFixture<RemoteThemesDetailComponent>;
    const route = ({ data: of({ remoteThemes: new RemoteThemes(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [RemoteThemesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RemoteThemesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RemoteThemesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load remoteThemes on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.remoteThemes).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
