package Main;

import java.io.IOException;

import algav.Tools.FileRead;
import algav.abresbrandais.Brandais;
import algav.abresbrandais.IBrandais;
import algav.trihybrides.ITrieHybride;
import algav.trihybrides.TrieHybride;

public class MeasureTest {

	private static IBrandais testBrandais = new Brandais();
	private static ITrieHybride testTrieHybride = new TrieHybride();
	private static ITrieHybride testEquilibredTrieHybride = new TrieHybride();
	
	public static long initializeBrandais(String[] words) {
		long start = System.currentTimeMillis();
		for (String w : words) {
			testBrandais = (IBrandais) testBrandais.ajouter(w);
		}
		long end = System.currentTimeMillis();
		return end - start;
	}

	public static long initializeTrieHybride(String[] words) {
		long start = System.currentTimeMillis();
		for (String w : words) {
			testTrieHybride = (ITrieHybride) testTrieHybride.ajouter(w);
		}
		long end = System.currentTimeMillis();
		return end - start;
	}

	public static long initializeEquilibredTrieHybride(String[] words) {
		long start = System.currentTimeMillis();
		for (String w : words) {
			testEquilibredTrieHybride = TrieHybride.ajoutEquillibreBis(
					testEquilibredTrieHybride, w);
		}
		long end = System.currentTimeMillis();
		return end - start;
	}

	public static long addWordsToBrandais(String[] words) {
		long sumTime = 0;
		for (String w : words) {
			long start = System.nanoTime();
			testBrandais = (IBrandais) testBrandais.ajouter(w);
			long end = System.nanoTime();
			sumTime += end - start;
		}
		return sumTime / words.length;
	}

	public static long addWordsToTrieHybride(String[] words) {
		long sumTime = 0;
		for (String w : words) {
			long start = System.nanoTime();
			testTrieHybride = (ITrieHybride) testTrieHybride.ajouter(w);
			long end = System.nanoTime();
			sumTime += end - start;
		}
		return sumTime / words.length;
	}

	public static long addWordsToEquilibredTrieHybride(String[] words) {
		long sumTime = 0;
		for (String w : words) {
			long start = System.nanoTime();
			testEquilibredTrieHybride = TrieHybride.ajoutEquillibreBis(
					testEquilibredTrieHybride, w);
			long end = System.nanoTime();
			sumTime += end - start;
		}
		return sumTime / words.length;
	}

	public static long searchWordsToBrandais(String[] words) {
		long sumTime = 0;
		for (String w : words) {
			long start = System.nanoTime();
			testBrandais.Recherche(w);
			long end = System.nanoTime();
			sumTime += end - start;
		}
		return sumTime / words.length;
	}

	public static long searchWordsToTrieHybride(String[] words) {
		long sumTime = 0;
		for (String w : words) {
			long start = System.nanoTime();
			testTrieHybride.Recherche(w);
			long end = System.nanoTime();
			sumTime += end - start;
		}
		return sumTime / words.length;
	}

	public static long searchWordsToEquilibredTrieHybride(String[] words) {
		long sumTime = 0;
		for (String w : words) {
			long start = System.nanoTime();
			testEquilibredTrieHybride.Recherche(w);
			long end = System.nanoTime();
			sumTime += end - start;
		}
		return sumTime / words.length;
	}

	public static long removeWordsToBrandais(String[] words) {
		long sumTime = 0;
		for (String w : words) {
			long start = System.nanoTime();
			testBrandais = (IBrandais) testBrandais.supprimer(w);
			long end = System.nanoTime();
			sumTime += end - start;
		}
		return sumTime / words.length;
	}

	public static long removeWordsToTrieHybride(String[] words) {
		long sumTime = 0;
		for (String w : words) {
			long start = System.nanoTime();
			testTrieHybride = (ITrieHybride) testTrieHybride.supprimer(w);
			long end = System.nanoTime();
			sumTime += end - start;
		}
		return sumTime / words.length;
	}

	public static long removeWordsToEquilibredTrieHybride(String[] words) {
		long sumTime = 0;
		for (String w : words) {
			long start = System.nanoTime();
			testEquilibredTrieHybride = (ITrieHybride) testEquilibredTrieHybride
					.supprimer(w);
			long end = System.nanoTime();
			sumTime += end - start;
		}
		return sumTime / words.length;
	}

	public static void main(String[] args) {

		try {
			String[] chaineInit = FileRead.readDirectory("ressources");

			long timePassed = initializeBrandais(chaineInit);
			System.out.println("Brandais's tree initialized in " + timePassed
					+ " millisec");
			timePassed = initializeTrieHybride(chaineInit);
			System.out.println("Hybrid trie initialized in " + timePassed
					+ " millisec");

			timePassed = initializeEquilibredTrieHybride(chaineInit);
			System.out.println("Equilibred Hybrid trie initialized in "
					+ timePassed + " millisec");

			String x = "A quel genial professeur de dactylographie "
					+ "sommes nous redevables de la superbe phrase ci dessous, un "
					+ "modele du genre, que toute dactylo connait par coeur puisque "
					+ "elle fait appel a chacune des touches du "
					+ "clavier de la machine a ecrire ?";

			String[] chaineAdd = x.split(" ");

			timePassed = addWordsToBrandais(chaineAdd);
			System.out.println("Average time to add a word to Brandais is : "
					+ timePassed + " nanosec");

			timePassed = addWordsToTrieHybride(chaineAdd);
			System.out
					.println("Average time to add a word to hybrid trie is : "
							+ timePassed + " nanosec");

			timePassed = addWordsToEquilibredTrieHybride(chaineAdd);
			System.out
					.println("Average time to add a word to equilibred hybrid trie is : "
							+ timePassed + " nanosec");
			
			timePassed = searchWordsToBrandais(chaineAdd);
			System.out.println("Average time to search a word to Brandais is : "
					+ timePassed + " nanosec");

			timePassed = searchWordsToTrieHybride(chaineAdd);
			System.out
					.println("Average time to search a word to hybrid trie is : "
							+ timePassed + " nanosec");

			timePassed = searchWordsToEquilibredTrieHybride(chaineAdd);
			System.out
					.println("Average time to search a word to equilibred hybrid trie is : "
							+ timePassed + " nanosec");

			timePassed = removeWordsToBrandais(chaineAdd);
			System.out
					.println("Average time to remove a word to Brandais is : "
							+ timePassed + " nanosec");

			timePassed = removeWordsToTrieHybride(chaineAdd);
			System.out
					.println("Average time to remove a word to hybrid trie is : "
							+ timePassed + " nanosec");

			timePassed = removeWordsToEquilibredTrieHybride(chaineAdd);
			System.out
					.println("Average time to remove a word to equilibred hybrid trie is : "
							+ timePassed + " nanosec");

		} catch (IOException e) {

		}

	}

}
