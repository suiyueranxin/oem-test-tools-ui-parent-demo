package runtime;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;
import org.testng.internal.annotations.DisabledRetryAnalyzer;


public class RetryListener implements IAnnotationTransformer {
	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		if (annotation.getRetryAnalyzerClass() == DisabledRetryAnalyzer.class) {
			annotation.setRetryAnalyzer(RetryImpl.class);
		}
	}
}
