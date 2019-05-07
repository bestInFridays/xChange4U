/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanPackage;

import currencyConversion.Currency;
import currencyConversion.CurrencyConvertor;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.xml.ws.WebServiceRef;

/**
 *
 * @author Stern
 */
@Named(value = "currencyConversionBean")
@SessionScoped
public class currencyConversionBean implements Serializable {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/www.webservicex.net/CurrencyConvertor.asmx.wsdl")
    private CurrencyConvertor service;
    
    private String amout;
    private String fromOption = "MYR";
    private String toOption = "GBP";
    private String output;
    
    public currencyConversionBean() {
    }

    public String getAmout() {
        return amout;
    }

    public void setAmout(String amout) {
        this.amout = amout;
    }

    public String getFromOption() {
        return fromOption;
    }

    public void setFromOption(String fromOption) {
        this.fromOption = fromOption;
    }

    public String getToOption() {
        return toOption;
    }

    public void setToOption(String toOption) {
        this.toOption = toOption;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    private double conversionRate(currencyConversion.Currency fromCurrency, currencyConversion.Currency toCurrency) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        currencyConversion.CurrencyConvertorSoap port = service.getCurrencyConvertorSoap();
        return port.conversionRate(fromCurrency, toCurrency);
    }
    
    public void callConvert(){
        if(this.amout.matches("[0-9.]*")&&this.amout.length() > 0)
            this.output = this.amout + " " + this.fromOption + " equals " + String.format("%.2f", (Double.parseDouble(this.amout)*this.conversionRate(Currency.valueOf(this.fromOption), Currency.valueOf(this.toOption)))) + " " + this.toOption;
        else
            this.output = "Invalid input. Please try again.";
    }    
}
