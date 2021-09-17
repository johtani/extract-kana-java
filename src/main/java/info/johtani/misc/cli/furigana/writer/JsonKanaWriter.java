/*
 *
 * Copyright (c) 2021  johtani
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package info.johtani.misc.cli.furigana.writer;

import info.johtani.misc.cli.furigana.Output;

import java.io.Writer;
import java.util.List;

// FIXME not implemented yet
public class JsonKanaWriter extends KanaWriter{

    public JsonKanaWriter(Writer writer) {
        super(writer);
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public void setHeader(String[] header) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public void write(List<Output> outputs) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public void close() {
        throw new RuntimeException("Not implemented yet");
    }
}
