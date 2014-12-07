/** 
 * Classes authors. 
 * @author Chitimbo Manyanda a Master Student at the University of Pierre and Marie Curie. 
 * @author Larbi Mohamed  Youcef   also a Master Student at the same university
 */

package algav.trihybrides;

import interfaces.Iarbre;

import java.util.ArrayList;

import algav.abresbrandais.Brandais;
import algav.abresbrandais.IBrandais;

public class TrieHybride implements ITrieHybride {

	private final int WORD_ABSENCE = -1;
	private char lettre;
	private int valeur = WORD_ABSENCE; // a negative -1 value means that a node
	// X does not
	// represent a word
	private ITrieHybride pere;
	private ITrieHybride fils_gauche;
	private ITrieHybride fils_central;
	private ITrieHybride fils_droit;

	/**
	 * Constructor of an empty TrieHybride. takes no parameter.
	 */

	public TrieHybride() {
		this.pere = null;
		this.fils_central = null;
		this.fils_droit = null;
		this.fils_gauche = null;
	}

	public TrieHybride(String mot) {
		if (mot.isEmpty())
			new TrieHybride();
		else {
			lettre = mot.charAt(0);
			this.fils_droit = null;
			this.fils_gauche = null;
			if (mot.length() == 1) { // a new word identified
				setWordPresence();
				this.fils_central = null;
			} else {
				this.fils_central = new TrieHybride(mot.substring(1));
				((TrieHybride) fils_central).setPere(this);
			}
		}
	}

	public TrieHybride(char key, ITrieHybride fils_central,
			ITrieHybride fils_gauche, ITrieHybride fils_droit) {
		this.lettre = key;
		this.fils_central = fils_central;
		this.fils_gauche = fils_gauche;
		this.fils_droit = fils_droit;
		if (fils_central != null)
			((TrieHybride) fils_central).setPere(this);
		if (fils_gauche != null)
			((TrieHybride) fils_gauche).setPere(this);
		if (fils_droit != null)
			((TrieHybride) fils_droit).setPere(this);
	}

	public TrieHybride(char lettre, int valeur, ITrieHybride fils_central,
			ITrieHybride fils_gauche, ITrieHybride fils_droit) {
		this.lettre = lettre;
		this.fils_central = fils_central;
		this.fils_gauche = fils_gauche;
		this.fils_droit = fils_droit;
		if (fils_central != null)
			((TrieHybride) fils_central).setPere(this);
		if (fils_gauche != null)
			((TrieHybride) fils_gauche).setPere(this);
		if (fils_droit != null)
			((TrieHybride) fils_droit).setPere(this);
		this.valeur = valeur;
	}

	public static ITrieHybride makeOne(ITrieHybride premier, ITrieHybride second) {
		if (premier == null)
			return second;
		else if (second == null)
			return premier;
		else {
			if (premier.getKey() < second.getKey()) {
				if (second.getFilsGauche() == null) {
					second.setFilsGauche(premier);
					return second;
				} else {
					return new TrieHybride(second.getKey(),
							second.getFilsCentrale(), makeOne(premier,
									second.getFilsGauche()),
							second.getFilsDroit());
				}
			} else if (premier.getKey() > second.getKey()) {
				if (second.getFilsDroit() == null) {
					second.setFilsDroit(premier);
					return second;
				} else {
					return new TrieHybride(second.getKey(),
							second.getFilsCentrale(), second.getFilsGauche(),
							makeOne(premier, second.getFilsDroit()));
				}
			} else {
				ITrieHybride result = new TrieHybride(
						second.getKey(),
						makeOne(second.getFilsCentrale(),
								premier.getFilsCentrale()),
						makeOne(second.getFilsGauche(), premier.getFilsGauche()),
						makeOne(null, null));
				if (second.wordPresent() || premier.wordPresent())
					result.setWordPresence();
				return result;
			}
		}
	}

	@Override
	public boolean Recherche(String mot) {
		if (mot.isEmpty()) {
			return false;
		} else {
			if (lettre == mot.charAt(0)) {
				if (fils_central != null)
					return fils_central.Recherche(mot.substring(1));
				else {
					if (mot.length() == 1 && wordPresent())
						return true;
					else
						return false;
				}
			} else {
				if (lettre < mot.charAt(0)) {
					if (fils_droit != null)
						return fils_droit.Recherche(mot);
					else
						return false;
				} else {
					if (fils_gauche != null)
						return fils_gauche.Recherche(mot);
					else
						return false;
				}
			}
		}
	}

