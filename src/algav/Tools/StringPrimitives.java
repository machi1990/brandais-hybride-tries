package algav.Tools;

public class StringPrimitives {
	public StringPrimitives() {
	}

	public static char prem(String mot) {
		if (mot.isEmpty())
			return (char)0; // à revoir
		else
			return mot.charAt(0);
	}

	public static int longeur(String mot) {
		return mot.length();
	}

	public static String suffixe(String mot, String prefixe) {

		return null; // à revoir
	}

	public static char car(String mot, int index) {
		if (index >= 0 && !mot.isEmpty())
			return mot.charAt(index);
		else
			return (char)0;
	}
}
