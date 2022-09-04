package com.entrepidea.jersey.swing.patterns.mediator.Notebook;

/**
 * Component interface
 */
public interface Component {
    void setMediator(Mediator mediator);
    String getName();
}
