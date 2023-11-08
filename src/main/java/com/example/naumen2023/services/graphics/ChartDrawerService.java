package com.example.naumen2023.services.graphics;

import com.example.naumen2023.services.graphics.interfaces.ChartDrawerInterface;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.XYDataset;

import java.awt.*;
import java.time.LocalDate;

public class ChartDrawerService implements ChartDrawerInterface {
    @Override
    public JFreeChart createPieChart(String titleChart, PieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart(
                titleChart,
                dataset,
                true,
                true,
                false);
        TextTitle title = chart.getTitle();
        title.setPaint(Color.black);
        title.setFont(new Font("Times New Roman", Font.ITALIC, 24));
        TextTitle source = new TextTitle(
                LocalDate.now().toString(),
                new Font("Times New Roman", Font.BOLD, 20)
        );
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);

        chart.addSubtitle(source);
        chart.setBackgroundPaint(new Color(117, 120, 120));

        return chart;
    }

    @Override
    public JFreeChart createCategoryChart(String titleChart, String titleX, String titleY,
                                          CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                titleChart,
                titleX,
                titleY,
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
                );

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(new Color(117, 120, 120));
        TextTitle title = chart.getTitle();
        title.setPaint(Color.black);
        title.setFont(new Font("Times New Roman", Font.ITALIC, 24));
        TextTitle source = new TextTitle(
                LocalDate.now().toString(),
                new Font("Times New Roman", Font.BOLD, 20)
        );
        chart.addSubtitle(source);

        CategoryAxis axis = plot.getDomainAxis();
        axis.setCategoryLabelPositions(
                CategoryLabelPositions.createUpRotationLabelPositions(Math.PI /4)
        );

        return chart;
    }

    @Override
    public JFreeChart createTimeSeriesChart(XYDataset dataset) {
        return null;
    }
}
