package gestionnaires;

import composants_principaux.Controleur;
import composants_principaux.Couleur;
import strategies.Difficulte;

import javax.swing.SwingWorker;
import java.util.List;

public class GestionnaireIAvsIA extends SwingWorker<Void, Void> {
    private Controleur controleur;
    private Difficulte difficulteIA1;
    private Difficulte difficulteIA2;

    public GestionnaireIAvsIA(Controleur controleur, Difficulte difficulteIA1, Difficulte difficulteIA2) {
        this.controleur = controleur;
        this.difficulteIA1 = difficulteIA1;
        this.difficulteIA2 = difficulteIA2;
    }

    @Override
    protected Void doInBackground() {
        while (!controleur.plateau.jeuTermine) {
            if (controleur.plateau.tourJoueur == Couleur.NOIR) {
                tourIA(difficulteIA1);
            } else {
                tourIA(difficulteIA2);
            }
            publish(); // Demande une mise à jour de l'interface graphique
        }
        return null;
    }

    @Override
    protected void process(List<Void> chunks) {
        controleur.vue.miseAjourVue(); // Mise à jour de l'interface graphique après chaque coup
    }

    private void tourIA(Difficulte difficulte) {
        if (difficulte == Difficulte.FACILE) {
            controleur.ordiFacile();
        } else if (difficulte == Difficulte.MOYEN) {
            controleur.ordiMoyen();
        } else if (difficulte == Difficulte.DIFFICILE) {
            controleur.ordiDifficile();
        }
    }
}
