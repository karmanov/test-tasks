package io.karmanov.reactive;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;


public class S01_IntroductionTest {

    private StackOverflowClient client;

    @Before
    public void setUp() throws Exception {
        client = new StackOverflowClient();
    }

    @Test
    public void blockingCall() {
        final String title = client.mostRecentQuestionAbout("java");
        System.out.println("Most recent Java question: '" + title + "'");
    }

    @Test
    public void executorService() {
        final Callable<String> task = () -> client.mostRecentQuestionAbout("java");
        //final Future<String> javaQuestionFuture =
        final String javaQuestion = "";
        System.out.println("Found '" + javaQuestion + "'");
    }
}
