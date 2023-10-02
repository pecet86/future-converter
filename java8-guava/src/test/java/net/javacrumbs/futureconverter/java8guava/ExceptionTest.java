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
package net.javacrumbs.futureconverter.java8guava;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.MoreExecutors;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.core.Is.is;

public class ExceptionTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testFinishedRuntimeExceptionStageToListenable() throws ExecutionException, InterruptedException {
        IllegalStateException inputException = new IllegalStateException("something went wrong");
        CompletableFuture<String> future = new CompletableFuture<>();
        future.completeExceptionally(inputException);

        expectedException.expect(ExecutionException.class);
        expectedException.expectCause(is(inputException));
        FutureConverter.toListenableFuture(future)
                .get();
    }

    @Test
    public void testFinishedCheckedExceptionStageToListenable() throws ExecutionException, InterruptedException {
        Exception inputException = new Exception("something went wrong");
        CompletableFuture<String> future = new CompletableFuture<>();
        future.completeExceptionally(inputException);

        expectedException.expect(ExecutionException.class);
        expectedException.expectCause(is(inputException));
        FutureConverter.toListenableFuture(future)
                .get();
    }

    @Test
    public void testApplyRuntimeExceptionStageToListenable() throws ExecutionException, InterruptedException {
        IllegalStateException inputException = new IllegalStateException("something went wrong");
        expectedException.expect(ExecutionException.class);
        expectedException.expectCause(is(inputException));

        FutureConverter.toListenableFuture(
                CompletableFuture.completedFuture("")
                        .thenApply(s -> {
                            throw inputException;
                        }))
                .get();
    }

    @Test
    public void testApplyCheckedExceptionStageToListenable() throws ExecutionException, InterruptedException {
        Exception inputException = new Exception("something went wrong");
        expectedException.expect(ExecutionException.class);
        expectedException.expectCause(is(inputException));

        FutureConverter.toListenableFuture(
                CompletableFuture.completedFuture("")
                        .thenCompose(s -> {
                            CompletableFuture<String> composed = new CompletableFuture<>();
                            composed.completeExceptionally(inputException);
                            return composed;
                        }))
                .get();
    }

    @Test
    public void testFinishedRuntimeExceptionListenableToStage() {
        IllegalStateException inputException = new IllegalStateException("something went wrong");
        expectedException.expect(CompletionException.class);
        expectedException.expectCause(is(inputException));

        FutureConverter.toCompletableFuture(
                Futures.immediateFailedFuture(inputException))
                .join();
    }

    @Test
    public void testFinishedCheckedExceptionListenableToStage() {
        Exception inputException = new Exception("something went wrong");
        expectedException.expect(CompletionException.class);
        expectedException.expectCause(is(inputException));

        FutureConverter.toCompletableFuture(
                Futures.immediateFailedFuture(inputException))
                .join();
    }

    @Test
    public void testApplyRuntimeExceptionListenableToStage() {
        IllegalStateException inputException = new IllegalStateException("something went wrong");
        expectedException.expect(CompletionException.class);
        expectedException.expectCause(is(inputException));

        FutureConverter.toCompletableFuture(
                Futures.transform(
                        Futures.immediateFuture(""),
                        s -> {
                            throw inputException;
                        },
                        MoreExecutors.directExecutor()))
                .join();
    }

    @Test
    public void testApplyCheckedExceptionListenableToStage() {
        Exception inputException = new Exception("something went wrong");
        expectedException.expect(CompletionException.class);
        expectedException.expectCause(is(inputException));

        FutureConverter.toCompletableFuture(
                Futures.transformAsync(
                        Futures.immediateFuture(""),
                        s -> Futures.immediateFailedFuture(inputException),
                        MoreExecutors.directExecutor()))
                .join();
    }
}
