package com.lhsj.police.chain.filter;

import com.lhsj.police.chain.Request;
import com.lhsj.police.chain.Response;

public class DefaultFilterChain<Req extends Request, Resp extends Response> implements FilterChain<Req, Resp> {

    public static final int INCREMENT = 10;

    @SuppressWarnings("unchecked")
    private Filter<Req, Resp>[] filters = new Filter[0];

    /**
     * The int which is used to maintain the current position in the filter chain.
     */
    private int pos = 0;
    /**
     * The int which gives the current number of filters in the chain.
     */
    private int n   = 0;

    @Override
    public void doFilter(Req request, Resp response) {
        // Call the next filter if there is one
        if (pos < n) {
            Filter<Req, Resp> filter = filters[pos++];
            filter.doFilter(request, response, this);
        }
    }

    @Override
    public FilterChain<Req, Resp> addFilter(Filter<Req, Resp> addFilter) {
        // Prevent the same filter being added multiple times
        for (Filter<Req, Resp> filter : filters) {
            if (filter == addFilter) {
                return this;
            }
        }

        if (n == filters.length) {
            @SuppressWarnings("unchecked")
            Filter<Req, Resp>[] newFilters = new Filter[n + INCREMENT];
            System.arraycopy(filters, 0, newFilters, 0, n);
            filters = newFilters;
        }
        filters[n++] = addFilter;
        return this;
    }

    @Override
    public void reuse() {
        pos = 0;
    }

    @Override
    public void release() {
        for (int i = 0; i < n; i++) {
            filters[i] = null;
        }
        n = 0;
        pos = 0;
    }
}