	@Override
	public int ComptageMots() {
		if (estVide())
			return 0;
		else if (wordPresent())
			return 1
					+ ((fils_gauche != null) ? fils_gauche.ComptageMots() : 0)
					+ ((fils_central != null) ? fils_central.ComptageMots() : 0)
					+ ((fils_droit != null) ? fils_droit.ComptageMots() : 0);
		else
			return ((fils_gauche != null) ? fils_gauche.ComptageMots() : 0)
					+ ((fils_central != null) ? fils_central.ComptageMots() : 0)
					+ ((fils_droit != null) ? fils_droit.ComptageMots() : 0);
	}

	@Override
	public ArrayList<String> ListeMots() {
		return construireMot("");
	}

	@Override
	public boolean estVide() {
		return fils_central == null && fils_droit == null
				&& fils_gauche == null && valeur == WORD_ABSENCE;
	}

	@Override
	public boolean isTrieHybride() {
		return true;
	}

	@Override
	public Iarbre ajouter(String mot) {
		return ajout(toWord(mot));
	}

	private Iarbre ajout(String mot) {
		if (estVide()) {
			return new TrieHybride(mot);
		} else {
			if (!mot.isEmpty()) {
				if (this.lettre < mot.charAt(0)) {
					if (fils_droit == null) {
						fils_droit = new TrieHybride(mot);
						((TrieHybride) fils_droit).setPere(this);
					} else
						((TrieHybride) fils_droit).ajout(mot);
					return this;
				} else {
					if (this.lettre > mot.charAt(0)) {
						if (fils_gauche == null) {
							fils_gauche = new TrieHybride(mot);
							((TrieHybride) fils_gauche).setPere(this);
						} else
							((TrieHybride) fils_gauche).ajout(mot);
						return this;
					} else {
						if (mot.length() == 1) {
							setWordPresence();
							return this;
						}
						if (fils_central == null) {// not complete
							fils_central = new TrieHybride(mot.substring(1));
							((TrieHybride) fils_central).setPere(this);
						}
						// not complete
						else
							((TrieHybride) fils_central)
									.ajout(mot.substring(1));
						return this;
					}
				}
			} else {
				return this;
			}
		}
	}

	@Override
	public Iarbre supprimer(String mot) {
		if (estVide())
			return null;
		// we do not have any word, we can as well return null
		else {
			if (mot.isEmpty())
				return this;
			// word do no have any representation of an empty word
			else {
				if (mot.charAt(0) < lettre) {
					if (fils_gauche != null) {
						return new TrieHybride(lettre, valeur, fils_central,
								(ITrieHybride) fils_gauche.supprimer(mot),
								fils_droit);
					} else
						return this; // we do not have such a word
				} else if (mot.charAt(0) > lettre) {
					if (fils_droit != null) {
						return new TrieHybride(lettre, valeur, fils_central,
								fils_gauche,
								(ITrieHybride) fils_droit.supprimer(mot));
					} else
						return this; // we do not have such a word
				} else {
					if (fils_central == null) {
						if (!wordPresent())
							return this; // Word not found
						else {
							if (mot.length() > 1)
								return this; // Word not found
							else
								return makeOne(fils_gauche, fils_droit);
							// word found in our dictionary, delete it by
							// returning the union of the left sub-tree and the
							// right one
						}
					} else {
						ITrieHybride filsApresSupression = (ITrieHybride) fils_central
								.supprimer(mot.substring(1));
						if (filsApresSupression == null) {
							if (wordPresent()) {
								// we have a word's representation in this
								// node
								fils_central = null;
								return this;
							} else
								return makeOne(fils_gauche, fils_droit);
							// otherwise we can delete the node
						} else {
							ITrieHybride tree = new TrieHybride(lettre,
									filsApresSupression, fils_gauche,
									fils_droit);
							if (wordPresent())
								tree.setWordAbsence();
							// we have a word's representation in this
							// node so can not delete the node i.e we
							// have to keep the first letter of the
							// word
							return tree;
						}
					}
				}

			}
		}
	}

