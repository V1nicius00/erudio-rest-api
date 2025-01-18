package dev.vini.erudio_microservice.service;

import org.springframework.stereotype.Service;

@Service
public class MathService {

    public Double sum (String numberOne, String numberTwo){
        return convertToDouble(numberOne) + convertToDouble(numberTwo);
    }

    public Double subtraction (String numberOne, String numberTwo){
        return convertToDouble(numberOne) - convertToDouble(numberTwo);
    }

    public Double multiplication (String numberOne, String numberTwo){
        return convertToDouble(numberOne) * convertToDouble(numberTwo);
    }

    public Double division (String numberOne, String numberTwo){
        return convertToDouble(numberOne) / convertToDouble(numberTwo);
    }

    public Double mean (String numberOne, String numberTwo){
        return (convertToDouble(numberOne) + convertToDouble(numberTwo)) / 2;
    }

    public Double squareRoot (String number){
        return Math.sqrt(convertToDouble(number));
    }

    public Double convertToDouble(String strNumber) {
        if(strNumber == null) return 0D;
        String number = strNumber.replace(",",".");
        if(isNumeric(number)) return Double.parseDouble(number);
        return 0D;
    }

    public boolean isNumeric(String strNumber) {
        if(strNumber == null) return false;
        String number = strNumber.replace(",",".");
        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }

}
