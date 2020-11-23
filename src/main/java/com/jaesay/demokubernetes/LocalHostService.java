package com.jaesay.demokubernetes;

import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
public class LocalHostService {

    public String getLocalHostInfo() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            return String.format("host-address: %s, host-name: %s",
                    localHost.getHostAddress(),
                    localHost.getHostName());

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
