package runtime;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;


public class TestRunnerListener extends TestListenerAdapter {
	@Override
	public void onTestFailure(ITestResult tr) {
		super.onTestFailure(tr);
		SeleniumWrapper.capturePage();
		this.handlerIfRerun(tr);
	}
	
	@Override
	public void onTestSuccess(ITestResult tr) {
		this.handlerIfRerun(tr);
	}
	
	private void handlerIfRerun(ITestResult tr) {
		if (tr.getMethod().getRetryAnalyzerClass() == RetryImpl.class) {
			RetryImpl retryAnalyzer = (RetryImpl) tr.getMethod().getRetryAnalyzer(tr);
			retryAnalyzer.resetCount();
		}
	}
}
