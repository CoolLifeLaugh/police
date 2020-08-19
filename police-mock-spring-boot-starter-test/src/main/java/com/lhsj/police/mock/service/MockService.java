package com.lhsj.police.mock.service;

import com.lhsj.police.mock.annotation.Mock;
import org.springframework.stereotype.Service;

@Service
public class MockService {

    @Mock(key = "'simple'", path = "'/tmp/mock'")
    public boolean simple() {
        return true;
    }

    @Mock(key = "'remote'", remote = true, url = "http://127.0.0.1:8888/api/city/mock")
    public boolean remote() {
        return true;
    }
}
