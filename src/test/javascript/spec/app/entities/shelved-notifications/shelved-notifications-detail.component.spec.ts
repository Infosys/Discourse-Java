import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { ShelvedNotificationsDetailComponent } from 'app/entities/shelved-notifications/shelved-notifications-detail.component';
import { ShelvedNotifications } from 'app/shared/model/shelved-notifications.model';

describe('Component Tests', () => {
  describe('ShelvedNotifications Management Detail Component', () => {
    let comp: ShelvedNotificationsDetailComponent;
    let fixture: ComponentFixture<ShelvedNotificationsDetailComponent>;
    const route = ({ data: of({ shelvedNotifications: new ShelvedNotifications(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ShelvedNotificationsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ShelvedNotificationsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ShelvedNotificationsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load shelvedNotifications on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.shelvedNotifications).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
