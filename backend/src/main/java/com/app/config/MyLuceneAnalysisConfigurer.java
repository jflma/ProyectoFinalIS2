package com.app.config;

import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurationContext;
import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurer;

public class MyLuceneAnalysisConfigurer implements LuceneAnalysisConfigurer {
       private static final String STANDARD = "standard";
       private static final String LOWERCASE = "lowercase";
       private static final String SNOWBALL = "snowballPorter";
       private static final String LANGUAGE = "language";
       private static final String ASCIIFOLDING = "asciiFolding";


   @Override
  public void configure(LuceneAnalysisConfigurationContext context) {
    context.analyzer("english").custom()
                    .tokenizer(STANDARD)
                    .tokenFilter(LOWERCASE)
                    .tokenFilter(SNOWBALL).param(LANGUAGE, "English")
                    .tokenFilter(ASCIIFOLDING);

    // Analizador para texto en español
    context.analyzer("spanish").custom()
           .tokenizer(STANDARD)
           .tokenFilter(LOWERCASE)
           .tokenFilter("stop")
              .param("words", "spanish.txt")
              .param("ignoreCase", "true")
           .tokenFilter("spanishLightStem")
           .tokenFilter(ASCIIFOLDING);

    // Analizador para busqueda multilanguage
    context.analyzer("multilingual").custom()
           .tokenizer(STANDARD)
           .tokenFilter(LOWERCASE)
           .tokenFilter("stop")
               .param("words", "spanish.txt") // Archivo con stop words en español e inglés
               .param("ignoreCase", "true")
           .tokenFilter(SNOWBALL)
               .param(LANGUAGE, "Spanish")
           .tokenFilter(SNOWBALL)
               .param(LANGUAGE, "English")
           .tokenFilter(ASCIIFOLDING);

    // analizador para nombres
    context.analyzer("name").custom()
           .tokenizer(STANDARD)
           .tokenFilter(LOWERCASE)
           .tokenFilter("wordDelimiterGraph")
           .tokenFilter(ASCIIFOLDING)
           .tokenFilter("stop")
           .tokenFilter("removeDuplicates");

    // analizador para busqueda exacta
    context.analyzer("exact").custom()
           .tokenizer("keyword")
           .tokenFilter(LOWERCASE)
           .tokenFilter(ASCIIFOLDING);

    context.normalizer("exact").custom()
           .tokenFilter(LOWERCASE)
           .tokenFilter(ASCIIFOLDING);
    
  }

}
