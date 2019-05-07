/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 *
 * @author Stern
 */
@WebService(serviceName = "Calculator")
public class Calculator {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "bodCalculation")
    public String bodCalculation(@WebParam(name = "inputCalculation") String inputCalculation) {
        ScriptEngineManager engineManager = new ScriptEngineManager();
        ScriptEngine engine = engineManager.getEngineByName("JavaScript");
        String answer = "";
        try {
            answer = String.valueOf(engine.eval(inputCalculation));
        } catch (ScriptException ex) {
            Logger.getLogger(Calculator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return answer;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "ltrCalculation")
    public String ltrCalculation(@WebParam(name = "inputCalculation") String inputCalculation) {
        inputCalculation = inputCalculation.replace(" ", "");
        if(inputCalculation.charAt(0) == '+' || inputCalculation.charAt(0) == '-')
            inputCalculation = "0" + inputCalculation;
        String splitedCalculation[] = inputCalculation.split("(?<=[-+*/])|(?=[-+*/])");
        Double answer = Double.parseDouble(splitedCalculation[0]);
        for(int i=1;i<splitedCalculation.length;i=i+2){
            if(splitedCalculation[i].equals("+"))
                answer = answer + Double.parseDouble(splitedCalculation[i+1]);
            else if(splitedCalculation[i].equals("-"))
                answer = answer - Double.parseDouble(splitedCalculation[i+1]);
            else if(splitedCalculation[i].equals("*"))
                answer = answer * Double.parseDouble(splitedCalculation[i+1]);
            else if(splitedCalculation[i].equals("/"))
                answer = answer / Double.parseDouble(splitedCalculation[i+1]);
        }
        return String.valueOf(answer);
    }
}
