package com.gvstave.mistergift.data.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gvstave.mistergift.data.service.access.UserAccessService;
import com.gvstave.mistergift.data.service.cache.CacheService;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class UserAccessServiceTest {

    @Mock
    private CacheService cacheService;

    @InjectMocks
    private UserAccessService userAccessService;

    private String pattern = "access:ip:127.0.0.1";

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        List<String> keys = new ArrayList<>();
        keys.add(pattern);

        Mockito.when(cacheService.getKeys()).thenReturn(keys);
    }

    /**
     *
     */
    public void testRemoveOther() throws Exception {
        Mockito.when(cacheService.get(pattern)).thenReturn(1);

    }

}