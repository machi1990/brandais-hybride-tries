package Main;

import interfaces.Iarbre;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import algav.Tools.FileRead;
import algav.abresbrandais.Brandais;
import algav.abresbrandais.IBrandais;
import algav.trihybrides.ITrieHybride;
import algav.trihybrides.TrieHybride;

public class FonctionnalityTest {
	private static final IBrandais testBrandais = new Brandais();
	private static ITrieHybride testTrieHybride = new TrieHybride();
	private static ITrieHybride testEquilibredTrieHybride = new TrieHybride();

	public static void launchTest(Iarbre testArbre) {
		try {
			String[] chaineInit = FileRead.readDirectory("ressources");
			//String[] chaineInit = FileRead.readWord(new File("ressources/macbeth.txt"));
			testArbre = initializeArbre(chaineInit, testArbre);
			hauteurArbre(testArbre);
			comptageMotsArbre(testArbre);
			comptageNilsArbre(testArbre);
			rechercheDansArbre(testArbre, "zo");
			prefixeDansArbre(testArbre, "zo");
			//testArbre.affiche("");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Iarbre initializeArbre(String[] words, Iarbre testArbre) {
		for (String w : words) {
			testArbre = testArbre.ajouter(w);
		}
		return testArbre;
	}

	public static void hauteurArbre(Iarbre testArbre) {
		System.out.println("La hauteur de l'arbre vaut: " + testArbre.Hauteur());
	}

	public static void comptageMotsArbre(Iarbre testArbre) {
		System.out.println("Le nombre de mots vaut : " + testArbre.ComptageMots());
	}

	public static void listeMotsArbre(Iarbre testArbre) {
		System.out.println("La liste des mots est : ");
		ArrayList<String> listeMots = testArbre.ListeMots();
		for (String word : listeMots) {
			System.out.println(word);
		}
	}

	public static void comptageNilsArbre(Iarbre testArbre) {
		System.out.println("Le nombre de nils vaut : " + testArbre.ComptageNil());
	}

	public static void profondeurMoyenneArbre(Iarbre testArbre) {
		System.out.println("La profondeur moyenne de l'arbre vaut : " + testArbre.profondeurMoyenne());
	}
	
	public static void rechercheDansArbre(Iarbre testArbre, String word){
		if(testArbre.Recherche(word)){
			System.out.println("Le mot " + word + " est dans le dictionnaire");
		}
		else{
			System.out.println("Le mot " + word + " n'est pas dans le dictionnaire");
		}
	}
	
	public static void prefixeDansArbre(Iarbre testArbre, String word) {
		System.out.println("Il y'a " + testArbre.prefixe(word) + " mots avec comme préfixe " + word);
	}

	public static void main(String[] args) {
		launchTest(testTrieHybride);
	}
}