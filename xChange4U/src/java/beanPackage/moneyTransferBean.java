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
@Named(value = "moneyTransferBean")
@SessionScoped
public class moneyTransferBean implements Serializable {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/www.webservicex.net/CurrencyConvertor.asmx.wsdl")
    private CurrencyConvertor service;
    String origin = "MYR";
    String destination = "MYR";
    String sendAmount = "1";
    String receiveAmount = "1";
    String sender;
    String receiver;
    String exchangeRate = "1";
    String validationSender;
    String validationReceiver;
    String output;
    /**
     * Creates a new instance of moneyTransferBean
     */
    public moneyTransferBean() {
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getSendAmount() {
        return sendAmount;
    }

    public void setSendAmount(String sendAmount) {
        this.sendAmount = sendAmount;
    }

    public String getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(String receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getValidationSender() {
        return validationSender;
    }

    public void setValidationSender(String validationSender) {
        this.validationSender = validationSender;
    }

    public String getValidationReceiver() {
        return validationReceiver;
    }

    public void setValidationReceiver(String validationReceiver) {
        this.validationReceiver = validationReceiver;
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
    
    public void sendChange(){
        if(this.sendAmount.matches("[0-9.]*")&&this.sendAmount.length() > 0){
            this.receiveAmount = String.format("%.2f", (Double.parseDouble(this.sendAmount)*this.conversionRate(Currency.valueOf(this.origin), Currency.valueOf(this.destination))));
        }
        exchangeRate = String.format("%.2f", this.conversionRate(Currency.valueOf(this.origin), Currency.valueOf(this.destination)));
    }
    
    public void receiveChange(){
        if(this.receiveAmount.matches("[0-9.]*")&&this.receiveAmount.length() > 0){
            this.sendAmount = String.format("%.2f", (Double.parseDouble(this.receiveAmount)/this.conversionRate(Currency.valueOf(this.origin), Currency.valueOf(this.destination))));
        }
        exchangeRate = String.format("%.2f", this.conversionRate(Currency.valueOf(this.origin), Currency.valueOf(this.destination)));
    }
    
    public void callTransfer(){
        if(this.sender.matches("[a-zA-Z][a-zA-Z ]+")){
            if(this.receiver.matches("[a-zA-Z][a-zA-Z ]+"))
                output = this.sendAmount+" "+this.origin+" amount has been transferred to "+this.receiver+". Thank You.";
            else
                validationReceiver = "This field is required.";
        }
        else{
            validationSender = "This field is required.";
            if(!(this.receiver.matches("[a-zA-Z][a-zA-Z ]+")))
                validationReceiver = "This field is required.";
        }
    }
    
    public void removeValidation(){
        validationSender = "";
        validationReceiver = "";
    }
    
}
