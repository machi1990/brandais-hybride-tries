/** 
 * Classes authors. 
 * @author Chitimbo Manyanda a Master Student at the University of Pierre and Marie Curie. 
 * @author Larbi Mohamed  Youcef   also a Master Student at the same university
 */

package algav.abresbrandais;

import interfaces.Iarbre;

import java.util.ArrayList;
import java.util.Iterator;

import algav.trihybrides.ITrieHybride;
import algav.trihybrides.TrieHybride;

public class Brandais implements IBrandais {

	private char lettre;
	private IBrandais frere;
	private IBrandais fils;
	private IBrandais pere = null;
	public static final char charVide = 0;

	public Brandais() {
		frere = null;
		fils = null;
		pere = null;
		lettre = charVide;
	}

	public Brandais(char key, IBrandais fils, IBrandais frere) {
		this.frere = frere;
		this.fils = fils;
		if (fils != null)
			((Brandais) fils).setPere(this);
		this.lettre = key;
	}

	public Brandais(String mot) {
		if (mot.isEmpty()) {
			new Brandais(charVide, null, null);
		} else {
			lettre = mot.charAt(0);
			frere = null;
			fils = new Brandais(mot.substring(1));
			((Brandais) fils).setPere(this);
		}
	}

	@Override
	public boolean Recherche(String mot) {
		if (estVide() && pere != null) {
			return true; // Word found with |O|null|null as our last node
		} else if (estVide()) {
			return false; // |O|null|null node meaning that we do not have any
							// meaningful word at all
		} else {
			if (mot.isEmpty()) {
				if (lettre == charVide) // this cause a null pointer exception
					return true; // Word found
				else
					return false; // Word is prefix of a certain word in our
									// dictionary
			}
			if (mot.charAt(0) == lettre) {
				if (fils != null)
					return fils.Recherche(mot.substring(1)); // see
																// fils.getKey()==charVide
				else {
					return false;
				}
			} else {
				if (mot.charAt(0) < lettre) {
					return false;
				} else {
					if (frere != null)
						return frere.Recherche(mot);
					else {
						return false;
					}
				}

			}

		}
	}

	@Override
	public int ComptageMots() {
		if (lettre == charVide) {
			if (estVide() && pere == null)
				return 0;
			if (frere != null)
				return 1 + frere.ComptageMots();
			else
				return 1;
		} else {
			if (frere != null && fils != null)
				return fils.ComptageMots() + frere.ComptageMots();
			else if (frere == null && fils != null) {
				return fils.ComptageMots();
			} else if (frere != null && fils == null) {
				return frere.ComptageMots();
			}
			return 0;
		}
	}

	@Deprecated
	@Override
	public int compteMots() {
		return this.ListeMots().size();
	}

	@Override
	public boolean estVide() {
		if (lettre == charVide && fils == null && frere == null)
			return true;
		else
			return false;
	}

	@Override
	public boolean isBrandais() {
		return true;
	}

	@Override
	public Iarbre ajouter(String mot) {
		return ajout(TrieHybride.toWord(mot));
	}

	private Iarbre ajout(String mot) { // works
		if (this.estVide() && pere == null) {
			return new Brandais(mot);
		}
		if (mot.isEmpty()) {
			if (lettre == charVide) {
				return this;
			} else {
				if(pere!=null)
				   return new Brandais(charVide, null, this);
				else 
					return this;
			}
		} else {
			if (mot.charAt(0) < lettre) {
				return new Brandais(mot.charAt(0), new Brandais(
						mot.substring(1)), this);
			} else {
				if (lettre < mot.charAt(0)) {
					if (frere != null) {
						return new Brandais(lettre, fils,
								(IBrandais) ((Brandais) frere).ajout(mot));
					} else {
						return new Brandais(lettre, fils, new Brandais(mot));
					}

				} else {
					if (fils != null) {
						return new Brandais(lettre,
								(IBrandais) ((Brandais) fils).ajout(mot
										.substring(1)), frere);
					} else {
						return new Brandais(lettre, new Brandais(
								mot.substring(1)), frere);
					}
				}
			}
		}
	}

