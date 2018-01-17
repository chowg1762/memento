package com.example.wongeun.memento.editor.presenter;

import java.util.Observer;

/**
 * Created by wongeun on 1/16/18.
 */

public interface Subject {
    public void register(Observer observer);
    public void unregister(Observer observer);
    public void notifyObservers();
}
