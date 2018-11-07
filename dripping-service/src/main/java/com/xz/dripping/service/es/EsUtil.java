package com.xz.dripping.service.es;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;

import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;


import java.net.InetAddress;
import java.net.UnknownHostException;

public class EsUtil {

    public static void main(String[] args) {

        try {
            Settings settings = Settings.builder()
                    .put("cluster.name","es").build();
            TransportClient client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName("172.16.36.120"),9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }
}
