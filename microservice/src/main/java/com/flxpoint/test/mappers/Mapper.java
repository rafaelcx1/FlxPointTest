package com.flxpoint.test.mappers;

import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Mapper<F, D> {

    public abstract D map(F from);
    public abstract F revert(D destination);


    public List<D> mapAll(Collection<F> from) {
        return from.stream().map(this::map).collect(Collectors.toList());
    }

    public Page<D> mapAll(Page<F> from) {
        return from.map(this::map);
    }

    public List<F> revertAll(Collection<D> destination) {
        return destination.stream().map(this::revert).collect(Collectors.toList());
    }

    public Page<F> revertAll(Page<D> destination) {
        return destination.map(this::revert);
    }
}
