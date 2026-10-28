package fr.efrei.transfret;

import java.util.EnumMap;
import java.util.Map;

/**
 * Associe chaque mode de transport et chaque type de client à sa stratégie.
 * C'est le seul endroit à compléter quand on ajoute un mode ou un type de client.
 */
public final class Configuration {

    private Configuration() {
    }

    public static Map<ModeTransport, TarifTransport> tarifsParMode() {
        Map<ModeTransport, TarifTransport> tarifs = new EnumMap<>(ModeTransport.class);
        tarifs.put(ModeTransport.ROUTE, new TarifRoute());
        tarifs.put(ModeTransport.RAIL, new TarifRail());
        tarifs.put(ModeTransport.MER, new TarifMer());
        tarifs.put(ModeTransport.AIR, new TarifAir());
        return tarifs;
    }

    public static Map<ModeTransport, DelaiTransport> delaisParMode() {
        Map<ModeTransport, DelaiTransport> delais = new EnumMap<>(ModeTransport.class);
        delais.put(ModeTransport.ROUTE, new DelaiRoute());
        delais.put(ModeTransport.RAIL, new DelaiRail());
        delais.put(ModeTransport.MER, new DelaiMer());
        delais.put(ModeTransport.AIR, new DelaiAir());
        return delais;
    }

    public static Map<TypeClient, PolitiqueRemise> remisesParTypeClient() {
        Map<TypeClient, PolitiqueRemise> remises = new EnumMap<>(TypeClient.class);
        remises.put(TypeClient.PARTICULIER, new AucuneRemise());
        remises.put(TypeClient.ENTREPRISE, new RemiseEntreprise());
        remises.put(TypeClient.PREMIUM, new RemisePremium());
        return remises;
    }
}
