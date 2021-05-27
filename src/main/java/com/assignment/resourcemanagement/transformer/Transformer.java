package com.assignment.resourcemanagement.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface Transformer<R, P> {

  R toDomain(P p);

  P toModel(R r);

  default List<R> toEntities(List<P> p) {
    return p != null
        ? p.stream().map(this::toDomain).collect(Collectors.toList())
        : new ArrayList<>();
  }

  default List<? extends P> toModels(List<R> r) {
    return r != null
        ? r.stream().map(this::toModel).collect(Collectors.toList())
        : new ArrayList<>();
  }
}
