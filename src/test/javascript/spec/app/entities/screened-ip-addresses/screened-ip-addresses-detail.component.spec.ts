import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { ScreenedIpAddressesDetailComponent } from 'app/entities/screened-ip-addresses/screened-ip-addresses-detail.component';
import { ScreenedIpAddresses } from 'app/shared/model/screened-ip-addresses.model';

describe('Component Tests', () => {
  describe('ScreenedIpAddresses Management Detail Component', () => {
    let comp: ScreenedIpAddressesDetailComponent;
    let fixture: ComponentFixture<ScreenedIpAddressesDetailComponent>;
    const route = ({ data: of({ screenedIpAddresses: new ScreenedIpAddresses(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ScreenedIpAddressesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ScreenedIpAddressesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ScreenedIpAddressesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load screenedIpAddresses on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.screenedIpAddresses).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
