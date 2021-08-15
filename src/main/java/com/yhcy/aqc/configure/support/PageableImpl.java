package com.yhcy.aqc.configure.support;

import static com.google.common.base.Preconditions.checkArgument;

public class PageableImpl implements Pageable {

    private final int pageableOffset;
    private final int pageableLimit;

    public PageableImpl(int pageableOffset, int pageableLimit) {
        checkArgument(pageableOffset >= 0, "Offset must be greater or equals to zero.");
        checkArgument(pageableLimit >= 1, "Limit must be greater than zero.");

        this.pageableOffset = pageableOffset;
        this.pageableLimit = pageableLimit;
    }

    @Override
    public int offset() {
        return pageableOffset;
    }

    @Override
    public int limit() {
        return pageableLimit;
    }
}