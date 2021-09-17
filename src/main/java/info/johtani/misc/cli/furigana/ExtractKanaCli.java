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

package info.johtani.misc.cli.furigana;

import info.johtani.misc.cli.furigana.analyzer.AnalyzeWord;
import info.johtani.misc.cli.furigana.analyzer.KuromojiNeologdAnalyzer;
import info.johtani.misc.cli.furigana.analyzer.SudachiAanalyzer;
import info.johtani.misc.cli.furigana.writer.*;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;


@Command(name = "extract-kana",
        mixinStandardHelpOptions = true,
        version = "0.1.0",
        description = ""
)
public class ExtractKanaCli implements Callable<Integer> {
    @Parameters(arity = "1", description = "The word what you want to extract furigana if default mode . The input file path that contains words for extracting furigana, if ")
    String input;

    @Option(names = {"-m", "--mode"},
            description = "The input mode. ${COMPLETION-CANDIDATES} can be specified. Default is ${DEFAULT-VALUE}"
    )
    InputMode mode = InputMode.Word;

    @Option(names = {"-o", "--output"},
            description = "The output format. ${COMPLETION-CANDIDATES} can be specified. Default is ${DEFAULT-VALUE}"
    )
    OutputFormat format = OutputFormat.CSV;

    KanaWriter writer;

    public static void main(String... args) {
        int exitCode = new CommandLine(new ExtractKanaCli()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        int exitCode = 0;
        // TODO add file writer?
        writer = KanaWriter.createKanaWriter(new OutputStreamWriter(System.out), format);
        List<AnalyzeWord> analyzers = makeAnalyzers();
        writer.setHeader(makeHeader(analyzers));

        switch (mode) {
            case File -> {
                try {
                    if (input != null && !input.isEmpty()) {
                        List<String> words = readFile(input);
                        for (String word : words) {
                            List<Output> outputs = analyzeWord(word, analyzers);
                            writer.write(outputs);
                        }
                    }
                } catch (IOException ioe) {
                    System.err.println(ioe.getMessage());
                    ioe.printStackTrace();
                    exitCode = 1;
                }
            }
            case Word -> {
                List<Output> outputs = analyzeWord(input, analyzers);
                writer.write(outputs);
            }
        }
        writer.close();
        return exitCode;
    }

    private List<AnalyzeWord> makeAnalyzers() {
        List<AnalyzeWord> analyzers = new ArrayList<>();
        analyzers.add(new KuromojiNeologdAnalyzer());
        try {
            analyzers.add(new SudachiAanalyzer());
        } catch (Exception e) {
            // TODO log
            System.err.println("SudachiAnalyzer creation failed... " + e.getMessage());
            //e.printStackTrace();
        }
        return analyzers;
    }

    private String[] makeHeader(List<AnalyzeWord> analyzers) {
        String[] header = new String[analyzers.size() + 1];
        header[0] = "original";
        int i = 1;
        for (AnalyzeWord analyzer : analyzers) {
            header[i] = analyzer.getName();
            i++;
        }
        return header;
    }

    private List<Output> analyzeWord(String word, List<AnalyzeWord> analyzers) {
        List<Output> outputs = new ArrayList<>();
        for (AnalyzeWord analyzer : analyzers) {
            outputs.add(analyzer.analyzeWord(word));
        }
        return outputs;
    }

    private List<String> readFile(String path) throws IOException {
        Path file = Paths.get(path);
        return Files.readAllLines(file);
    }


}