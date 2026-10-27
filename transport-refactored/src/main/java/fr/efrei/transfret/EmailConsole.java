package fr.efrei.transfret;

import java.util.ArrayList;
import java.util.List;

/**
 * Envoi d'emails simulé sur la console.
 */
public class EmailConsole implements CanalNotification {

    private static final String SMTP_HOST = "smtp.transfret.fr";
    private static final String EXPEDITEUR = "noreply@transfret.fr";

    private final List<Envoi> envois = new ArrayList<>();

    @Override
    public void envoyer(Client client, Message message) {
        String destinataire = client.getEmail();
        if (destinataire == null || !destinataire.contains("@")) {
            // comme avant : on ignore, ce n'est pas grave si l'email ne part pas
            return;
        }
        System.out.println("[EMAIL via " + SMTP_HOST + "] " + EXPEDITEUR + " -> " + destinataire + " | " + message.sujet());
        System.out.println("        " + message.corps());
        envois.add(new Envoi(destinataire, message.sujet()));
    }

    @Override
    public List<Envoi> envois() {
        return List.copyOf(envois);
    }
}
