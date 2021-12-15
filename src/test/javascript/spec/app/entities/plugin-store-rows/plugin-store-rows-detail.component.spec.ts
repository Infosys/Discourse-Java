import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { PluginStoreRowsDetailComponent } from 'app/entities/plugin-store-rows/plugin-store-rows-detail.component';
import { PluginStoreRows } from 'app/shared/model/plugin-store-rows.model';

describe('Component Tests', () => {
  describe('PluginStoreRows Management Detail Component', () => {
    let comp: PluginStoreRowsDetailComponent;
    let fixture: ComponentFixture<PluginStoreRowsDetailComponent>;
    const route = ({ data: of({ pluginStoreRows: new PluginStoreRows(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [PluginStoreRowsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PluginStoreRowsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PluginStoreRowsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pluginStoreRows on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pluginStoreRows).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
