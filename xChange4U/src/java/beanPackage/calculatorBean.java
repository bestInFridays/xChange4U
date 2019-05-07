/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanPackage;

import calculator.Calculator_Service;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.xml.ws.WebServiceRef;

/**
 *
 * @author Stern
 */
@Named(value = "calculatorBean")
@SessionScoped
public class calculatorBean implements Serializable {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/_xc4ux_/Calculator.wsdl")
    private Calculator_Service service;
    
    String calculation;
    String calculateMethod = "BOD";
    String output;
    
    /**
     * Creates a new instance of calculatorBean
     */
    public calculatorBean() {
    }

    public String getCalculation() {
        return calculation;
    }

    public void setCalculation(String calculation) {
        this.calculation = calculation;
    }

    public String getCalculateMethod() {
        return calculateMethod;
    }

    public void setCalculateMethod(String calculateMethod) {
        this.calculateMethod = calculateMethod;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    private String bodCalculation(java.lang.String inputCalculation) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        calculator.Calculator port = service.getCalculatorPort();
        return port.bodCalculation(inputCalculation);
    }

    private String ltrCalculation(java.lang.String inputCalculation) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        calculator.Calculator port = service.getCalculatorPort();
        return port.ltrCalculation(inputCalculation);
    }
    
    public void callCalculate(){
        if(this.calculation.matches("^[\\d\\+\\/\\*\\.\\-]*$")&&this.calculation.length() > 0){
            if(this.calculateMethod.equals("BOD"))
                this.output = String.format("%.2f", Double.parseDouble(bodCalculation(calculation)));
            else
                this.output = String.format("%.2f", Double.parseDouble(ltrCalculation(calculation)));
        }
        else
            this.output = "Invalid input. Please try again.";
    }
    
}
