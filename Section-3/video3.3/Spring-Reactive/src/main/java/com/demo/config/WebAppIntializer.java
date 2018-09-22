package com.demo.config;

import org.springframework.web.server.adapter.AbstractReactiveWebInitializer;

/**
 * @author ankidaemon
 *
 */
public class WebAppIntializer extends AbstractReactiveWebInitializer{

	@Override
	protected Class<?>[] getConfigClasses() {
		// TODO Auto-generated method stub
		return new Class<?>[] { WebConfig.class,RootConfig.class };
	}	
}
