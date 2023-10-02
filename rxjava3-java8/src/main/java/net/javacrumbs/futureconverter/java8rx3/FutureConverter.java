/*
 * Copyright © 2014-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.javacrumbs.futureconverter.java8rx3;


import io.reactivex.rxjava3.core.Single;

import java.util.concurrent.CompletableFuture;

import static net.javacrumbs.futureconverter.java8common.Java8FutureUtils.createCompletableFuture;
import static net.javacrumbs.futureconverter.java8common.Java8FutureUtils.createValueSource;
import static net.javacrumbs.futureconverter.rxjava3common.RxJava3FutureUtils.createSingle;
import static net.javacrumbs.futureconverter.rxjava3common.RxJava3FutureUtils.createValueSource;

/**
 * Converts between Java 8 {@link java.util.concurrent.CompletableFuture} and RxJava {@link io.reactivex.rxjava3.core.Single}
 */
public class FutureConverter {

    /**
     * Converts {@link io.reactivex.rxjava3.core.Single} to {@link java.util.concurrent.CompletableFuture}.
     */
    public static <T> CompletableFuture<T> toCompletableFuture(Single<T> single) {
        return createCompletableFuture(createValueSource(single));
    }

    /**
     * Converts {@link java.util.concurrent.CompletableFuture} to {@link io.reactivex.rxjava3.core.Single}.
     * The original future is canceled upon unsubscribe.
     */
    public static <T> Single<T> toSingle(CompletableFuture<T> completableFuture) {
        return createSingle(createValueSource(completableFuture));
    }
}

