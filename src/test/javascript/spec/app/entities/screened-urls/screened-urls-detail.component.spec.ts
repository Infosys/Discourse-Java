import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { ScreenedUrlsDetailComponent } from 'app/entities/screened-urls/screened-urls-detail.component';
import { ScreenedUrls } from 'app/shared/model/screened-urls.model';

describe('Component Tests', () => {
  describe('ScreenedUrls Management Detail Component', () => {
    let comp: ScreenedUrlsDetailComponent;
    let fixture: ComponentFixture<ScreenedUrlsDetailComponent>;
    const route = ({ data: of({ screenedUrls: new ScreenedUrls(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ScreenedUrlsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ScreenedUrlsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ScreenedUrlsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load screenedUrls on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.screenedUrls).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
