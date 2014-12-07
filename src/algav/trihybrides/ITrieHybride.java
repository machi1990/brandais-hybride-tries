/** 
 * Interface authors. 
 * @author Chitimbo Manyanda a Master Student at the University of Pierre and Marie Curie. 
 * @author Larbi Mohamed  Youcef   also a Master Student at the same university
 */

package algav.trihybrides;

import algav.abresbrandais.IBrandais;
import interfaces.Iarbre;

public interface ITrieHybride extends Iarbre {
	boolean isTrieHybride();

	IBrandais toBrandais();

	char getKey();

	ITrieHybride getPere();

	ITrieHybride getFilsCentrale();

	ITrieHybride getFilsGauche();

	ITrieHybride getFilsDroit();

	void setFilsGauche(ITrieHybride arbre);

	void setFilsDroit(ITrieHybride arbre);

	void setFilsCentral(ITrieHybride arbre);

	void setWordPresence();

	boolean wordPresent();

	int getValeur();

	void setWordAbsence();

	void setPere(ITrieHybride x);
	
	void afficheArbre(int k);
}
