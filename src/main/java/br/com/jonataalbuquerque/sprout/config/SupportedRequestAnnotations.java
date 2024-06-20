package br.com.jonataalbuquerque.sprout.config;

import br.com.jonataalbuquerque.sprout.annotations.Delete;
import br.com.jonataalbuquerque.sprout.annotations.Get;
import br.com.jonataalbuquerque.sprout.annotations.Post;
import br.com.jonataalbuquerque.sprout.annotations.Put;

import java.lang.annotation.Annotation;
import java.util.List;

public class SupportedRequestAnnotations {
    private SupportedRequestAnnotations() {
    }

    public static List<Class<? extends Annotation>> get() {
        return List.of(Get.class, Post.class, Put.class, Delete.class);
    }
}
