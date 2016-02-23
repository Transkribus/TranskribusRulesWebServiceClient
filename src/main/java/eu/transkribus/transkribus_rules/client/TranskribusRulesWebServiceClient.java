package eu.transkribus.transkribus_rules.client;
import java.rmi.RemoteException;

import eu.transkribus.client.AuthenticationExceptionException;
import eu.transkribus.client.ConfigExceptionException;
import eu.transkribus.client.IOExceptionException;
import eu.transkribus.client.JessRuleExceptionException;
import eu.transkribus.client.ParserConfigurationExceptionException;
import eu.transkribus.client.SAXExceptionException;
import eu.transkribus.client.TranskribusRulesWebServiceCallbackHandler;
import eu.transkribus.client.TranskribusRulesWebServiceStub;
import eu.transkribus.client.TranskribusRulesWebServiceStub.AnalyzePageStructure;
import eu.transkribus.client.TranskribusRulesWebServiceStub.AnalyzePageStructureResponse;

public class TranskribusRulesWebServiceClient {
	
	public static String analyzePageStructure(String url, int pageNr, boolean detectPageNumbers, boolean detectRunningTitles, boolean detectFootnotes) throws RemoteException, IOExceptionException, ConfigExceptionException, AuthenticationExceptionException, SAXExceptionException, JessRuleExceptionException, ParserConfigurationExceptionException {
		TranskribusRulesWebServiceStub stub = new TranskribusRulesWebServiceStub();
		
		AnalyzePageStructure ps = new AnalyzePageStructure();
		ps.setCurrentImageNumber(pageNr);
		ps.setDetectPageNumbers(detectPageNumbers);
		ps.setDetectRunningTitles(detectRunningTitles);
		ps.setDetectFootnotes(detectFootnotes);
		ps.setPageFileUrl(url);
		
		AnalyzePageStructureResponse resp = stub.analyzePageStructure(ps);
		
		return resp.get_return();
	}
	
	public static void analyzePageStructureAsync(String url, int pageNr, boolean detectPageNumbers, boolean detectRunningTitles, boolean detectFootnotes,
			TranskribusRulesWebServiceCallbackHandler handler) throws RemoteException {
		TranskribusRulesWebServiceStub stub = new TranskribusRulesWebServiceStub();
		
		AnalyzePageStructure ps = new AnalyzePageStructure();
		ps.setCurrentImageNumber(pageNr);
		ps.setDetectPageNumbers(detectPageNumbers);
		ps.setDetectRunningTitles(detectRunningTitles);
		ps.setDetectFootnotes(detectFootnotes);
		ps.setPageFileUrl(url);
				
		stub.startanalyzePageStructure(ps, handler);
	}
	
	public static void main(String[] args) throws RemoteException, IOExceptionException, ConfigExceptionException, AuthenticationExceptionException, SAXExceptionException, JessRuleExceptionException, ParserConfigurationExceptionException, InterruptedException {
//		String url = "https://dbis-thure.uibk.ac.at/f/Get?id=OKNZPPENHCJPWDOLPINTAOKZ"; // null result --> no words in transcription
		String url = "https://dbis-thure.uibk.ac.at/f/Get?id=MZNZATGEXGMQOREUMUACEIVI"; // empty string exception -> frisch, reason unknown
//		
		
		// TEST CALL SYNC VERSION:		
//		String xmlStr = analyzePageStructure(url, 1, true, true, true);
//		System.out.println(xmlStr);
		
		// TEST CALL ASYNC VERSION:
		analyzePageStructureAsync(url, 1, true, true, true, new TranskribusRulesWebServiceCallbackHandler() {
			@Override public void receiveResultanalyzePageStructure(
					eu.transkribus.client.TranskribusRulesWebServiceStub.AnalyzePageStructureResponse result) {
				System.out.println("RESULT:");
				System.out.println(result.get_return());
			}

			@Override public void receiveErroranalyzePageStructure(java.lang.Exception e) {
				e.printStackTrace();
			}
		});
		Thread.currentThread().sleep(2000); // wait for 2 seconds to recieve async response before program exits
		
//		System.out.println("DONE!");
	}
	

}
