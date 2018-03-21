package org.qifu.esb.router;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.lang3.StringUtils;

/**
 * http://127.0.0.1:8080/core-web/camel/example?msg=TEST123
 */
public class ExampleBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		this.from("servlet:///example")
		.process(new Processor() {

			@Override
			public void process(Exchange exchange) throws Exception {
				String msg = StringUtils.defaultString(exchange.getIn().getHeader("msg", String.class));
				exchange.getOut().setBody( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<message>" + msg + "</message>" );
			}
			
		}).to("stream:out");
	}

}
