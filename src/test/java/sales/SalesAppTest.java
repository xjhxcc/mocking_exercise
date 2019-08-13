package sales;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
@RunWith(MockitoJUnitRunner.class)
public class SalesAppTest {
    @Mock
    SalesReportDao salesReportDao;
    @Mock
    SalesDao salesDao;
    @Mock
    EcmService ecmService;
    @Mock
    Date date;

    @InjectMocks
    SalesApp mockSalesApp;
//	@Test
////	public void testGenerateReport() {
////
////        SalesApp salesApp = new SalesApp();
////        salesApp.generateSalesActivityReport("DUMMY", 1000, false, false);
////
////	}
    @Test
    public void should_return_not_null_given_right_salesId_when_call_getSales() {
        Sales sales = new Sales();
        Mockito.when(salesDao.getSalesBySalesId(anyString())).thenReturn(sales);
        Sales result = mockSalesApp.getSales("DUMMY");
        Assert.assertNotNull(result);
    }
    @Test
    public void should_return_timeList_given_true_when_call_getHeaders(){
        SalesApp spySalesApp = spy(new SalesApp());
        List<String> headers= Arrays.asList("Sales ID", "Sales Name", "Activity", "Time");
        List<String> result = spySalesApp.getHeaders(true);
        Assert.assertEquals(headers,result);

    }
}
