package fr.efrei.transfret;

import java.util.List;

public interface CanalNotification {

    void envoyer(Client client, Message message);

    /** Les envois réussis, dans l'ordre (demandé par le support client). */
    List<Envoi> envois();

    /** @param objet sujet d'un email, ou texte d'un SMS */
    record Envoi(String destinataire, String objet) {
    }
}
