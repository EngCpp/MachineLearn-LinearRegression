package com.engcpp.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.UUID;
import javax.swing.JDialog;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author engcpp
 */
public class ChartUtils {
    
    
    public static void showChart(XYSeriesCollection dataset, 
            String title, String xLabel, String yLable) {
        String chartTitle = title;
        String xAxisLabel = xLabel;
        String yAxisLabel = yLable;

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesFilled(0, false);
        renderer.setSeriesLinesVisible(1, true);
        renderer.setSeriesShapesVisible(1, false);
                
        JFreeChart chart = ChartFactory.createXYLineChart(chartTitle, 
           xAxisLabel, yAxisLabel, dataset, PlotOrientation.VERTICAL, 
           false, false, false);

        chart.getXYPlot().setRenderer(renderer);
        
        
        // Create and show dlg
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        
        JDialog dlg = new JDialog();        
        
        dlg.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dlg.setModal(true);
        dlg.setSize(800, 600);                
        dlg.setLocation((screen.width - dlg.getWidth())/2,
                (screen.height- dlg.getHeight())/2);
        dlg.getContentPane().add(new ChartPanel(chart));
        dlg.show();   
    }
    
    
    public static void plotToDataset(XYSeriesCollection dataset, Matrix matrix) {        
        XYSeries series1 = new XYSeries(UUID.randomUUID());
        
        for (int row=0; row<matrix.rowsCount(); row++)
             series1.add(matrix.get(row, 0), matrix.get(row, 1));

        dataset.addSeries(series1);
    }
}
