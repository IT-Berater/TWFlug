package de.wenzlaff.twflug.be;

/*
 * #%L
 * twflug
 * %%
 * Copyright (C) 2015 Thomas Wenzlaff
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Die fachliche FlugInfo. Hält alle empfangene Nachichten.
 * 
 * @author Thomas Wenzlaff
 * @version 0.1
 * @since 21.12.2014
 */
public class FlugInfos {

	private static final Logger LOG = LogManager.getLogger(FlugInfos.class.getName());

	/** Die empfangenen FlugInfos. Key: hexIdent Key ist die ICAO oder auch HexIdent. */
	private Map<String, List<FieldDataRaw>> flugInfos;

	/** Die Parameter. */
	private Parameter parameter;

	public FlugInfos() {
		flugInfos = new HashMap<String, List<FieldDataRaw>>();
	}

	public void addNachricht(final FieldDataRaw nachricht) {

		if (nachricht != null) {

			if (isNachrichtValid(nachricht)) {

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
			} else {
				if (parameter != null && parameter.isDebug()) {
					LOG.info("INFO: Es gibt kein Key (HexIdent) in der Nachricht. Flugzeug bzw. Nachricht kann nicht identifiziert werden und wird ignoriert. Die Fehlerhafte Nachricht: "
							+ nachricht);
				}
			}
		} else {
			if (parameter != null && parameter.isDebug()) {
				LOG.info("INFO: Es muss eine Nachricht zum hinzufügen übergeben werden. Nachricht = null");
			}
		}
	}

	private boolean isNachrichtValid(final FieldDataRaw nachricht) {
		// ohne key sind die Daten nicht vollständig
		return !(nachricht.getHexIdent() == null || nachricht.getHexIdent().isEmpty());
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

	public void setParameter(Parameter parameter) {
		this.parameter = parameter;
	}

}
