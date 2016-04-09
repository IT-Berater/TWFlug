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

/**
 * Test Positionen und Langenhagen.
 * 
 * @author Thomas Wenzlaff
 * @version 0.1
 * @since 11.11.2014
 */
public class TestPositionen {

	private List<Position> positionen = new ArrayList<Position>();
	/**
	 * Die Home Position (Langenhagen).
	 */
	public static final Position HOME = new Position(52.4548, 9.7123);

	public TestPositionen() {
		// links unten
		Position p1 = new Position(52.3781, 9.0037);
		// rechts unten
		Position p2 = new Position(52.8197, 9.0248);
		// links oben
		Position p3 = new Position(52.3133, 10.1586);
		positionen.add(p1);
		positionen.add(p2);
		positionen.add(p3);
	}

	public List<Position> getPositionen() {
		return positionen;
	}

	public void setPositionen(List<Position> positionen) {
		this.positionen = positionen;
	}

}
