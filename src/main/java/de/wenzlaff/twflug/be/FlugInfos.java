package de.wenzlaff.twflug.be;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Die fachliche FlugInfo. Hält alle empfangene Nachichten.
 * 
 * @author Thomas Wenzlaff
 *
 */
public class FlugInfos {

	/**
	 * Die empfangenen FlugInfos. Key: hexIdent Key ist die ICAO oder auch HexIdent.
	 */
	private Map<String, List<FieldDataRaw>> flugInfos;

	public FlugInfos() {
		flugInfos = new HashMap<String, List<FieldDataRaw>>();
	}

	public void addNachricht(final FieldDataRaw nachricht) {

		if (nachricht == null) {
			throw new IllegalArgumentException("Es muss eine Nachricht zum hinzufügen übergeben werden.");
		}

		if (isNachrichtValid(nachricht)) {
			throw new IllegalArgumentException("Es gibt kein Key (HexIdent) in der Nachricht. Flugzeug bzw. Nachricht kann nicht identifiziert werden");
		}

		// testen ob Key schon vorhanden
		String key = nachricht.getHexIdent();
		if (flugInfos.containsKey(key)) {
			List<FieldDataRaw> satz = flugInfos.get(key);
			satz.add(nachricht);
		} else {
			// nicht vorhanden
			List<FieldDataRaw> satz = new ArrayList<FieldDataRaw>();
			satz.add(nachricht);
			flugInfos.put(key, satz);
		}
	}

	private boolean isNachrichtValid(final FieldDataRaw nachricht) {
		// ohne key sind die Daten nicht vollständig
		return nachricht.getHexIdent() == null || nachricht.getHexIdent().isEmpty();
	}

	public int getMaxAnzahlFlugzeuge() {
		return flugInfos.size();
	}

	public List<FieldDataRaw> getAlleNachrichtenZumFlugzeug(String key) {
		return flugInfos.get(key);
	}

	public void setMaxAnzahlFlugzeugeAufNull() {
		flugInfos.clear();
	}
}
