package net.sinam.nbiot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.ip.udp.UnicastReceivingChannelAdapter;

public class BeanConfig {

    @Bean
    public IntegrationFlow processUniCastUdpMessage() {
        return IntegrationFlows
                .from(new UnicastReceivingChannelAdapter(15012))
                .handle("UDPServer", "handleMessage")
                .get();
    }

}
