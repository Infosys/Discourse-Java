import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { GroupCustomFieldsDetailComponent } from 'app/entities/group-custom-fields/group-custom-fields-detail.component';
import { GroupCustomFields } from 'app/shared/model/group-custom-fields.model';

describe('Component Tests', () => {
  describe('GroupCustomFields Management Detail Component', () => {
    let comp: GroupCustomFieldsDetailComponent;
    let fixture: ComponentFixture<GroupCustomFieldsDetailComponent>;
    const route = ({ data: of({ groupCustomFields: new GroupCustomFields(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [GroupCustomFieldsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(GroupCustomFieldsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GroupCustomFieldsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load groupCustomFields on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.groupCustomFields).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
