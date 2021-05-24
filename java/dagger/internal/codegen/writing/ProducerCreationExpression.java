/*
 * Copyright (C) 2015 The Dagger Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dagger.internal.codegen.writing;

import static com.google.common.base.Preconditions.checkNotNull;
import static dagger.internal.codegen.binding.SourceFiles.generatedClassNameForBinding;

import com.squareup.javapoet.CodeBlock;
import dagger.assisted.Assisted;
import dagger.assisted.AssistedFactory;
import dagger.assisted.AssistedInject;
import dagger.internal.codegen.binding.ContributionBinding;
import dagger.internal.codegen.writing.FrameworkFieldInitializer.FrameworkInstanceCreationExpression;

/**
 * A {@link dagger.producers.Producer} creation expression for a {@link
 * dagger.producers.Produces @Produces}-annotated module method.
 */
// TODO(dpb): Resolve with InjectionOrProvisionProviderCreationExpression.
final class ProducerCreationExpression implements FrameworkInstanceCreationExpression {

  private final ComponentBindingExpressions componentBindingExpressions;
  private final ContributionBinding binding;

  @AssistedInject
  ProducerCreationExpression(
      @Assisted ContributionBinding binding,
      ComponentBindingExpressions componentBindingExpressions) {
    this.binding = checkNotNull(binding);
    this.componentBindingExpressions = checkNotNull(componentBindingExpressions);
  }

  @Override
  public CodeBlock creationExpression() {
    return CodeBlock.of(
        "$T.create($L)",
        generatedClassNameForBinding(binding),
        componentBindingExpressions.getCreateMethodArgumentsCodeBlock(binding));
  }

  @AssistedFactory
  static interface Factory {
    ProducerCreationExpression create(ContributionBinding binding);
  }
}
