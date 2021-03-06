import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SequortalibfinalTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { HistoriqueEnseignantModuleDeleteDialogComponent } from 'app/entities/historique-enseignant-module/historique-enseignant-module-delete-dialog.component';
import { HistoriqueEnseignantModuleService } from 'app/entities/historique-enseignant-module/historique-enseignant-module.service';

describe('Component Tests', () => {
  describe('HistoriqueEnseignantModule Management Delete Component', () => {
    let comp: HistoriqueEnseignantModuleDeleteDialogComponent;
    let fixture: ComponentFixture<HistoriqueEnseignantModuleDeleteDialogComponent>;
    let service: HistoriqueEnseignantModuleService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SequortalibfinalTestModule],
        declarations: [HistoriqueEnseignantModuleDeleteDialogComponent]
      })
        .overrideTemplate(HistoriqueEnseignantModuleDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HistoriqueEnseignantModuleDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HistoriqueEnseignantModuleService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
