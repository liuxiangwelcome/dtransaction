package com.yqkj.components.dtransaction;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DTransactionFactory implements ApplicationContextAware {

    private static Map<String, IDTransation> services = new HashMap<>();

    private static final String DTSERVICE_NAME_PRIFX = "DTX";

    public IDTransation getDTransaction(String dtName){
        return services.get(DTSERVICE_NAME_PRIFX + dtName);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String,IDTransation> map = applicationContext.getBeansOfType(IDTransation.class);
        map.forEach((key,value) -> services.put(DTSERVICE_NAME_PRIFX + value.getDTServiceName(),value));
    }
}
