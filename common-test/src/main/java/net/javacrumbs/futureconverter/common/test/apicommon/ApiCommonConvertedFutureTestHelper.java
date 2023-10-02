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
package net.javacrumbs.futureconverter.common.test.apicommon;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.common.util.concurrent.MoreExecutors;
import net.javacrumbs.futureconverter.common.test.AbstractConverterTest;
import net.javacrumbs.futureconverter.common.test.ConvertedFutureTestHelper;
import net.javacrumbs.futureconverter.common.test.common.CommonConvertedFutureTestHelper;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ApiCommonConvertedFutureTestHelper extends CommonConvertedFutureTestHelper implements ConvertedFutureTestHelper<ApiFuture<String>> {
    private final ApiFutureCallback<String> callback = mock(ApiFutureCallback.class);

    @Override
    public void waitForCalculationToFinish(ApiFuture<String> convertedFuture) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);

        ApiFutures.addCallback(convertedFuture, new ApiFutureCallback<String>() {
            @Override
            public void onSuccess(String result) {
                latch.countDown();
            }

            @Override
            public void onFailure(Throwable t) {
                latch.countDown();
            }
        }, MoreExecutors.directExecutor());

        latch.await(1, TimeUnit.SECONDS);
    }

    @Override
    public void verifyCallbackCalledWithException(Exception exception) {
        waitForCallback();
        verify(callback).onFailure(exception);
    }

    @Override
    public void verifyCallbackCalledWithException(Class<? extends Exception> exceptionClass) {
        waitForCallback();
        verify(callback).onFailure(any(exceptionClass));
    }

    @Override
    public void verifyCallbackCalledWithCorrectValue() {
        waitForCallback();
        verify(callback).onSuccess(AbstractConverterTest.VALUE);
    }

    @Override
    public void addCallbackTo(ApiFuture<String> convertedFuture) {
        ApiFutures.addCallback(convertedFuture, callback);
        convertedFuture.addListener(this::callbackCalled, MoreExecutors.directExecutor());
    }
}
