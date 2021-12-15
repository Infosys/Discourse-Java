import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { WatchedWordsDetailComponent } from 'app/entities/watched-words/watched-words-detail.component';
import { WatchedWords } from 'app/shared/model/watched-words.model';

describe('Component Tests', () => {
  describe('WatchedWords Management Detail Component', () => {
    let comp: WatchedWordsDetailComponent;
    let fixture: ComponentFixture<WatchedWordsDetailComponent>;
    const route = ({ data: of({ watchedWords: new WatchedWords(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [WatchedWordsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(WatchedWordsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WatchedWordsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load watchedWords on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.watchedWords).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
