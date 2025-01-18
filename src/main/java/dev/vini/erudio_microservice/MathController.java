package dev.vini.erudio_microservice;

import dev.vini.erudio_microservice.exception.UnsupportedMathOperationException;
import dev.vini.erudio_microservice.service.MathService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class MathController {

    private final AtomicLong counter = new AtomicLong();

    private final MathService mathService;

    public MathController(MathService mathService) {
        this.mathService = mathService;
    }

    @RequestMapping("/sum/{numberOne}/{numberTwo}")
    public Double sum(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
    ) throws Exception{
        if(!mathService.isNumeric(numberOne) || !mathService.isNumeric(numberTwo)){
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        }
        return mathService.sum(numberOne, numberTwo);
    }

    @RequestMapping("/sub/{numberOne}/{numberTwo}")
    public Double subtraction(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
    ) throws Exception{
        if(!mathService.isNumeric(numberOne) || !mathService.isNumeric(numberTwo)){
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        }
        return mathService.subtraction(numberOne, numberTwo);
    }

    @RequestMapping("/mult/{numberOne}/{numberTwo}")
    public Double multiplication(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
    ) throws Exception{
        if(!mathService.isNumeric(numberOne) || !mathService.isNumeric(numberTwo)){
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        }
        return mathService.multiplication(numberOne, numberTwo);
    }

    @RequestMapping("/div/{numberOne}/{numberTwo}")
    public Double division(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
    ) throws Exception{
        if(!mathService.isNumeric(numberOne) || !mathService.isNumeric(numberTwo)){
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        }
        return mathService.division(numberOne, numberTwo);
    }

    @RequestMapping("/mean/{numberOne}/{numberTwo}")
    public Double mean(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
    ) throws Exception{
        if(!mathService.isNumeric(numberOne) || !mathService.isNumeric(numberTwo)){
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        }
        return mathService.mean(numberOne, numberTwo);
    }

    @RequestMapping("/squareroot/{number}")
    public Double squareRoot(
            @PathVariable(value = "number") String number
    ) throws Exception{
        if(!mathService.isNumeric(number)){
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        }
        return mathService.squareRoot(number);
    }


}
