package de.wenzlaff.twflug;

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.jupiter.api.Test;

import de.wenzlaff.twflug.be.FieldDataRaw;

public class UtilTest {

	@Test
	public void testGetFieldData() {

		// Test
		// Feld:--1--,2,-3-,--4--,--5---,--6---,------7---,-----8------,----9-----,-----10-----,--11----,-12-
		String data = "MSG,5,111,11111,3C6DCF,111111,2014/11/10,17:20:57.552,2014/11/10,17:20:57.543,GWI53X  ,28275,,,,,,,0,,0,0";
		FieldDataRaw raw = Util.getFieldData(data);
		assertNotNull(raw);

		assertEquals("MSG", raw.getMessageType()); // Feld 1
		assertEquals("5", raw.getTransmissionType()); // 2
		assertEquals("111", raw.getSessionId()); // 3
		assertEquals("11111", raw.getAircraftId()); // 4
		assertEquals("3C6DCF", raw.getHexIdent()); // 5
		assertEquals("111111", raw.getFlightId()); // 6
		assertEquals("2014/11/10", raw.getDateMessageGenerated()); // 7
		assertEquals("17:20:57.552", raw.getTimeMessageGenerated()); // 8
		assertEquals("2014/11/10", raw.getDateMessageLogged()); // 9
		assertEquals("17:20:57.543", raw.getTimeMessageLogged()); // 10
	}

	@Test
	public void testGetOutputDatei() {
		File datei = Util.getLokaleOutputDatei();
		assertNotNull(datei);
		assertEquals(false, datei.getName().isEmpty());
	}

	@Test
	public void testGetEntfernteOutputDatei() {
		File datei = Util.getEntfernteOutputDatei();
		assertNotNull(datei);
		assertEquals(false, datei.getName().isEmpty());
	}

}
