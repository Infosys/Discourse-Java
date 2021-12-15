import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { EmbeddableHostsUpdateComponent } from 'app/entities/embeddable-hosts/embeddable-hosts-update.component';
import { EmbeddableHostsService } from 'app/entities/embeddable-hosts/embeddable-hosts.service';
import { EmbeddableHosts } from 'app/shared/model/embeddable-hosts.model';

describe('Component Tests', () => {
  describe('EmbeddableHosts Management Update Component', () => {
    let comp: EmbeddableHostsUpdateComponent;
    let fixture: ComponentFixture<EmbeddableHostsUpdateComponent>;
    let service: EmbeddableHostsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [EmbeddableHostsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EmbeddableHostsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmbeddableHostsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmbeddableHostsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EmbeddableHosts(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new EmbeddableHosts();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
