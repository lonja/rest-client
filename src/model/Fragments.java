package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

import java.util.Collection;
import java.util.HashMap;

public class Fragments {

    private HashMap<String, VBox> fragments;

    public Fragments(VBox ... items) {
        fragments = new HashMap<String, VBox>();
        for(VBox item: items) {
            fragments.put(item.toString(), item);
        }
    }

    public VBox getFragment(String key) {
        return fragments.get(key);
    }



    public ObservableList<VBox> getAllFragments() {

        return FXCollections.observableArrayList(fragments.values());

    }
}
