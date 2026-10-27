package fr.efrei.transfret;

/**
 * Prévient un client d'un événement sur tous les canaux auxquels il a droit.
 */
public class Notificateur {

    public void notifier(Client client, String sujet, String corps, String sms) {
        EmailService.envoyer(client.getEmail(), sujet, corps);
        if (client.getType() == TypeClient.PREMIUM) {
            new SmsService().envoyerSms(client.getTelephone(), sms);
        }
    }
}
