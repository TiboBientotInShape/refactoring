package fr.efrei.transfret;

import java.util.ArrayList;
import java.util.List;

/**
 * Envoi de SMS simulé sur la console.
 */
public class SmsConsole implements CanalNotification {

    private static final int LONGUEUR_MAX_SMS = 160;
    private static final String POINTS_DE_SUSPENSION = "...";

    private final List<Envoi> envois = new ArrayList<>();

    @Override
    public void envoyer(Client client, Message message) {
        String texte = tronquer(message.texteCourt());
        System.out.println("[SMS] -> " + client.getTelephone() + " : " + texte);
        envois.add(new Envoi(client.getTelephone(), texte));
    }

    @Override
    public List<Envoi> envois() {
        return List.copyOf(envois);
    }

    private static String tronquer(String texte) {
        if (texte.length() <= LONGUEUR_MAX_SMS) {
            return texte;
        }
        return texte.substring(0, LONGUEUR_MAX_SMS - POINTS_DE_SUSPENSION.length()) + POINTS_DE_SUSPENSION;
    }
}
