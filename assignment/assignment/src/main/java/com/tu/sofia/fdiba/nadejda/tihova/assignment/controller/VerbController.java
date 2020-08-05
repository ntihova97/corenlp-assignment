package com.tu.sofia.fdiba.nadejda.tihova.assignment.controller;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@RestController
public class VerbController {

    private StanfordCoreNLP pipeline;

    @PostConstruct
    void init() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        this.pipeline = new StanfordCoreNLP(props);
    }


    @PostMapping("/sentence/tokens/{part}")
    public ResponseEntity<List<String>> extractFromText(@RequestBody String text, @PathVariable(value = "part") String partOfSentence) {

        // create an empty Annotation just with the given text
        Annotation document = new Annotation(text);

        // run all Annotators on this text
        pipeline.annotate(document);
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);


        List<String> responseList = new ArrayList<>();
        for (CoreMap sentence : sentences) {
            // traversing the words in the current sentence
            // a CoreLabel is a CoreMap with additional token-specific methods
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                // this is the text of the token
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                // this is the POS tag of the token
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);


                switch (partOfSentence) {
                    case "noun":
                        if (pos.startsWith("NN")) {
                            responseList.add(word);
                        }
                        break;
                    case "verb":
                        if (pos.startsWith("VB")) {
                            responseList.add(word);
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid type of sentence: " + partOfSentence);
                }

            }

        }


        return ResponseEntity.ok(responseList);
    }

}
