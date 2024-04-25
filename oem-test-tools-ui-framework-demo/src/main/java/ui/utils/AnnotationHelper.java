package ui.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import runtime.ByPath;
import runtime.InIFrame;
import ui.business.BusinessObject;
import ui.component.Comp;


public class AnnotationHelper {
	
	public static <T extends Annotation> List<Field> getAllAnnotatedFields(Object obj, Class<T> annotationClass) {
		List<Field> fieldList = new ArrayList<>();
		Class clazz = obj.getClass();
		while (clazz != null) {
			Field[] fields = clazz.getDeclaredFields();
			for(Field field: fields) {
				if (null != field.getAnnotation(annotationClass)) {
					fieldList.add(field);
				}
			}
			clazz = clazz.getSuperclass();
		}
		return fieldList;
	}
	
	public static void generateInIFrameAnnotated(BusinessObject bo) {
		// get iframe path from InFrame annotation
		String iframePath = AnnotationHelper.getIFramePath(bo);
		
		// set iframe path at parent BO's compfactory
		bo.getCompFactory().setIframe(iframePath);
		
		// set iframe path at sub BO's comfactory
		List<Field> fieldList = AnnotationHelper.getAllAnnotatedFields(bo, InIFrame.class);
		try {
			for (Field field: fieldList) {
				Class<?> boClass = field.getType();
				Constructor<?> boGen = boClass.getConstructor();
				BusinessObject subBO = (BusinessObject) boGen.newInstance();
				subBO.getCompFactory().setIframe(iframePath);
				field.setAccessible(true);
				field.set(bo, subBO);
			}
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException | IllegalAccessException | InstantiationException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	
	
	public static String getIFramePath(BusinessObject annotatedBo) {
		String result = "";
		Class<?> clazz = annotatedBo.getClass();
		while (clazz != null) {
			if (null != clazz.getAnnotation(InIFrame.class)) {
				InIFrame iframe = (InIFrame)clazz.getAnnotation(InIFrame.class);
				if (!iframe.path().isEmpty()) {
					result = iframe.path();
					break;
				}
			}
			clazz = clazz.getSuperclass();
		}
		return result;
	}
	
	public static void generatedByPathAnnotated(Comp comp) {
		// get all sub comp annotated by "ByPath"
		List<Field> fieldList = AnnotationHelper.getAllAnnotatedFields(comp, ByPath.class);
		
		// generate all annotated ByPath sub component
		for (Field field : fieldList) {
			ByPath dom = (ByPath)field.getAnnotation(ByPath.class);	
			String fullPath = comp.subDomPath(dom.path(), dom.isRelative());
			Class<?> compClass = field.getType();
			try {
				Constructor<?> compGen = compClass.getConstructor(String.class);
				Comp subComp = (Comp) compGen.newInstance(fullPath);
				field.setAccessible(true);
				field.set(comp, subComp);
			} catch (NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException | IllegalAccessException | InstantiationException
					| InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
