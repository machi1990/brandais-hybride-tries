package Main;

import algav.abresbrandais.Brandais;
import algav.abresbrandais.IBrandais;
import algav.trihybrides.ITrieHybride;
import algav.trihybrides.TrieHybride;


public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// String[] x = FileRead.readDirectory("ressources");
		// String[] x = FileRead.readWord(new
		// File("ressources/macbeth.txt"));
		
		final String x = "A quel genial professeur de dactylographie "
				+ "sommes nous redevables de la superbe phrase ci dessous, "
				+ "un modele du genre, que toute dactylo connait par coeur puisque "
				+ "elle fait appel a chacune des touches du "
				+ "clavier de la machine a ecrire ?";
		

		final String[] chaine = x.split(" ");
        System.out.println(chaine.length);
		ITrieHybride trie = new TrieHybride();
		ITrieHybride trie1 = new TrieHybride();
		IBrandais a=new Brandais();
		for (String s : chaine) {
			trie = TrieHybride.ajoutEquillibre(trie, s);
			trie1 = (ITrieHybride) trie1.ajouter(s);
			a=(IBrandais) a.ajouter(s);
		}

		System.out.println("Is TrieHybride balanced?: "
				+ (TrieHybride.isEquilibre(trie) ? " Yes" : "No"));
		
		System.out.println("Displaying the TrieHybride: \n\n");
		//trie.affiche("");

		System.out.println("Total number of words in TrieHybride is: "
				+ trie.ComptageMots()+ "  "+   trie1.ComptageMots() + "\n\n");
		System.out.println("\n\n");

		//a.affiche("");
		
		ITrieHybride z=a.toTrie();
		
		System.out.println("\n\nAfter to Trie \n\n"+z.ComptageMots());
		z.affiche("");
		System.out.println("\n\n After toTrie being displayed upTop");
		
		
		//System.out.println(a.ComptageMots());
		System.out.println("Transforming TriHybride to Brandais "
				+ "and displaying it:\n\n");
		IBrandais b = trie.toBrandais();
		//b.affiche("");

		System.out.println("Total number of words in Brandais is:"
				+ b.ComptageMots() + "\n\n");

		System.out.println("\n\n");

		System.out
				.println("\n Searching for a word and then delete it if it does "
						+ "well exists and displaying the resulting tree!\n\n");
		if (trie.Recherche("appel")) {
			trie.supprimer("appel").affiche("");
			System.out.println("Total number of words in TrieHybride after "
					+ "removing " + "a word is: " + trie.ComptageMots()
					+ "\n\n");
			System.out.println("\n\n");
		}

		System.out.println("\n The Height of Brandais tree is: " + b.Hauteur());
		System.out.println("\n The Height of TrieHybride tree is: "
				+ trie.Hauteur());
	}
}
