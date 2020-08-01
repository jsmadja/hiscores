package com.shmup.hiscores.clients;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShmupClientTest {

    private ShmupClient sc = new ShmupClient();

    @Test
    public void should_get_login() {
        String loginById = sc.getLoginById(34938L);
        assertEquals("Laudec", loginById);
    }

    @Test
    public void should_get_anzymus_login() {
        String loginById = sc.getLoginById(33489L);
        assertEquals("anzymus", loginById);
    }

    @Test
    public void should_parse_page_to_find_login() {
        String page = "Raccourcis\n" +
                "\t\t\t\n" +
                "\t\t\tFAQ\n" +
                "\t\t\t\n" +
                "\t\tPCA\n" +
                "\t\t\t\t\n" +
                "\t\t\tVos messages\n" +
                "\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\tNouveaux messages\n" +
                "\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\tMessages non lus\n" +
                "\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\tSujets sans réponse\n" +
                "\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\tSujets actifs\n" +
                "\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\tRechercher\n" +
                "\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\tMembres\n" +
                "\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\tL’équipe\n" +
                "\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\tFAQ\n" +
                "\t\t\t\n" +
                "\t\tPCA\n" +
                "\t\t\t\t\n" +
                "\t\t\tanzymus\n" +
                "\t\t\t\tPanneau de contrôle de l’utilisateur\n" +
                "\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\tProfil\n" +
                "\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\tDéconnexion\n" +
                "\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t 0\n" +
                "\t\t\t\t\n" +
                "\t\t\t 0\n" +
                "\t\t\t\t\n" +
                "\t\t\t\t\n" +
                "\t\t\tNotifications\n" +
                "\t\t\tParamètres\n" +
                "\t\t\t\t\t\t\t\n" +
                "\t\tPublication d’un message par Kat dans le sujet :« V2 : avancement »16 juil. 2019, 10:29\t\t\t\t\t\t\t\t\tPublication d’un message par Joe-Dalton dans le sujet :« hiscores : bugs et requêtes »15 juil. 2019, 12:28\t\t\t\t\t\t\t\t\tCitation par Kat dans :« V2 : avancement »15 juil. 2019, 08:22\t\t\t\t\t\t\t\t\tPublication de messages par Guts et Kat dans le sujet :« V2 : avancement »15 juil. 2019, 07:55\t\t\t\t\t\t\t\t\tPublication d’un message par Kat dans le sujet :« V2 : avancement »14 juil. 2019, 18:11\t\t\t\t\t\t\t\t\tTout consulter\n" +
                "\t\t\n" +
                "                                                                                    Accueil\n" +
                "                                        \t\t\t\t\tAccueil du forum\n" +
                "\n" +
                "                                                        Rechercher\n" +
                "                        \n" +
                "                    Consulte le profil de LaudecNom d’utilisateur :Laudec\t\t\t [ Administrer l’utilisateur ]\t\t\t\t\t\t [ Tester les permissions de l’utilisateur ]\t\tRang :Insert CoinGroupes :Utilisateurs inscrits Ajouter en ami Ajouter en ignoréContacter LaudecAdresse de courriel :Envoyer un courriel à LaudecMP :Envoyer un message privéStatistiques de l’utilisateurInscription :11 mars 2017, 01:51Dernière visite :10 mars 2019, 23:06Nombre total de messages :4 | Rechercher les messages de l’utilisateur\t\t\t\t\t(0.00 % de tous les messages / 0.00 messages par jour)\t\t\t\t\t\t\t\t\tForum le plus actif :En général...(3 messages / 75.00 % de tous les messages de l’utilisateur)Sujet le plus actif :hiscores : bugs et requêtes(3 messages / 75.00 % de tous les messages de l’utilisateur)Aller\n" +
                "\t\t\t\t\n" +
                "\t\t\t\n" +
                "\t\t Les Shmups en général↳   Blabla Général↳   Tips, conseils, méthodes...↳   High Scores↳   ShmupTube L'émulation↳   Actus et discussions sur l'émulation↳   Au secours ! Fourre-tout↳   Bizutage !↳   Fanboy Inside↳   Au comptoir de la salle d'arcade↳   IRL, meetings, multi, tournois...↳   Petites annonces↳   English Shmupping Do it yourself !↳   Matériel et bricolage↳   Homemade et créations A propos du site...↳   En général...↳   HEY!!! Il manque un jeu!↳   Doléances↳   Le QG↳   Section dev (Shmup V3)Accueil\t\t\t\t\t\t\t\t\tAccueil du forum\t\t\t\t\tMembres\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\tL’équipe\n" +
                "\t\t\t\t\n" +
                "\t\t\tNous contacter\n" +
                "\t\t\t\t\n" +
                "\t\t\tFuseau horaire sur UTC+01:00Supprimer les cookies\n" +
                "\t\t\t\t\n" +
                "\t\t\tConfidentialité\n" +
                "            \n" +
                "        Conditions\n" +
                "                       \n" +
                "        Fuseau horaire sur UTC+01:00Supprimer les cookies\n" +
                "\t\t\t\t\n" +
                "\t\t\tMembres\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\tConfidentialité\n" +
                "            \n" +
                "        Conditions\n" +
                "                       \n" +
                "        L’équipe\n" +
                "\t\t\t\t\n" +
                "\t\t\tNous contacter";

        String loginById = sc.parse(page);
        assertEquals("Laudec", loginById);
    }

}