	@Override
	public Iarbre supprimer(String mot) { // does not work
		if (mot.isEmpty())
			return frere;
		if (lettre == charVide) {
			this.frere = (frere != null) ? ((IBrandais) frere.supprimer(mot))
					: null;
			return this;
		}
		if (lettre == mot.charAt(0)) {
			if (fils != null)
				return new Brandais(mot.charAt(0),
						(IBrandais) fils.supprimer(mot.substring(1)), frere);
			else
				return this;
		} else {
			if (lettre > mot.charAt(0))
				return this;
			if (frere != null)
				return new Brandais(lettre, fils,
						(IBrandais) frere.supprimer(mot));
			else
				return this;
		}
	}

	@Override
	public IBrandais getFils() {
		return this.fils;
	}

	@Override
	public IBrandais getFrere() {
		return this.frere;
	}

	private void setPere(IBrandais pere) {
		this.pere = pere;
	}

	@Override
	public IBrandais getPere() {
		return pere;
	}

	@Override
	public char getKey() {
		return lettre;
	}

	@Override
	public int ComptageNil() {
		if (fils == null && frere == null)
			return 2;
		else if (fils == null)
			return 1 + frere.ComptageNil();
		else if (frere == null)
			return 1 + fils.ComptageNil();
		return fils.ComptageNil() + frere.ComptageNil();
	}

	@Override
	public int Hauteur() {
		if (fils != null && frere != null)
			return 1 + ((fils.Hauteur() > frere.Hauteur()) ? fils.Hauteur()
					: frere.Hauteur());
		else if (fils != null && frere == null) {
			return 1 + fils.Hauteur();
		} else if (frere != null) {
			return frere.Hauteur();
		} else
			return 1;
	}

	@Override
	public float profondeurMoyenne() {
		return (float) this.sommeProfondeur(0) / (float) this.nombreNoeuds();
	}

	@Override
	public int prefixe(String mot) {
		if (mot.isEmpty()) {
			if (lettre == charVide) {
				return 1 + (frere != null ? frere.ComptageMots() : 0);
				/*
				 * We have found the word and a word is a prefix of a word i.e
				 * the word has the word itself as a prefix
				 */
			} else {
				return (fils != null ? fils.ComptageMots() : 0)
						+ (frere != null ? frere.ComptageMots() : 0);
				// else it's the sum of all the word contained in the next tree
				// of the sub-tree of our tree

			}
		} else {
			if (mot.charAt(0) < lettre)
				return 0;
			else {
				if (mot.charAt(0) == lettre)
					return fils.prefixe(mot.substring(1));
				else
					return frere.prefixe(mot);
			}
		}
	}

	@Override
	@Deprecated
	public ITrieHybride toTrieHybride() {
		ArrayList<String> mots = ListeMots();
		ITrieHybride hybride = new TrieHybride();
		for (String mot : mots)
			hybride = TrieHybride.ajoutEquillibre(hybride, mot);
		return hybride;
	}

	@Override
	public ITrieHybride toTrie() {
		if (estVide())
			return new TrieHybride();
		else {
			if (lettre != charVide) {
				ITrieHybride trie = new TrieHybride(lettre,
						fils != null ? fils.toTrie() : null, null,
						frere != null ? frere.toTrie() : null);
				if (fils.getKey() == charVide) {
					trie.setWordPresence();
				}
				return TrieHybride.equillibre(trie);
			} else
				return (frere != null) ? frere.toTrie() : null;
		}
	}

	@Override
	public void affiche(String value) {
		if (lettre == charVide) {
			System.out.println(value);
			if (frere != null) {
				((Brandais) frere).affiche(value);
			}
		} else {
			if (fils != null) {
				((Brandais) fils).affiche(value + lettre);
			}
			if (frere != null) {
				((Brandais) frere).affiche(value);
			}
		}
	}

	@Override
	public ArrayList<String> ListeMots() {
		return this.construireMot("");
	}

