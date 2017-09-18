package com.nuoxin.virtual.rep.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by fenggang on 9/15/17.
 */
@Service
public class CenterDataService {

    @Value("${data.center.prefix.url}")
    private String prefixUrl;

    @Autowired
    private RestTemplate restTemplate;


}
