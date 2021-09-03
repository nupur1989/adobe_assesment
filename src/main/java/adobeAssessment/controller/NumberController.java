package adobeAssessment.controller;

import adobeAssessment.entity.NumberEntity;
import adobeAssessment.service.NumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.logging.Logger;

@RestController
@RequestMapping("/")
public class NumberController {

    @Autowired
    NumberService numberService;

    static Logger log = Logger.getLogger(NumberController.class.getName());

    @RequestMapping(value= "/romannumeral/{input}", method= RequestMethod.GET)
    public ResponseEntity<NumberEntity> getConversion(@PathVariable(value = "input") String input){

        try{
            log.info("NumberController:Received request to convert number:" + input);

            if(input == null || input.isEmpty() ||  Integer.parseInt(input) < 0 || Integer.parseInt(input) > 3999){
                log.info("here");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad input received");
            }
            NumberEntity numberEntity =  numberService.getRoman(input);
            return ResponseEntity.ok().body(numberEntity);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error happened.");
        }


    }
}
