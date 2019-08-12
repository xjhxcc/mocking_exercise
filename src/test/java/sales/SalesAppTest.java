package sales;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

public class SalesAppTest {

	@Test
	public void testGenerateReport() {

        SalesApp salesApp = new SalesApp();
        salesApp.generateSalesActivityReport("DUMMY", 1000, false, false);
		
	}
    @Test
    public void should_run_1_times_given_right_salesId_when_call_getSales(){
        SalesApp spySalesApp = spy(new SalesApp());
        SalesDao salesDao = mock(SalesDao.class);
        Sales sales = mock(Sales.class);
        Date date = mock(Date.class);

        spySalesApp.getSales("DUMMY",salesDao,sales,date);

        verify(spySalesApp,times(1)).getSales("DUMMY",salesDao,sales,date);
    }
    @Test
    public void should_return_timeList_given_true_when_call_getHeaders(){
        SalesApp spySalesApp = spy(new SalesApp());
        List<String> headers= Arrays.asList("Sales ID", "Sales Name", "Activity", "Time");
        List<String> result = spySalesApp.getHeaders(true);
        Assert.assertEquals(headers,result);
    }
}
