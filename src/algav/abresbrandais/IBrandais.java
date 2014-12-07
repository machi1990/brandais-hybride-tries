/** 
 * Interface authors. 
 * @author Chitimbo Manyanda a Master Student at the University of Pierre and Marie Curie. 
 * @author Larbi Mohamed  Youcef   also a Master Student at the same university
 */
package algav.abresbrandais;

import algav.trihybrides.ITrieHybride;
import interfaces.Iarbre;

public interface IBrandais extends Iarbre {
	boolean isBrandais();

	IBrandais getFils();

	IBrandais getFrere();

	IBrandais getPere();

	ITrieHybride toTrieHybride();

	char getKey();

	void setFils(IBrandais brandais);
	
	void setFrere(IBrandais brandais);

	ITrieHybride toTrie();
}
