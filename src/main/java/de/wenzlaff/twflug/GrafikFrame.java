package de.wenzlaff.twflug;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Der Grafik Frame für die empfangenen Flugzeuge.
 * 
 * http://stackoverflow.com/questions/7205742/adding-points-to-xyseries-
 * dynamically-with-jfreechart
 * 
 * http://www.codejava.net/java-se/graphics/using-jfreechart-to-draw-xy-line-
 * chart-with-xydataset
 * 
 * 
 * @author Thomas Wenzlaff
 * @version 0.1
 * @since 11.11.2014
 */
public class GrafikFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final String title = "Empfangene Flugzeuge";

	private XYSeriesCollection xySeriesCollection;

	public GrafikFrame(String s) {
		super(s);

		xySeriesCollection = new XYSeriesCollection();

		final ChartPanel chartPanel = createPanel();
		this.add(chartPanel, BorderLayout.CENTER);
		JPanel control = new JPanel();
		control.add(new JButton(new AbstractAction("Löschen") {

			@Override
			public void actionPerformed(ActionEvent e) {
				xySeriesCollection.removeAllSeries();
			}
		}));
		control.add(new JButton(new AbstractAction("Aktualisieren") {

			@Override
			public void actionPerformed(ActionEvent e) {

			}
		}));
		this.add(control, BorderLayout.SOUTH);
	}

	private ChartPanel createPanel() {

		JFreeChart jfreechart = ChartFactory.createXYLineChart("Flugzeuge", "Langitude (Breitengrade, x)",
				"Longitude (Längengrade, y)", getData(), PlotOrientation.VERTICAL, false, true, false);

		XYPlot xyPlot = (XYPlot) jfreechart.getPlot();
		xyPlot.setDomainCrosshairVisible(true);
		xyPlot.setRangeCrosshairVisible(true);

		NumberAxis domain = (NumberAxis) xyPlot.getDomainAxis();
		domain.setVerticalTickLabels(true);
		return new ChartPanel(jfreechart);
	}

	private XYDataset getData() {

		xySeriesCollection = new XYSeriesCollection();

		// DIE REFERENZ in das Testpackage baut nicht
		// TestPositionen t = new TestPositionen();
		// List<Position> testPos = t.getPositionen();
		//
		// for (int i = 0; i < testPos.size(); i++) {
		//
		// XYSeries series = new XYSeries(i);
		//
		// double x = testPos.get(i).getLatitude();
		// double y = testPos.get(i).getLongitude();
		// series.add(x, y);
		// series.add(TestPositionen.HOME.getLatitude(),
		// TestPositionen.HOME.getLongitude());
		// xySeriesCollection.addSeries(series);
		// }

		return xySeriesCollection;
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				GrafikFrame demo = new GrafikFrame(title);
				demo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				demo.pack();
				demo.setLocationRelativeTo(null);
				demo.setVisible(true);
			}
		});
	}

}