	@Override
	public int ComptageNil() {
		int x = 0;
		if (fils_gauche == null) {
			x++;
		} else {
			x += fils_gauche.ComptageNil();
		}

		if (fils_central == null) {
			x++;
		} else {
			x += fils_central.ComptageNil();
		}

		if (fils_droit == null) {
			x++;
		} else {
			x += fils_droit.ComptageNil();
		}
		return x;
	}

	@Override
	public int Hauteur() {
		int h1 = ((fils_central != null) ? fils_central.Hauteur() : 0);
		int h2 = ((fils_gauche != null) ? fils_gauche.Hauteur() : 0);
		int h3 = ((fils_droit != null) ? fils_droit.Hauteur() : 0);
		int maxH1H2 = ((h1 > h2) ? h1 : h2);
		return 1 + ((h3 > maxH1H2) ? h3 : maxH1H2);
	}

	@Override
	public float profondeurMoyenne() {
		return (float) sommeProfondeur(0) / (float) nombreNoeuds();
	}

	@Override
	public int prefixe(String mot) {
		if (estVide())
			return 0;
		if (mot.isEmpty()) {
			return ComptageMots();
		} else {
			char c = mot.charAt(0);
			String s = mot.substring(1);
			if (c < lettre)
				return ((fils_gauche != null) ? fils_gauche.prefixe(mot) : 0);
			else if (c > lettre)
				return ((fils_droit != null) ? fils_droit.prefixe(mot) : 0);
			else
				return ((fils_central != null) ? fils_central.prefixe(s) : 0);
		}
	}

	@Override
	public IBrandais toBrandais() {
		if (estVide())
			return new Brandais();
		else {
			IBrandais brandais = new Brandais(lettre, null,
					((fils_droit != null) ? fils_droit.toBrandais() : null));
			if (wordPresent()) {
				brandais.setFils(new Brandais(Brandais.charVide, null,
						(fils_central != null ? fils_central.toBrandais()
								: null)));
			} else {
				brandais.setFils(fils_central.toBrandais());
			}
			if (fils_gauche != null)
				return Brandais.fusion(fils_gauche.toBrandais(), brandais);
			else
				return brandais;
		}
	}

	@Override
	public char getKey() {
		return lettre;
	}

	@Override
	public ITrieHybride getPere() {
		return this.pere;
	}

	@Override
	public ITrieHybride getFilsCentrale() {
		return this.fils_central;
	}

	@Override
	public ITrieHybride getFilsGauche() {
		return this.fils_gauche;
	}

	@Override
	public ITrieHybride getFilsDroit() {
		return this.fils_droit;
	}

	@Override
	public void setPere(ITrieHybride pere) {
		this.pere = pere;
	}

	@Override
	public ArrayList<String> construireMot(String value) {
		ArrayList<String> liste = new ArrayList<String>();
		if (estVide())
			return liste;
		else {
			if (fils_gauche != null)
				liste.addAll(fils_gauche.construireMot(value));
			if (wordPresent()) {
				liste.add(value + lettre);
				if (fils_central != null)
					liste.addAll(fils_central.construireMot(value + lettre));
			} else {
				if (fils_central != null)
					liste.addAll(fils_central.construireMot(value + lettre));
			}
			if (fils_droit != null)
				liste.addAll(fils_droit.construireMot(value));
			return liste;
		}
	}

	@Override
	public int nombreNoeuds() {
		return 1 + (fils_gauche != null ? fils_gauche.nombreNoeuds() : 0)
				+ (fils_central != null ? fils_central.nombreNoeuds() : 0)
				+ (fils_droit != null ? fils_droit.nombreNoeuds() : 0);
	}

	@Override
	public int sommeProfondeur(int profondeur) {
		return profondeur
				+ (fils_gauche != null ? fils_gauche
						.sommeProfondeur(profondeur + 1) : 0)
				+ (fils_central != null ? fils_central
						.sommeProfondeur(profondeur + 1) : 0)
				+ (fils_droit != null ? fils_droit
						.sommeProfondeur(profondeur + 1) : 0);
	}

