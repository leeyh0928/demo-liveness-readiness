package com.example.demo;

import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
public class LocalhostService {
    public String getInfo() {
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            return String.format("host address: %s host name: %s",
                    localhost.getHostAddress(),
                    localhost.getHostName());
        } catch (UnknownHostException e) {
            throw new IllegalStateException(e);
        }
    }
}
