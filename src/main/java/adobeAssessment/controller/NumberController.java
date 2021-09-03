package adobeAssessment.controller;

import adobeAssessment.entity.NumberEntity;
import adobeAssessment.service.NumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.logging.Logger;

@RestController
@RequestMapping("/")
public class NumberController {

    @Autowired
    NumberService numberService;

    static Logger log = Logger.getLogger(NumberController.class.getName());

    @RequestMapping(value = "/romannumeral", method = RequestMethod.GET)
    public ResponseEntity<NumberEntity> getConversion(@RequestParam(name = "query") String integer) {

        try {
            log.info("NumberController:Received request to convert number:" + integer);

            if (integer == null || integer.isEmpty() || Integer.parseInt(integer) < 0 || Integer.parseInt(integer) > 3999) {
                return ResponseEntity.badRequest().build();
            }
            NumberEntity numberEntity = numberService.getRoman(integer);
            return ResponseEntity.ok().body(numberEntity);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error happened.");
        }
    }
}
