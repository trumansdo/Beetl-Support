/*

 * Copyright 2019 trumansdo

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



// This is a generated file. Not intended for manual editing.
package com.intellij.ibeetl.generated.psi.impl;

import com.intellij.ibeetl.generated.psi.BPSemicolon;
import com.intellij.ibeetl.generated.psi.BPVisitor;
import com.intellij.ibeetl.lang.base.BeetlCompositePsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;

public abstract class BPSemicolonImpl extends BeetlCompositePsiElement implements BPSemicolon {

  public BPSemicolonImpl(@NotNull ASTNode node) {
    super(node);
  }

  public <R> R accept(@NotNull BPVisitor<R> visitor) {
    return visitor.visitSemicolon(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof BPVisitor) accept((BPVisitor)visitor);
    else super.accept(visitor);
  }

}
