package com.oc.alexandra;

import java.util.*;
import org.apache.log4j.*;
import java.io.*;

public class Bootstrap {

	private static Logger logger = Logger.getLogger(Bootstrap.class); // on peut l'utiliser comme on veut � partir de
																		// la ligne 16.

	public static void main(String[] args) throws IOException {
		String localDir = System.getProperty("user.dir");

		FileInputStream config = new FileInputStream(localDir + "//src//properties//config.properties"); // instancie FileInputStream
		Properties properties = new Properties(); // instancie et mets dans une propri�t� Properties 
		properties.load(config); // m�thode load prend un param�tre un FileImputStream  

		String nbEssai = properties.getProperty("nb_essai"); // a maitriser par coeur !!!
		String nBChiffreCombinaison = properties.getProperty("nb_chiffre_combinaison");
		String modeDev = properties.getProperty("mode_dev");
		if (args.length != 0) {
			modeDev = args[0];
		}
		int choiceReStart = 1; // variable interne a la methode et non plus un attribut de classe
		View.display(View.bonjour);
		
		while (choiceReStart == 1) { // tant que choiceRestart est = 1 (choix) j'execute la boucle

			View.display(View.choixmodedejeu);
			logger.info("Le joueur choisi un mode de jeu"); // rajout pour log

			Scanner sc = new Scanner(System.in);
			int userInput;
			try {
				userInput = sc.nextInt();
				if (userInput != 1 && userInput != 2 && userInput != 3) {
					throw new Exception("Mauvaise combinaison");
				}
				View.display(View.choixmodejeueffectu�(userInput));
			} catch (Exception e) {
				View.display(View.erreurdefrappe); // rajout pour display information joueur
				logger.info("Message d'erreur affich� au joueur, doit choisir un mode de jeu"); // rajout log pour
																								// erreur de frappe
				continue;
			}
			switch (userInput) {
			case 1:
				Challenger chall = new Challenger(nBChiffreCombinaison);
				chall.play(modeDev, Integer.parseInt(nbEssai));

				break;

			case 2:
				Defender def = new Defender(nBChiffreCombinaison);
				def.play(Integer.parseInt(nbEssai));
				break;

			case 3:
				Duel duel = new Duel(nBChiffreCombinaison);
				duel.play(modeDev, Integer.parseInt(nbEssai));
				break;

			default:

				break;

			}
			boolean restartValid = false;
			while (restartValid == false) {

				View.display(View.voulezVousrejouer);
				logger.info("Le joueur a le choix de refaire une partie"); // rajout pour log
				try {
					Scanner scan = new Scanner(System.in); // rajout try and catch si erreur choix restart mode
					choiceReStart = scan.nextInt();
					if (choiceReStart != 1 && choiceReStart != 2) {
						throw new Exception("Mauvaise combinaison");
					}
					restartValid = true;
				} catch (Exception e) {
					View.display(View.restart);
					logger.info("Le joueur a entr� un mauvais choix, il doit choisir 1 ou 2");
					restartValid = false;
				}

			}

		}
		View.display(View.aurevoirAbient�t);
		logger.info("Le joueur a choisi de ne pas recommencer une partie"); // rajout pour log
	}

}
