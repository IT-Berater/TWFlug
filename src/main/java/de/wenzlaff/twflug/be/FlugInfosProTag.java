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
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author Thomas Wenzlaff
 * @version 0.1
 * @since 04.01.2015
 *
 */
public class FlugInfosProTag {

	private static final Logger LOG = LogManager.getLogger(FlugInfosProTag.class.getName());

	/**
	 * Objekt für die Ermittlung der Anzahl der Flugzeuge pro Tag.
	 * 
	 * Die empfangenen FlugInfos. Key: hexIdent Key ist die ICAO oder auch HexIdent.
	 */
	private List<String> flugInfosProTag;

	/** Die Parameter. */
	private Parameter parameter;

	public FlugInfosProTag() {
		flugInfosProTag = new ArrayList<String>();
	}

	public void setParameter(Parameter parameter) {
		this.parameter = parameter;
	}

	public void addNachricht(FieldDataRaw nachricht) {
		if (nachricht != null) {

			if (isNachrichtValid(nachricht)) {

				// testen ob Key schon vorhanden
				String key = nachricht.getHexIdent();
				if (flugInfosProTag.contains(key)) {
					// schon Vorhanden,
				} else {
					// nicht vorhanden
					flugInfosProTag.add(key);
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

	public void setMaxAnzahlFlugzeugeAufNull() {
		flugInfosProTag.clear();
	}

	public int getAnzahlFlugzeugeProTag() {
		return flugInfosProTag.size();
	}

}
