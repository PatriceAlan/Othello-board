package gestionnaires;

import composants_principaux.Controleur;
import strategies.Difficulte;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingWorker;

public class GestionnaireIAvsIAMouseAdapter extends MouseAdapter {
    private Controleur controleur;
    private Difficulte difficulteIA1;
    private Difficulte difficulteIA2;

    public GestionnaireIAvsIAMouseAdapter(Controleur controleur, Difficulte difficulteIA1, Difficulte difficulteIA2) {
        this.controleur = controleur;
        this.difficulteIA1 = difficulteIA1;
        this.difficulteIA2 = difficulteIA2;
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        GestionnaireIAvsIA gestionnaireIAvsIA = new GestionnaireIAvsIA(controleur, difficulteIA1, difficulteIA2);
        gestionnaireIAvsIA.execute();
    }
}