	public static int hauteurSansFilsCentrale(ITrieHybride trie) {
		if (trie == null)
			return 0;
		return 1 + Math.max(hauteurSansFilsCentrale(trie.getFilsGauche()),
				hauteurSansFilsCentrale(trie.getFilsDroit()));
	}

	public static boolean isEquilibre(ITrieHybride trie) {
		if (trie == null)
			return true;
		else {
			if (trie.estVide())
				return true;
			else {
				if (isEquilibre(trie.getFilsCentrale())
						&& isEquilibre(trie.getFilsGauche())
						&& isEquilibre(trie.getFilsDroit())
						&& Math.abs(hauteurSansFilsCentrale(trie.getFilsDroit())
								- hauteurSansFilsCentrale(trie.getFilsGauche())) <= 1)
					return true;
				else
					return false;
			}
		}
	}

	// Just had a rough idea. This has to be reviewed
	// does not work i.e see where the method rotation is called
	
	public static ITrieHybride ajoutEquillibre(ITrieHybride trie, String mot){
		return ajoutEquillibreBis(trie, toWord(mot));
	}
	public static ITrieHybride ajoutEquillibreBis(ITrieHybride trie, String mot) {
		if (trie == null)
			return new TrieHybride(mot);
		else {
			if (trie.estVide())
				return new TrieHybride(mot);
			else {
				if (mot.isEmpty()) {
					return trie;
				}
			}
			final char c = trie.getKey();
			int v = trie.getValeur();
			final String suffixe = mot.substring(1);
			if (c == mot.charAt(0)) {
				if (mot.length() == 1)
					v = 1;
				return new TrieHybride(c, v, ajoutEquillibreBis(
						trie.getFilsCentrale(), suffixe), trie.getFilsGauche(),
						trie.getFilsDroit());
			} else if (c > mot.charAt(0)) {
				int nb = hauteurSansFilsCentrale(trie.getFilsGauche() != null ? trie
						.getFilsGauche().getFilsDroit() : null);
				ITrieHybride newTrie = new TrieHybride(c, v,
						trie.getFilsCentrale(), ajoutEquillibreBis(
								trie.getFilsGauche(), mot), trie.getFilsDroit());
				if (hauteurSansFilsCentrale(newTrie.getFilsGauche())
						- hauteurSansFilsCentrale(newTrie.getFilsDroit()) > 1) {
					// Rotation
					if (hauteurSansFilsCentrale(newTrie.getFilsGauche().getFilsDroit()) - nb > 0) {
						// insertion was made at the right of fils
						// gauche. Left rotate it's fils_droit
						// RotationGauche(newTrie.getFilsGauche(), newTrie
						// .getFilsGauche().getFilsDroit());
						newTrie = RotationGauche(newTrie,
								newTrie.getFilsGauche());
					}
					// Right rotate fils gauche
					// RotationDroit(newTrie, newTrie.getFilsGauche());
					newTrie = RotationDroit(newTrie, newTrie);
				}
				return newTrie;

			} else {
				int nb = hauteurSansFilsCentrale(trie.getFilsDroit() != null ? trie
						.getFilsDroit().getFilsGauche() : null);
				ITrieHybride newTrie = new TrieHybride(c, v,
						trie.getFilsCentrale(), trie.getFilsGauche(),
						ajoutEquillibreBis(trie.getFilsDroit(), mot));
				if (hauteurSansFilsCentrale(newTrie.getFilsDroit())
						- hauteurSansFilsCentrale(newTrie.getFilsGauche()) > 1) {
					if (hauteurSansFilsCentrale(newTrie.getFilsDroit().getFilsGauche()) - nb > 0) {
						// insertion was made at the left of fils
						// droit. Right rotate it's fils_droit
						// RotationDroit(newTrie.getFilsDroit(), newTrie
						// .getFilsDroit().getFilsGauche());
						newTrie = RotationDroit(newTrie, newTrie.getFilsDroit());
					}

					// left rotate fils droit
					// RotationGauche(newTrie, newTrie.getFilsDroit());
					newTrie = RotationGauche(newTrie, newTrie);
				}
				return newTrie;
			}
		}
	}

