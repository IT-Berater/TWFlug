package de.wenzlaff.twflug.be;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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

public class FlugInfosTest {

	private FlugInfos flugInfo;

	@BeforeAll
	public void setUp() {
		flugInfo = new FlugInfos();
	}

	@Test
	public void testFlugInfos() {
		assertNotNull(flugInfo);
	}

	@Test
	public void testAddNull() {
		FieldDataRaw nachricht = null;
		flugInfo.addNachricht(nachricht);
	}

	@Test
	public void testAddOhneKey() {
		FieldDataRaw nachricht = new FieldDataRaw();
		flugInfo.addNachricht(nachricht);
		assertEquals(0, flugInfo.getMaxAnzahlFlugzeuge());
	}

	@Test
	public void testAddEinSatz() {
		FieldDataRaw nachricht = new FieldDataRaw();
		nachricht.setHexIdent("123456");
		flugInfo.addNachricht(nachricht);
		assertEquals(1, flugInfo.getMaxAnzahlFlugzeuge());
	}

	@Test
	public void testAddZweiSatz() {
		FieldDataRaw nachricht = new FieldDataRaw();
		nachricht.setHexIdent("123456");
		flugInfo.addNachricht(nachricht);

		FieldDataRaw nachricht2 = new FieldDataRaw();
		nachricht2.setHexIdent("2222");
		flugInfo.addNachricht(nachricht2);
		assertEquals(2, flugInfo.getMaxAnzahlFlugzeuge());
	}

	@Test
	public void testAddZweiSatzZweiNachrichten() {
		FieldDataRaw nachricht = new FieldDataRaw();
		nachricht.setHexIdent("123456");
		flugInfo.addNachricht(nachricht);

		FieldDataRaw nachricht2 = new FieldDataRaw();
		nachricht2.setHexIdent("2222");
		flugInfo.addNachricht(nachricht2);
		nachricht.setAircraftId("2");
		flugInfo.addNachricht(nachricht2);
		assertEquals(2, flugInfo.getAlleNachrichtenZumFlugzeug("2222").size());
		assertEquals(1, flugInfo.getAlleNachrichtenZumFlugzeug("123456").size());

		assertEquals(2, flugInfo.getMaxAnzahlFlugzeuge());
	}
}
