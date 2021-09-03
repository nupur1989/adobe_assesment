package adobeAssessment.service;

import adobeAssessment.dao.NumberDao;
import adobeAssessment.entity.NumberEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class NumberService {

    @Autowired
    NumberDao numberDao;

    static Logger log = Logger.getLogger(NumberService.class.getName());

    public NumberEntity getRoman(String input) throws Exception{

        log.info("NumberService:Received request to convert number:" + input);
        if(input == null || input.isEmpty())
            throw new Exception("Bad input received");

        NumberEntity cachedResult = numberDao.getNumber(input);
        if(cachedResult!=null){
            return cachedResult;
        }

        //if cache does not has it, calculate roman presentation and add it to cache, also return to client.
        StringBuilder sb = new StringBuilder();
        int num = Integer.parseInt(input);
        while(num > 0){

            if(num >=1000){

                addNumber(num/1000, 'M', sb);
                num = num%1000;

            }
            else if( num >=500){

                if(num < 900){

                    addNumber(num/500, 'D', sb);
                    num = num%500;

                }
                // 900 == 1000-100
                else{

                    addNumbers('C', 'M',sb);
                    num = num%100;
                }

            }

            else if(num >= 100){

                if(num < 400){

                    addNumber(num/100, 'C', sb);
                    num = num%100;

                }
                // 400 = 500-100
                else{

                    addNumbers('C', 'D', sb);
                    num = num%100;
                }

            }

            else if( num >= 50){


                if(num < 90){

                    addNumber(num/50, 'L',sb);
                    num = num%50;
                }

                //90 = 100-10
                else{

                    addNumbers('X', 'C',sb);
                    num = num%10;
                }

            }

            else if(num >= 10){

                if(num < 40 ){

                    addNumber(num/10, 'X',sb);
                    num = num%10;
                }
                //  40 = 50-10
                else{

                    addNumbers('X', 'L',sb);
                    num = num%10;
                }

            }

            else if(num >=5){
                //5,4..8
                if(num < 9){

                    addNumber(num/5, 'V',sb);
                    num = num%5;
                }
                //9 = 10-1
                else{

                    addNumbers('I', 'X',sb);
                    num =0;
                }

            }
            // 1,2,3,4
            else {

                // 1,2,3
                if (num < 4) {

                    addNumber(num, 'I', sb);
                    num = 0;
                }
                //4 = 5-1
                else {

                    addNumbers('I', 'V', sb);
                    num = 0;
                }
            }
        }

        NumberEntity response = NumberEntity.builder().input(input).output(sb.toString()).build();
        //cache result for future requests.
        numberDao.putNumber(input, response);
        return response;
    }

    void addNumber(int n , char ch, StringBuilder sb){
        int i = 0;
        while(i < n){
            sb.append(ch);
            i++;
        }
    }

    void addNumbers(char ch1, char ch2, StringBuilder sb){
        sb.append(ch1).append(ch2);
    }
}
