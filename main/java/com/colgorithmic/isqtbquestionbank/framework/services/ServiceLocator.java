package com.colgorithmic.isqtbquestionbank.framework.services;

import com.colgorithmic.isqtbquestionbank.helper.ApplicationConstants;
import com.colgorithmic.isqtbquestionbank.helper.FrameWorkHelper;
import com.colgorithmic.isqtbquestionbank.ui.FrameworkActivity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by anandsoni on 2017-10-10.
 */
public class ServiceLocator {

    Map<String, Object> serviceProviderInterfaceMap;

    private static ServiceLocator serviceLocator;
    private FrameworkActivity frameworkActivity;

    private ServiceLocator(FrameworkActivity frameworkActivity) {
        serviceProviderInterfaceMap = new HashMap<String, Object>();
        this.frameworkActivity = frameworkActivity;
        initalizeAndInstallServices();
    }

    private void initalizeAndInstallServices() {

        String str=frameworkActivity.getApplicationContextData().getValue(ApplicationConstants.SERVICES);
        String[] classes=str.split("::");
        for(String className:classes){
            initializeClass(className);
        }

    }

    private void initializeClass(String className) {
        try {
            Class serviceClass=  Class.forName(className);
            Class[] type ={FrameworkActivity.class};
            Constructor constructor = serviceClass.getConstructor(type);
            Object[] parameters = {frameworkActivity};

                Object serviceInstance=constructor.newInstance(parameters);
            String nameOfClass=FrameWorkHelper.getClassName(className, 5);
            serviceProviderInterfaceMap.put(nameOfClass,serviceInstance);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
        e.printStackTrace();
    }
    }

    public static ServiceLocator getServiceLocator(FrameworkActivity frameworkActivity) {

        if (serviceLocator == null) {
            synchronized (ServiceLocator.class) {
                if (serviceLocator == null) {
                    serviceLocator = new ServiceLocator(frameworkActivity);
                }
            }

        }
        return serviceLocator;
    }

    public Object resolve(String serviceName){
        return serviceProviderInterfaceMap.get((String)serviceName);
    }
}
