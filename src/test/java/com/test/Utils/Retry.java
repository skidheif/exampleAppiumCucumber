package com.test.Utils;

import com.test.Configuration.PropertyReader;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.util.HashMap;
import java.util.Map;

public class Retry implements IRetryAnalyzer {
    public int retryCountForTest = 0;
    PropertyReader maxCountReader = new PropertyReader();
    int maxRetryCount = Integer.parseInt(maxCountReader.readProperty("MAX_RETRY_COUNT"));
    private Map<String, Integer> retryCounts = new HashMap<String, Integer>();

    @Override
    public boolean retry ( ITestResult iTestResult ) {
        int counter = 0;
        String methodName = iTestResult.getMethod().getMethodName();
        Object[] obj = iTestResult.getParameters();

        while (obj.length != counter) {
            methodName = methodName + "_" + obj[counter];
            counter++;
        }

        if (retryCounts.containsKey(methodName)) {
            retryCountForTest = retryCounts.get(methodName);
            retryCountForTest++;
        }

        if (!iTestResult.isSuccess() && retryCountForTest < maxRetryCount) {
            System.out.println(methodName
                    + " execution failed in count: " + retryCountForTest + "\n");
            retryCounts.put(methodName, retryCountForTest);
            return true;
        } else if (!iTestResult.isSuccess() && retryCountForTest == maxRetryCount) {
            System.out.println(methodName
                    + " execution failed in count: " + maxRetryCount + "\n");
            return false;
        }
        return false;
    }
}
