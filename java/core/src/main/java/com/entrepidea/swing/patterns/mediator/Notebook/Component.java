package com.entrepidea.swing.patterns.mediator.Notebook;

/**
 * Component interface
 */
public interface Component {
    void setMediator(Mediator mediator);
    String getName();
}
