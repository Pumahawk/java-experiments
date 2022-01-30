package com.pumahawk;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionalUtil {
    
    @Transactional
    public void transactional(Runnable runnable) {
        runnable.run();
    }
}
