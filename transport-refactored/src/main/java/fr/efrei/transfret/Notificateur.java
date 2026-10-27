package fr.efrei.transfret;

/**
 * Prévient un client d'un événement sur tous les canaux auxquels il a droit.
 */
public class Notificateur {

    private final CanalNotification email;
    private final CanalNotification sms;

    public Notificateur(CanalNotification email, CanalNotification sms) {
        this.email = email;
        this.sms = sms;
    }

    public void notifier(Client client, Message message) {
        email.envoyer(client, message);
        if (client.getType() == TypeClient.PREMIUM) {
            sms.envoyer(client, message);
        }
    }
}
