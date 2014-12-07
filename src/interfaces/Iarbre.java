/** 
 * Interface authors. 
 * @author Chitimbo Manyanda a Master Student at the University of Pierre and Marie Curie. 
 * @author Larbi Mohamed  Youcef   also a Master Student at the same university
 */

package interfaces;

import java.util.ArrayList;

public interface Iarbre {
	Iarbre ajouter(String mot);

	Iarbre supprimer(String mot);

	int nombreNoeuds();

	int sommeProfondeur(int profondeur);

	boolean Recherche(String mot);

	int ComptageMots();

	ArrayList<String> ListeMots();

	boolean estVide();

	int ComptageNil();

	int Hauteur();

	float profondeurMoyenne();

	int prefixe(String mot);

	ArrayList<String> construireMot(String value);

	void affiche(String value);

	int compteMots();

}
