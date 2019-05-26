package com.engcpp;

import com.engcpp.utils.ChartUtils;
import com.engcpp.utils.FileUtils;
import com.engcpp.utils.Matrix;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author engcpp
 */
public class LinearRegression {    
    
    LinearRegression(String fileName) throws FileNotFoundException {
        final Matrix data = FileUtils.loadData(fileName);        
        
    // ======================= Part 2: Plotting ================================       
        
        XYSeriesCollection series = new XYSeriesCollection();       
        ChartUtils.plotToDataset(series, data);
    // ChartUtils.showChart(series, null, "Population of City in 10,000s", "Profit in $10,000s");
 
    // =================== Part 3: Cost and Gradient descent ===================
        Matrix theta = new Matrix(2, 1); // default is to be zeros already        
        final Matrix X = data.getCol(0).addColumn(0, 1);
        final Matrix Y = data.getCol(1);
        
    // Some gradient descent settings
        final int iterations = 1500;
        final double alpha = 0.01;
        
    // compute and display initial cost
        double J = computeCost(X, Y, theta);
        System.out.println("With theta = [0 ; 0], Cost computed = " + J);
        System.out.println("Expected cost value (approx) 32.07");
       
    // further testing of the cost function
        J = computeCost(X, Y, new Matrix(new double[][]{{-1}, {2}}));
        System.out.println("With theta = [-1 ; 2], Cost computed = "+ J);
        System.out.println("Expected cost value (approx) 54.24");        
        System.out.println("=================================================");
        
        theta = gradientDescent(X, Y, theta, alpha, iterations);
        System.out.println("Theta found by gradient descent:" + theta);
        System.out.println("Expected theta values (approx): [-3.6303,  1.1664]");        
        
        
        // Plot prediction =====================================================
        final Matrix prediction = new Matrix(data.getCol(0), X.multiply(theta));
        ChartUtils.plotToDataset(series, prediction);
        ChartUtils.showChart(series, null, "Population of City in 10,000s", "Profit in $10,000s");
    }

    
    private double computeCost(Matrix X, Matrix Y, Matrix theta) {
    // Number of training examples
       final double m = Y.rowsCount();
    
       Matrix dev = ( X.multiply(theta) ).subtract(Y);     
       Matrix devT = dev.transpose();
       
       Matrix J = devT.multiply(dev).divide(2 * m);
       
       return J.get(0, 0);
    }
    
    
    private Matrix gradientDescent(Matrix X, Matrix Y, Matrix theta, double alpha, int iterations) {
    // Number of training examples
       final double m = Y.rowsCount();
       
       for (int i = 0; i < iterations; i++) {
    
            Matrix hT =  (( X.multiply(theta) ).subtract(Y)).transpose();

            Matrix deriv0 = (hT.multiply(X.getCol(0))).divide(m);
            Matrix deriv1 = (hT.multiply(X.getCol(1))).divide(m);

            double temp0 = theta.get(0, 0) - alpha * deriv0.get(0, 0);
            double temp1 = theta.get(1, 0) - alpha * deriv1.get(0, 0);

            theta.setWith(0, 0, temp0);
            theta.setWith(1, 0, temp1);
        
       }
       
       return theta;
    }
    
    private Matrix gradientDescentNParams(Matrix X, Matrix Y, Matrix theta, double alpha, int iterations) {
    // Number of training examples
       final double m = Y.rowsCount();
       
       for (int i = 0; i < iterations; i++) {
    
            Matrix hT =  (( X.multiply(theta) ).subtract(Y)).transpose();
            List<Double> tmp = new ArrayList();
            
            for (int param=0; param < theta.rowsCount(); param++) {
                Matrix derivative = (hT.multiply(X.getCol(param))).divide(m);
                
                tmp.add(theta.get(param, 0) - alpha * derivative.get(0, 0));                
            }
            
            for (int j=0; j < tmp.size(); j++)
                theta.setWith(j, 0, tmp.get(j));            
       }
       
       return theta;
    }
    
    public static void main(String args[]) throws FileNotFoundException {
        new LinearRegression("data1.txt");
    }
}