	public static ITrieHybride equillibre(ITrieHybride trie) {
		if (isEquilibre(trie))
			return trie;
		else {
			ArrayList<String> mots = trie.ListeMots();
			ITrieHybride newTrie = new TrieHybride();
			for (String mot : mots) {
				newTrie = ajoutEquillibre(newTrie, mot);
			}
			return newTrie;
		}
	}
	 

	// Just had a rough idea. This has to be reviewed
	@Deprecated
	public static ITrieHybride equilibre(ITrieHybride trie) {
		if (isEquilibre(trie))
			return trie;
		else {
			ITrieHybride fc = equilibre(trie.getFilsCentrale());
			ITrieHybride fg = equilibre(trie.getFilsGauche());
			ITrieHybride fd = equilibre(trie.getFilsDroit());
			ITrieHybride newTrie = new TrieHybride(trie.getKey(),
					trie.getValeur(), fc, fg, fd);
			if (isEquilibre(newTrie))
				return newTrie;
			else { // Look the difference between heights of fils gauche and
					// fils droit i.e we have to rotate to get the right heigh
					// difference
				fg = newTrie.getFilsGauche();
				fd = newTrie.getFilsDroit();

				while (hauteurSansFilsCentrale(newTrie.getFilsGauche())
						- hauteurSansFilsCentrale(newTrie.getFilsDroit()) > 1) {
					// Successive right rotate
					if (fg == null) {
						return newTrie;
					}

					if (fg.getFilsGauche() == null && fg.getFilsDroit() != null) {
						newTrie = RotationGauche(newTrie, fg);
					}

					newTrie = RotationDroit(newTrie, newTrie);
					System.out.println("rotate");
				}

				while (hauteurSansFilsCentrale(newTrie.getFilsDroit())
						- hauteurSansFilsCentrale(newTrie.getFilsGauche()) > 1) {
					// Successive left rotation
					//System.out.println(fd);
					if (fd == null) {
						return newTrie;
					}
					if (fd.getFilsDroit() == null && fd.getFilsGauche() != null) {
						newTrie = RotationDroit(newTrie, fd);
					}

					newTrie = RotationGauche(newTrie, newTrie);
					System.out.println("rotate");
				}
				return equilibre(newTrie); //  Problem
			}
		}
	}
	
	@Override
	public void affiche(String value) {
		if (!estVide()) {
			if (fils_gauche != null)
				fils_gauche.affiche(value);
			if (wordPresent())
				System.out.println(value + lettre);
			if (fils_central != null)
				fils_central.affiche(value + lettre);
			if (fils_droit != null)
				fils_droit.affiche(value);
		}
	}

	@Override
	@Deprecated
	public int compteMots() {
		return ListeMots().size();
	}

	@Override
	public void setFilsGauche(ITrieHybride arbre) {
		this.fils_gauche = arbre;
		if (fils_gauche != null)
			((TrieHybride) fils_gauche).setPere(this);
	}

	@Override
	public void setFilsDroit(ITrieHybride arbre) {
		this.fils_droit = arbre;
		if (fils_droit != null)
			((TrieHybride) fils_droit).setPere(this);
	}

	@Override
	public void setFilsCentral(ITrieHybride arbre) {
		this.fils_central = arbre;
		if (fils_central != null)
			((TrieHybride) fils_central).setPere(this);
	}

	@Override
	public void setWordPresence() {
		valeur = 1;
	}

	@Override
	public void setWordAbsence() {
		valeur = WORD_ABSENCE;
	}

	@Override
	public boolean wordPresent() {
		return valeur != WORD_ABSENCE;
	}

	@Override
	public int getValeur() {
		return valeur;
	}

