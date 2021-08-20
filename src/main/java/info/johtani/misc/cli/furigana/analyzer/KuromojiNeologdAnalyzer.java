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

import info.johtani.misc.cli.furigana.Output;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.codelibs.neologd.ipadic.lucene.analysis.ja.JapaneseTokenizer;
import org.codelibs.neologd.ipadic.lucene.analysis.ja.tokenattributes.ReadingAttribute;

import java.io.IOException;
import java.io.StringReader;

public class KuromojiNeologdAnalyzer extends AnalyzeWord{


    private final Analyzer analyzer;
    public KuromojiNeologdAnalyzer() {
        super("kuromoji_neologd");

        // no user dictionary
        analyzer = new Analyzer() {
            @Override
            protected TokenStreamComponents createComponents(String fieldName) {
                JapaneseTokenizer t = new JapaneseTokenizer(null, false, JapaneseTokenizer.Mode.NORMAL);
                return new TokenStreamComponents(t, t);
            }
        };
    }

    @Override
    public Output analyzeWord(String word) {
        String kana = "";
        TokenStream ts = analyzer.tokenStream("ignored", new StringReader(word));
        ReadingAttribute reading = ts.getAttribute(ReadingAttribute.class);

        try {
            ts.reset();
            StringBuilder sb = new StringBuilder();
            while(ts.incrementToken()){
                if (reading.getReading() == null) {
                    // TODO append other special character if clarify
                    sb.append("");
                } else {
                    sb.append(reading.getReading());
                }
                sb.append(" ");
            }
            kana = sb.toString().trim();
            ts.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return createOutput(word, kana);
    }
}
