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

package info.johtani.misc.cli.furigana.analyzer;

import com.worksap.nlp.sudachi.Dictionary;
import com.worksap.nlp.sudachi.DictionaryFactory;
import com.worksap.nlp.sudachi.Morpheme;
import com.worksap.nlp.sudachi.Tokenizer;
import info.johtani.misc.cli.furigana.Output;

import java.io.IOException;
import java.util.List;

public class SudachiAanalyzer extends AnalyzeWord{

    private final Tokenizer tokenizer;

    public SudachiAanalyzer() throws IOException{
        super("sudachi");
        // TODO 辞書が必要
        // TODO 辞書内包できないかな？
        // String path = "";

        Dictionary dictionary = new DictionaryFactory().create();
        tokenizer = dictionary.create();
    }

    @Override
    public Output analyzeWord(String word) {
        StringBuilder kana = new StringBuilder();
        List<Morpheme> tokens = tokenizer.tokenize(word);
        for (Morpheme token : tokens) {
            kana.append(token.readingForm()).append(" ");
        }

        return createOutput(word, kana.toString().trim());
    }
}
