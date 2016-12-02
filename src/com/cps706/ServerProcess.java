package com.cps706;

import java.io.IOException;

/**
 * Created by Baheer.
 */
public interface ServerProcess<T> {

    public void process(T pros) throws IOException;
}
