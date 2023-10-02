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
package net.javacrumbs.futureconverter.common.test.java8;

import net.javacrumbs.futureconverter.common.test.AbstractConverterTest;
import net.javacrumbs.futureconverter.common.test.OriginalFutureTestHelper;
import net.javacrumbs.futureconverter.common.test.common.CommonOriginalFutureTestHelper;

import java.util.concurrent.CompletableFuture;

public class Java8OriginalFutureTestHelper extends CommonOriginalFutureTestHelper implements OriginalFutureTestHelper<CompletableFuture<String>> {

    @Override
    public CompletableFuture<String> createFinishedFuture() {
        return CompletableFuture.completedFuture(AbstractConverterTest.VALUE);
    }

    @Override
    public CompletableFuture<String> createRunningFuture() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                waitForSignal();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return AbstractConverterTest.VALUE;
        });
    }

    @Override
    public CompletableFuture<String> createExceptionalFuture(Exception exception) {
        CompletableFuture<String> completable = new CompletableFuture<>();
        completable.completeExceptionally(exception);
        return completable;
    }

}