	@Override
	public ArrayList<String> construireMot(String value) {
		ArrayList<String> liste = new ArrayList<String>();
		if (lettre == charVide) {
			liste.add(value);
			if (frere != null) {
				liste.addAll(((Brandais) frere).construireMot(value));
				return liste;
			}
			return liste;
		} else {
			if (fils != null) {
				liste.addAll(((Brandais) fils).construireMot(value + lettre));
				if (frere != null)
					liste.addAll(((Brandais) frere).construireMot(value));
				return liste;
			}
		}
		return liste;
	}

	@Override
	public int nombreNoeuds() {
		if (fils != null && frere != null)
			return 1 + fils.nombreNoeuds() + frere.nombreNoeuds();
		else if (frere != null)
			return 1 + frere.nombreNoeuds();
		else if (fils != null)
			return 1 + fils.nombreNoeuds();
		else
			return 1; // an empty Brandais
	}

	@Override
	public int sommeProfondeur(int profondeur) {
		if (fils == null && frere == null)
			return profondeur;
		else if (fils == null && frere != null)
			return frere.sommeProfondeur(++profondeur);
		else if (frere == null && fils != null)
			return profondeur + fils.sommeProfondeur(++profondeur);
		else
			return profondeur + frere.sommeProfondeur(++profondeur)
					+ fils.sommeProfondeur(++profondeur);
	}

	public static IBrandais fusionner(IBrandais premier, IBrandais second) {
		if (premier == null) {
			if (second == null)
				return new Brandais();
			else
				return second;
		} // a encoder
		else if (second == null) {
			return premier;
		} else {
			ArrayList<String> liste = second.ListeMots();
			Iterator<String> iterator = liste.iterator();
			while (iterator.hasNext()) {
				premier.ajouter(iterator.next());
			}
			return premier;
		}
	}

	public static IBrandais fusion(IBrandais premier, IBrandais second) {
		if (premier == null)
			return second;
		else if (second == null)
			return premier;
		else {
			if (premier.getKey() != charVide && second.getKey() != charVide) {
				if (premier.getKey() < second.getKey()) {
					return new Brandais(premier.getKey(), premier.getFils(),
							fusion(premier.getFrere(), second));
				} else if (premier.getKey() > second.getKey()) {
					return new Brandais(second.getKey(), second.getFils(),
							fusion(second.getFrere(), premier));
				} else {
					return new Brandais(premier.getKey(), fusion(
							premier.getFils(), second.getFils()), fusion(
							premier.getFrere(), second.getFrere()));
				}
			} else {
				if (premier.getKey() != charVide && second.getKey() != charVide) {
					return new Brandais(charVide, null, fusion(
							second.getFrere(), premier));
				} else if (premier.getKey() == charVide
						&& second.getKey() != charVide) {
					return new Brandais(charVide, null, fusion(
							premier.getFrere(), second));
				} else {
					return new Brandais(charVide, null, fusion(
							second.getFrere(), premier.getFrere()));
				}
			}
		}
	}

	@Deprecated
	public int prefix(String mot) {
		ArrayList<String> words = ListeMots();
		int count = 0;
		for (String s : words) {
			if (s.startsWith(mot)) {
				count++;
			}
		}
		return count;
	}

	@Override
	public void setFils(IBrandais brandais) {
		this.fils = brandais;
	}

	@Override
	public void setFrere(IBrandais brandais) {
		this.frere = brandais;
	}
	
	public static void main(String[] args) {
		final String x = "A quel genial professeur de dactylographie "
				+ "sommes nous redevables de la superbe phrase ci dessous, "
				+ "un modele du genre, que toute dactylo";
		
		final String x1=
				" connait par coeur puisque "
				+ "elle fait appel a chacune des touches du "
				+ "clavier de la machine a ecrire ?";

		final String[] chaine = x.split(" ");
		final String[] chaine1=x1.split(" ");
		IBrandais b = new Brandais();
		for (String s : chaine) {
			b = (IBrandais) b.ajouter(s);
		}
		IBrandais b1 = new Brandais();
		for (String s : chaine1) {
			b1 = (IBrandais) b1.ajouter(s);
		}
		
		b.affiche("");
		
		System.out.println("\n\n");
		
		b1.affiche("");
		
		
		b=fusion(b,b1);
		
		
		System.out.println("\n");
		b.affiche("");
		System.out.println(b.ComptageMots());
	}
}