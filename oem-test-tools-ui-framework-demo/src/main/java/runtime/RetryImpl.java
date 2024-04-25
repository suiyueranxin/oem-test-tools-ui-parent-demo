package runtime;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import log.Log;

public class RetryImpl implements IRetryAnalyzer {
	
	private int retryCount = 1;
	private static final int maxRetryCount = 1;
	
	@Override
	public boolean retry(ITestResult iTestResult) {
		if (retryCount <= maxRetryCount) {
			Log.logDebug(String.format("It's the %d times retry. And the current case is %s", retryCount, iTestResult.getName()));
			
			retryCount++;
			return true;
		}
		return false;
	}
	
	public void resetCount() {
		retryCount = 1;
	}

}
