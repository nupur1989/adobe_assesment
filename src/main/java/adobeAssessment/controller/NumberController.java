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
    public ResponseEntity<NumberEntity> getConversion(@RequestParam(name = "query") String input) {

        try {
            log.info("NumberController:Received request to convert number:" + input);

            if (input == null || input.isEmpty() || Integer.parseInt(input) < 0 || Integer.parseInt(input) > 3999) {
                return ResponseEntity.badRequest().build();
            }
            NumberEntity numberEntity = numberService.getRoman(input);
            return ResponseEntity.ok().body(numberEntity);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error happened.");
        }
    }
}
