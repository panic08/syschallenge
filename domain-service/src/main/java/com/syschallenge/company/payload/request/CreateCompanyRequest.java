/*
 * Copyright by the original author or authors.
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

package com.syschallenge.company.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Represents a request payload for creating a company
 *
 * @author panic08
 * @since 1.0.0
 */
public record CreateCompanyRequest(
        @NotBlank(message = "Company slug is required")
                @Size(max = 60, message = "Company slug must be less than 60 characters")
                String slug,
        @NotBlank(message = "Company name is required")
                @Size(max = 60, message = "Company name must be less than 60 characters")
                String name) {}
