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
import info.johtani.misc.cli.furigana.OutputFormat;

import java.io.Writer;
import java.util.List;

public abstract class KanaWriter {

    final Writer writer;

    public KanaWriter(Writer writer) {
        this.writer = writer;
    }

    public static KanaWriter createKanaWriter(Writer writer, OutputFormat format) {
        KanaWriter instance;
        switch (format) {
            case JSON -> {
                instance = new JsonKanaWriter(writer);
            }
            default -> {
                instance = new CsvKanaWriter(writer);
            }
        }
        return instance;
    }

    public abstract void setHeader(String[] header);

    public abstract void write(List<Output> outputs);

    public abstract void close();



}
