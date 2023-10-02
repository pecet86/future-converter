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
package net.javacrumbs.futureconverter.java8apifuture;

import com.google.api.core.ApiFuture;
import net.javacrumbs.futureconverter.common.test.AbstractConverterHelperBasedTest;
import net.javacrumbs.futureconverter.common.test.apicommon.ApiCommonConvertedFutureTestHelper;
import net.javacrumbs.futureconverter.common.test.java8.Java8OriginalFutureTestHelper;
import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static net.javacrumbs.futureconverter.java8apifuture.FutureConverter.toApiFuture;
import static net.javacrumbs.futureconverter.java8apifuture.FutureConverter.toCompletableFuture;



public class ToApiFutureConverterTest extends AbstractConverterHelperBasedTest<
        CompletableFuture<String>,
        ApiFuture<String>> {


    public ToApiFutureConverterTest() {
        super(new Java8OriginalFutureTestHelper(), new ApiCommonConvertedFutureTestHelper());
    }

    @Override
    protected ApiFuture<String> convert(CompletableFuture<String> originalFuture) {
        return toApiFuture(originalFuture);
    }

    @Override
    protected CompletableFuture<String> convertBack(ApiFuture<String> converted) {
        return toCompletableFuture(converted);
    }

    @Test
    @Ignore
    public void testCancelBeforeConversion() throws ExecutionException, InterruptedException {
        // completable futures can not be canceled
    }

}
