package de.wenzlaff.twflug.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Point;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.dial.DialBackground;
import org.jfree.chart.plot.dial.DialPlot;
import org.jfree.chart.plot.dial.DialPointer;
import org.jfree.chart.plot.dial.DialTextAnnotation;
import org.jfree.chart.plot.dial.DialValueIndicator;
import org.jfree.chart.plot.dial.StandardDialFrame;
import org.jfree.chart.plot.dial.StandardDialScale;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.StandardGradientPaintTransformer;

import de.wenzlaff.twflug.be.Parameter;

/**
 * Das Hauptfenster der Anwendung.
 * 
 * @author Thomas Wenzlaff
 * @version 0.1
 * @since 11.12.2014
 */
public class HauptFenster {

	private final JFrame frame = new JFrame();

	private static DefaultValueDataset anzahlFlugzeuge;

	private DialPlot plot;

	private static boolean isNoGui;

	public void aktualisieren(int maxAnzahl) {
		anzahlFlugzeuge = new DefaultValueDataset(maxAnzahl);
		this.plot.setDataset(anzahlFlugzeuge);
	}

	public HauptFenster(Parameter parameter) {

		isNoGui = parameter.isNoGui();
		this.frame.setPreferredSize(new Dimension(parameter.getBreite(), parameter.getHoehe()));
		this.frame.add(this.buildDialPlot(parameter.getMinCount(), parameter.getMaxCount(), 10));
		this.frame.pack();
		this.frame.setLocationRelativeTo(null);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setTitle("Anzahl sichtbarer Flugzeuge");

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				HauptFenster.this.frame.setVisible(!isNoGui);
			}
		});
	}

	private ChartPanel buildDialPlot(int minimumValue, int maximumValue, int majorTickGap) {

		this.plot = new DialPlot(anzahlFlugzeuge);
		this.plot.setDialFrame(new StandardDialFrame());

		// in der Mitte nur ganze Zahlen anzeigen
		DialValueIndicator wert = new DialValueIndicator(0);
		wert.getNumberFormat().setMinimumFractionDigits(0);
		this.plot.addLayer(wert);

		this.plot.addLayer(new DialPointer.Pointer());

		// lila Hintergrund leicht verlaufend von oben
		GradientPaint graPaint = new GradientPaint(new Point(), new Color(255, 255, 255), new Point(), new Color(170, 170, 220));
		DialBackground background = new DialBackground(graPaint);
		background.setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.VERTICAL));
		this.plot.setBackground(background);

		// Skala
		StandardDialScale scale = new StandardDialScale(minimumValue, maximumValue, -120, -300, majorTickGap, majorTickGap - 1);
		scale.setTickRadius(0.88);
		scale.setTickLabelOffset(0.20);
		scale.setTickLabelFormatter(new DecimalFormat());
		this.plot.addScale(0, scale);

		// Label unten in der Mitte
		DialTextAnnotation annotation1 = new DialTextAnnotation("Flugzeuge");
		annotation1.setFont(new Font("Dialog", Font.BOLD, 16));
		annotation1.setRadius(0.7);
		this.plot.addLayer(annotation1);

		ChartPanel chartPanel = new ChartPanel(new JFreeChart(this.plot));

		return chartPanel;
	}

}
