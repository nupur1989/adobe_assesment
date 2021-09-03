import adobeAssessment.dao.NumberDao;
import adobeAssessment.entity.NumberEntity;
import adobeAssessment.service.NumberService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class NumberServiceTest {

    @Mock
    NumberDao numberDao;

    @InjectMocks
    NumberService numberService;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetConversion() throws Exception{

        //invoke
        numberService.getRoman("1");

        ArgumentCaptor<String> inputCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<NumberEntity> entityCaptor = ArgumentCaptor.forClass(NumberEntity.class);

        //verify
        verify(numberDao, times(1)).getNumber(inputCaptor.capture());

        assert (inputCaptor.getValue().equalsIgnoreCase("1"));
        verify(numberDao, times(1)).putNumber(inputCaptor.capture(), entityCaptor.capture());

        assert (entityCaptor.getValue().getInput().equals("1"));
        assert (entityCaptor.getValue().getOutput().equals("I"));
    }

    @Test(expected = Exception.class)
    public void testGetConversionBadInput() throws Exception {
        when(numberService.getRoman(""))
                .thenThrow(Exception.class);

        numberService.getRoman("");
    }
}
