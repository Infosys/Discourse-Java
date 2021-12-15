import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UnsubscribeKeysDetailComponent } from 'app/entities/unsubscribe-keys/unsubscribe-keys-detail.component';
import { UnsubscribeKeys } from 'app/shared/model/unsubscribe-keys.model';

describe('Component Tests', () => {
  describe('UnsubscribeKeys Management Detail Component', () => {
    let comp: UnsubscribeKeysDetailComponent;
    let fixture: ComponentFixture<UnsubscribeKeysDetailComponent>;
    const route = ({ data: of({ unsubscribeKeys: new UnsubscribeKeys(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UnsubscribeKeysDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UnsubscribeKeysDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UnsubscribeKeysDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load unsubscribeKeys on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.unsubscribeKeys).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
