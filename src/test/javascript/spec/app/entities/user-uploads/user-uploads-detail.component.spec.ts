import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserUploadsDetailComponent } from 'app/entities/user-uploads/user-uploads-detail.component';
import { UserUploads } from 'app/shared/model/user-uploads.model';

describe('Component Tests', () => {
  describe('UserUploads Management Detail Component', () => {
    let comp: UserUploadsDetailComponent;
    let fixture: ComponentFixture<UserUploadsDetailComponent>;
    const route = ({ data: of({ userUploads: new UserUploads(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserUploadsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserUploadsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserUploadsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userUploads on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userUploads).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
