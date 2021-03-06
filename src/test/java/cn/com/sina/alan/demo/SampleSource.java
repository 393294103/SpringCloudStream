/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.com.sina.alan.demo;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

/**
 * @author Ilayaperumal Gopinathan
 */
@EnableBinding(SampleSource.Source.class)
public class SampleSource {

	@Bean
	@InboundChannelAdapter(value = Source.SAMPLE, poller = @Poller(fixedDelay = "1000", maxMessagesPerPoll = "1"))
	public MessageSource<String> timerMessageSource() {
		return new MessageSource<String>() {
			public Message<String> receive() {
				System.out.println("******************");
				System.out.println("At the Source");
				System.out.println("******************");
				Converters.Foo foo = new Converters.Foo();
				foo.setValue("hi");
				System.out.println("Sending value: " + foo.getValue() + " of type " + foo.getClass());
				return new GenericMessage(foo);
			}
		};
	}

	public interface Source {
		String SAMPLE = "output";

		@Output("output")
		MessageChannel sampleSource();
	}
}
