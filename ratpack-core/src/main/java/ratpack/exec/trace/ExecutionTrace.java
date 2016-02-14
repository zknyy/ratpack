/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ratpack.exec.trace;

import java.util.ArrayList;
import java.util.List;

public abstract class ExecutionTrace {

  private ExecutionTrace() {
  }

  public static StackTraceElement[] trace(int windbackDepth) {
    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
    List<StackTraceElement> list = new ArrayList<>(stackTrace.length);
    for (int i = 0; i < stackTrace.length; ++i) {
      if (i > windbackDepth) {
        StackTraceElement element = stackTrace[i];
        if (element.getClassName().equals("ratpack.exec.internal.DefaultExecController$1") && element.getMethodName().equals("lambda$start$0")) {
          break;
        }
        if (!element.getClassName().startsWith("ratpack.exec.internal.DefaultExecution")) {
          list.add(element);
        }
      }
    }

    return list.toArray(new StackTraceElement[list.size()]);
  }
}
