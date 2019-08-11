package sales;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SalesApp {

	public void generateSalesActivityReport(String salesId, int maxRow, boolean isNatTrade, boolean isSupervisor) {

		if (salesId == null) return;

		SalesDao salesDao = new SalesDao();
		Sales sales = salesDao.getSalesBySalesId(salesId);
		Date today = new Date();
		getSales(salesId,salesDao,sales,today);

		if (sales == null) return;

		SalesReportDao salesReportDao = new SalesReportDao();
		List<SalesReportData> reportDataList = getSalesReportData(isSupervisor, sales,salesReportDao);

		List<SalesReportData> filteredReportDataList;
		replaceFilteredReportDataList(maxRow, reportDataList);
		List<String> headers = getHeaders(isNatTrade);
		SalesActivityReport report = this.generateReport(headers, reportDataList);
		uploadEcmServiceDocument(report);
	}

	protected void uploadEcmServiceDocument(SalesActivityReport report) {
		EcmService ecmService = new EcmService();
		ecmService.uploadDocument(report.toXml());
	}

	protected List<String> getHeaders(boolean isNatTrade) {
		List<String> headers = null;
		if (isNatTrade) {
			headers = Arrays.asList("Sales ID", "Sales Name", "Activity", "Time");
		} else {
			headers = Arrays.asList("Sales ID", "Sales Name", "Activity", "Local Time");
		}
		return headers;
	}

	protected void replaceFilteredReportDataList(int maxRow, List<SalesReportData> reportDataList) {
		List<SalesReportData> filteredReportDataList;
		List<SalesReportData> tempList = new ArrayList<SalesReportData>();
		for (int i=0; i < reportDataList.size() || i < maxRow; i++) {
			tempList.add(reportDataList.get(i));
		}
		filteredReportDataList = tempList;
	}

	protected List<SalesReportData> getSalesReportData(boolean isSupervisor, Sales sales, SalesReportDao salesReportDao) {
//		SalesReportDao salesReportDao = new SalesReportDao();
		List<SalesReportData> reportDataList = salesReportDao.getReportData(sales);
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
		return reportDataList;
	}

	protected Sales getSales(String salesId,SalesDao salesDao,Sales sales,Date today) {
//		SalesDao salesDao = new SalesDao();
//		Sales sales = salesDao.getSalesBySalesId(salesId);

//		Date today = new Date();
		if (today.after(sales.getEffectiveTo())
				|| today.before(sales.getEffectiveFrom())){
			return null;
		}
		return sales;
	}

	protected SalesActivityReport generateReport(List<String> headers, List<SalesReportData> reportDataList) {
		// TODO Auto-generated method stub
		return null;
	}

}