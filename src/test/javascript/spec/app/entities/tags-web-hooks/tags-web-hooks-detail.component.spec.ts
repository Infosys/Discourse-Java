import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TagsWebHooksDetailComponent } from 'app/entities/tags-web-hooks/tags-web-hooks-detail.component';
import { TagsWebHooks } from 'app/shared/model/tags-web-hooks.model';

describe('Component Tests', () => {
  describe('TagsWebHooks Management Detail Component', () => {
    let comp: TagsWebHooksDetailComponent;
    let fixture: ComponentFixture<TagsWebHooksDetailComponent>;
    const route = ({ data: of({ tagsWebHooks: new TagsWebHooks(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TagsWebHooksDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TagsWebHooksDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TagsWebHooksDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tagsWebHooks on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tagsWebHooks).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
