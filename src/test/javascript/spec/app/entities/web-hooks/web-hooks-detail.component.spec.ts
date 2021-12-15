import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { WebHooksDetailComponent } from 'app/entities/web-hooks/web-hooks-detail.component';
import { WebHooks } from 'app/shared/model/web-hooks.model';

describe('Component Tests', () => {
  describe('WebHooks Management Detail Component', () => {
    let comp: WebHooksDetailComponent;
    let fixture: ComponentFixture<WebHooksDetailComponent>;
    const route = ({ data: of({ webHooks: new WebHooks(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [WebHooksDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(WebHooksDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WebHooksDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load webHooks on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.webHooks).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
