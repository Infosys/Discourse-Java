import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { CustomEmojisDetailComponent } from 'app/entities/custom-emojis/custom-emojis-detail.component';
import { CustomEmojis } from 'app/shared/model/custom-emojis.model';

describe('Component Tests', () => {
  describe('CustomEmojis Management Detail Component', () => {
    let comp: CustomEmojisDetailComponent;
    let fixture: ComponentFixture<CustomEmojisDetailComponent>;
    const route = ({ data: of({ customEmojis: new CustomEmojis(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [CustomEmojisDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CustomEmojisDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CustomEmojisDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load customEmojis on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.customEmojis).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