	public static String toWord(String s) {
		StringBuffer res = new StringBuffer();
		int size = s.length();
		for (int i = 0; i < size; i++) {
			char c = s.charAt(i);
			if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
				res.append(c);
			}
		}
		return res.toString();
	}

	public void afficheArbre(int k) {
		if (!estVide()) {
			for (int i = 0; i < k; i++) {
				System.out.print("\t");
			}
			System.out.print(lettre);
			if (wordPresent())
				System.out.print(" v" + valeur);
			System.out.println();
			if (fils_gauche != null) {
				System.out.print("ga ");
				((TrieHybride) fils_gauche).afficheArbre(k + 1);
			}

			if (fils_central != null) {
				System.out.print("ce ");
				((TrieHybride) fils_central).afficheArbre(k + 1);
			}

			if (fils_droit != null) {
				System.out.print("dr ");
				((TrieHybride) fils_droit).afficheArbre(k + 1);
			}

		}
	}

	public static void main(String[] args) {
		ITrieHybride trie = new TrieHybride();
		// trie = (ITrieHybride) trie.ajouter("a").ajouter("b").ajouter("c");
		// System.out.println(isEquilibre(trie));
		/*
		 * trie.afficheArbre(0); trie = equilibre(trie);
		 */
		// trie = RotationGauche(trie, trie);

		trie = (ITrieHybride) trie.ajouter("aaa").ajouter("b").ajouter("ccc")
				.ajouter("cc").ajouter("d").ajouter("e").ajouter("fg")
				.ajouter("gf").ajouter("g").ajouter("c").ajouter("a")
				.ajouter("aa").ajouter("b").ajouter("cc").ajouter("bb")
				.ajouter("c");
		trie = equilibre(trie);
		System.out.println(isEquilibre(trie));

		/*
		 * trie = TrieHybride.AjoutEquillibre(trie, "aaa"); trie =
		 * TrieHybride.AjoutEquillibre(trie, "b"); trie =
		 * TrieHybride.AjoutEquillibre(trie, "ccc"); trie =
		 * TrieHybride.AjoutEquillibre(trie, "cc"); trie =
		 * TrieHybride.AjoutEquillibre(trie, "d"); trie =
		 * TrieHybride.AjoutEquillibre(trie, "e"); trie =
		 * TrieHybride.AjoutEquillibre(trie, "fg"); trie =
		 * TrieHybride.AjoutEquillibre(trie, "gf"); trie =
		 * TrieHybride.AjoutEquillibre(trie, "g");
		 * 
		 * trie = TrieHybride.AjoutEquillibre(trie, "c"); trie =
		 * TrieHybride.AjoutEquillibre(trie, "a"); trie =
		 * TrieHybride.AjoutEquillibre(trie, "aa"); trie =
		 * TrieHybride.AjoutEquillibre(trie, "b"); trie =
		 * TrieHybride.AjoutEquillibre(trie, "cc"); trie =
		 * TrieHybride.AjoutEquillibre(trie, "bb"); trie =
		 * TrieHybride.AjoutEquillibre(trie, "c"); trie = equilibre(trie);
		 */
		// System.out.println(isEquilibre(trie));
		// System.out.println(maxFilsGD(trie.getFilsDroit()));
		// System.out.println(maxFilsGD(trie.getFilsGauche()));
		trie.afficheArbre(0);
		// trie = TrieHybride.equillibre(trie);
		// System.out.println(isEquilibre(trie));
		return;
	}

	// T points where the root of the our tree is
	// C.f Introduction to Algorithms 3rd Edition by THOMAS H. CORMEN
	// page 313
	public static ITrieHybride RotationGauche(ITrieHybride T, ITrieHybride x) {
		ITrieHybride y = x.getFilsDroit();
		x.setFilsDroit(y.getFilsGauche());
		if (y.getFilsGauche() != null)
			y.getFilsGauche().setPere(x);
		y.setPere(x.getPere());
		if (x.getPere() == null) {
			T = y;
		}// return??
		else if (x == x.getPere().getFilsGauche()) {
			x.getPere().setFilsGauche(y);
		} else {
			x.getPere().setFilsDroit(y);

		}
		y.setFilsGauche(x);
		x.setPere(y);
		return T;
	}

	// T points where the root of the our tree is
	// C.f Introduction to Algorithms 3rd Edition by THOMAS H. CORMEN
	// page 313
	public static ITrieHybride RotationDroit(ITrieHybride T, ITrieHybride x) {
		ITrieHybride y = x.getFilsGauche();
		x.setFilsGauche(y.getFilsDroit());
		if (y.getFilsDroit() != null)
			y.getFilsDroit().setPere(x);
		y.setPere(x.getPere());
		if (x.getPere() == null)
			T = y;
		else if (x == x.getPere().getFilsDroit())
			x.getPere().setFilsDroit(y);
		else
			x.getPere().setFilsGauche(y);
		y.setFilsDroit(x);
		x.setPere(y);
		return T;
	}
}
