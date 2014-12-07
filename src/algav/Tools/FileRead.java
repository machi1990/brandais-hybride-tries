/** 
 * Classes authors. 
 * @author Chitimbo Manyanda a Master Student at the University of Pierre and Marie Curie. 
 * @author Larbi Mohamed  Youcef   also a Master Student at the same university
 */

package algav.Tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileRead {
	
	public static String[] readWord(File fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			String everything = sb.toString();
			return everything.split(System.lineSeparator());
		} finally {
			br.close();
		}
	}

	public static String[] readDirectory(String nomDirectory) throws IOException {
		File dir = new File(nomDirectory);
		String[] buffer = new String[0];
		if (!dir.isDirectory()) {
			return buffer;
		} else {
			String[] files = dir.list();

			for (int i = 0; i < files.length; i++) {
				File temp = new File(dir.getName()+"/"+files[i]);
				if (temp.isFile()) {
					buffer = concat(buffer, readWord(temp));
				}
			}
			return buffer;
		}

	}

	private static String[] concat(String[] first, String[] second) {
		List<String> both = new ArrayList<String>(first.length + second.length);
		Collections.addAll(both, first);
		Collections.addAll(both, second);
		return both.toArray(new String[both.size()]);
	}
}
