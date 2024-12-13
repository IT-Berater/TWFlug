package de.wenzlaff.twflug.be;

import java.util.ArrayList;
import java.util.List;

/**
 * Test Positionen und Langenhagen.
 * 
 * @author Thomas Wenzlaff
 */
public class TestPositionen {

	private List<Position> positionen = new ArrayList<>();
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
