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
    @Mock
    SalesReportData salesReportData;

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
    @Test
    public void should_return_not_null_given_sales_when_call_getSalesReportData() {
        List<SalesReportData> reportDataList = new ArrayList<>();
        List<SalesReportData> filteredDataList = new ArrayList<>();
        reportDataList.add(salesReportData);
        Sales sales = new Sales();
        List<SalesReportData> result = mockSalesApp.getSalesReportData(false,sales,reportDataList,filteredDataList);
        Assert.assertNotNull(result);
    }
    @Test
    public void should_run_1_times_given_salesActivityReport_when_call_uploadEcmServiceDocument(){
        SalesActivityReport salesActivityReport=mock(SalesActivityReport.class);
        boolean result = mockSalesApp.uploadEcmServiceDocument(salesActivityReport);
        Assert.assertEquals(true,result);
    }
    @Test
    public void should_return_not_null_given_maxRow_and_reportDataList_when_call_replaceFilteredReportDataList() {
        List<SalesReportData> reportDataList = new ArrayList<>();
        List<SalesReportData> filteredDataList = new ArrayList<>();
        SalesReportData salesReportData = mock(SalesReportData.class);
        reportDataList.add(salesReportData);
        List<SalesReportData> result = mockSalesApp.replaceFilteredReportDataList(1,reportDataList,filteredDataList);
        Assert.assertNotNull(result);
    }
}
