/*
 * Copyright (c) 2013 Original Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sharneng.lookup;

/**
 * Exception to indicate an error in building a lookup
 * <p>
 * This is made to be {@link RuntimeException} to simplify client's exception handling.
 * 
 * @author Kenneth Xu
 * 
 */
public class LookupBuildException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Construct a new {@code LookupBuildException} with given error message.
     * 
     * @param message
     *            the exception message
     */
    public LookupBuildException(final String message) {
        super(message);
    }

    /**
     * Construct a new {@code LookupBuildException} from another exception as a cause of error.
     * 
     * @param cause
     *            the cause of the error
     */
    public LookupBuildException(final Throwable cause) {
        super(cause);
    }

    /**
     * Construct a new {@code LookupBuildException} with given message and another exception as the cause of error.
     * 
     * @param message
     *            the exception message
     * @param cause
     *            the cause of the error
     */
    public LookupBuildException(String message, Throwable cause) {
        super(message, cause);
    }

}
