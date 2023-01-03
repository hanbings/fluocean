package io.hanbings.fluocean.common.interfaces;

import java.util.function.Consumer;

public interface Callback<D, W> {
    D data();

    W wrong();

    String token();

    Throwable throwable();

    Callback<D, W> succeed(Consumer<D> data);

    Callback<D, W> fail(Consumer<W> wrong);

    Callback<D, W> except(Consumer<Throwable> throwable);

    boolean success();
}