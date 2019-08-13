package sales;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SalesApp {

	SalesReportDao salesReportDao;
	SalesDao salesDao;
	EcmService ecmService;
	Date date;

	public SalesApp() {
		this.salesReportDao = new SalesReportDao();
		this.salesDao = new SalesDao();
		this.ecmService = new EcmService();
		this.date = new Date();
	}

	public void generateSalesActivityReport(String salesId, int maxRow, boolean isNatTrade, boolean isSupervisor) {

		if (salesId == null) return;
		Sales sales = getSales(salesId);

		if (sales == null) return;

		List<SalesReportData> reportDataList = new ArrayList<>();
		List<SalesReportData> filteredReportDataList = new ArrayList<>();

		getSalesReportData(isSupervisor, sales,reportDataList,filteredReportDataList);

		replaceFilteredReportDataList(maxRow, reportDataList,filteredReportDataList);

		List<String> headers = getHeaders(isNatTrade);

		SalesActivityReport report = this.generateReport(headers, reportDataList);
		uploadEcmServiceDocument(report);
	}

	protected boolean uploadEcmServiceDocument(SalesActivityReport report) {
		EcmService ecmService = new EcmService();
		if (report != null) {
			ecmService.uploadDocument(report.toXml());
			return true;
		}
		else
			return false;
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

	protected List<SalesReportData> replaceFilteredReportDataList(int maxRow, List<SalesReportData> reportDataList,List<SalesReportData> filteredReportDataList) {
		List<SalesReportData> tempList = new ArrayList<SalesReportData>();
		for (int i=0; i < reportDataList.size() || i < maxRow; i++) {
			tempList.add(reportDataList.get(i));
		}
		filteredReportDataList = tempList;
		return filteredReportDataList;
	}

	protected List<SalesReportData> getSalesReportData(boolean isSupervisor, Sales sales,List<SalesReportData> reportDataList,List<SalesReportData> filteredReportDataList) {
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

	protected Sales getSales(String salesId) {
		Sales sales = salesDao.getSalesBySalesId(salesId);
		if (date.after(sales.getEffectiveTo())
				|| date.before(sales.getEffectiveFrom())){
			return null;
		}
		return sales;
	}

	protected SalesActivityReport generateReport(List<String> headers, List<SalesReportData> reportDataList) {
		// TODO Auto-generated method stub
		return null;
	}

}