import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { DirectoryItemsDetailComponent } from 'app/entities/directory-items/directory-items-detail.component';
import { DirectoryItems } from 'app/shared/model/directory-items.model';

describe('Component Tests', () => {
  describe('DirectoryItems Management Detail Component', () => {
    let comp: DirectoryItemsDetailComponent;
    let fixture: ComponentFixture<DirectoryItemsDetailComponent>;
    const route = ({ data: of({ directoryItems: new DirectoryItems(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [DirectoryItemsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DirectoryItemsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DirectoryItemsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load directoryItems on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.directoryItems).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
