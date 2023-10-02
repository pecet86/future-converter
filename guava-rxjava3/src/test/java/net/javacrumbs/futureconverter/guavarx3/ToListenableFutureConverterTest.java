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
import net.javacrumbs.futureconverter.common.test.guava.GuavaConvertedFutureTestHelper;
import net.javacrumbs.futureconverter.common.test.rxjava3.AbstractSingleToFutureConverterTest;

import static net.javacrumbs.futureconverter.guavarx3.FutureConverter.toListenableFuture;

public class ToListenableFutureConverterTest extends AbstractSingleToFutureConverterTest<ListenableFuture<String>> {

    public ToListenableFutureConverterTest() {
        super(new GuavaConvertedFutureTestHelper());
    }

    @Override
    protected ListenableFuture<String> toFuture(Single<String> single) {
        return toListenableFuture(single);
    }

    @Override
    protected Single<String> toSingle(ListenableFuture<String> future) {
        return FutureConverter.toSingle(future);
    }
}