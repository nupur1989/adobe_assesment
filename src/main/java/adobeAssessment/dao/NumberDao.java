package adobeAssessment.dao;

import adobeAssessment.entity.NumberEntity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Repository
public class NumberDao {

    Map<String, NumberEntity> cache = new HashMap<>();

     static Logger log = Logger.getLogger(NumberDao.class.getName());

     public void putNumber(String input, NumberEntity numberEntity) throws Exception{

         log.info("NumberDao:Received request to put number:" + input);

         if(input == null || input.isEmpty())
             throw new Exception("Bad input received");

         cache.put(input, numberEntity);
     }

    public NumberEntity getNumber(String input) throws Exception{

        log.info("NumberDao:Received request to get number:" + input);

        if(input == null || input.isEmpty())
            throw new Exception("Bad input received");

        if(cache.containsKey(input))
            return cache.get(input);

        return null;
    }
}
