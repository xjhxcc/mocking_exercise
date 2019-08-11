package sales;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SalesApp {

	public void generateSalesActivityReport(String salesId, int maxRow, boolean isNatTrade, boolean isSupervisor) {
		if (salesId == null) {
			return;
		}
		Sales sales = getSales(salesId);

		if (getEffective(sales)) return;
		List<SalesReportData> reportDataList = getSalesReportData(sales);
		List<SalesReportData> filteredReportDataList = getFilteredReportDataList(isSupervisor, reportDataList);


		List<SalesReportData> tempList = new ArrayList<SalesReportData>();
		for (int i=0; i < reportDataList.size() || i < maxRow; i++) {
			tempList.add(reportDataList.get(i));
		}
		filteredReportDataList = tempList;
		List<String> headers = getHeaders(isNatTrade);

		SalesActivityReport report = this.generateReport(headers, reportDataList);
		
		EcmService ecmService = new EcmService();
		ecmService.uploadDocument(report.toXml());
		
	}

	private List<String> getHeaders(boolean isNatTrade) {
		List<String> headers = null;
		if (isNatTrade) {
			headers = Arrays.asList("Sales ID", "Sales Name", "Activity", "Time");
		} else {
			headers = Arrays.asList("Sales ID", "Sales Name", "Activity", "Local Time");
		}
		return headers;
	}

	private List<SalesReportData> getSalesReportData(Sales sales) {
		SalesReportDao salesReportDao = new SalesReportDao();
		return salesReportDao.getReportData(sales);
	}

	private List<SalesReportData> getFilteredReportDataList(boolean isSupervisor, List<SalesReportData> reportDataList) {
		List<SalesReportData> filteredReportDataList = new ArrayList<SalesReportData>();
		for (SalesReportData data : reportDataList) {
			if ("SalesActivity".equalsIgnoreCase(data.getType())) {
				if (data.isConfidential()) {
					if (isSupervisor) {
						filteredReportDataList.add(data);
					}
				}else {
					filteredReportDataList.add(data);
				}
			}
		}
		return filteredReportDataList;
	}

	private boolean getEffective(Sales sales) {
		Date today = new Date();
		if (today.after(sales.getEffectiveTo())
				|| today.before(sales.getEffectiveFrom())){
			return true;
		}
		return false;
	}

	private Sales getSales(String salesId) {
		SalesDao salesDao = new SalesDao();
		return salesDao.getSalesBySalesId(salesId);
	}

	private SalesActivityReport generateReport(List<String> headers, List<SalesReportData> reportDataList) {
		// TODO Auto-generated method stub
		return null;
	}

}
