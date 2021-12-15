import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { DiscourseTestModule } from '../../../test.module';
import { AnnouncmentDetailComponent } from 'app/entities/announcment/announcment-detail.component';
import { Announcment } from 'app/shared/model/announcment.model';

describe('Component Tests', () => {
  describe('Announcment Management Detail Component', () => {
    let comp: AnnouncmentDetailComponent;
    let fixture: ComponentFixture<AnnouncmentDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ announcment: new Announcment(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [AnnouncmentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AnnouncmentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AnnouncmentDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load announcment on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.announcment).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
