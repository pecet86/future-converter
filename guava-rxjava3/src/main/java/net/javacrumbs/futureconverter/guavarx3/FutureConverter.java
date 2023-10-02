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
package net.javacrumbs.futureconverter.guavarx3;

import com.google.common.util.concurrent.ListenableFuture;
import io.reactivex.rxjava3.core.Single;

import static net.javacrumbs.futureconverter.guavacommon.GuavaFutureUtils.createListenableFuture;
import static net.javacrumbs.futureconverter.guavacommon.GuavaFutureUtils.createValueSource;
import static net.javacrumbs.futureconverter.rxjava3common.RxJava3FutureUtils.createSingle;
import static net.javacrumbs.futureconverter.rxjava3common.RxJava3FutureUtils.createValueSource;

public class FutureConverter {

    /**
     * Converts {@link com.google.common.util.concurrent.ListenableFuture} to  {@link io.reactivex.rxjava3.core.Single}.
     * The original future is canceled upon unsubscribe.
     */
    public static <T> Single<T> toSingle(ListenableFuture<T> listenableFuture) {
        return createSingle(createValueSource(listenableFuture));
    }

    /**
     * Converts  {@link io.reactivex.rxjava3.core.Single} to {@link com.google.common.util.concurrent.ListenableFuture}.
     */
    public static <T> ListenableFuture<T> toListenableFuture(Single<T> single) {
        return createListenableFuture(createValueSource(single));
    }
}
