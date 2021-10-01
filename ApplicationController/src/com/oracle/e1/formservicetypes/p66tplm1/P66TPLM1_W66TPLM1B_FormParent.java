package com.oracle.e1.formservicetypes.p66tplm1;

import com.oracle.e1.formservicetypes.FormParent;
import oracle.adfmf.java.beans.PropertyChangeSupport;
import oracle.adfmf.java.beans.PropertyChangeListener;


public class P66TPLM1_W66TPLM1B_FormParent extends FormParent {

    private P66TPLM1_W66TPLM1B fs_P66TPLM1_W66TPLM1B = new P66TPLM1_W66TPLM1B();

    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this); 

    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }
    public P66TPLM1_W66TPLM1B_FormParent() {
        super();
    }

    public void setFs_P66TPLM1_W66TPLM1B(P66TPLM1_W66TPLM1B fs_P66TPLM1_W66TPLM1B) {
        P66TPLM1_W66TPLM1B oldfs_P66TPLM1_W66TPLM1B = this.fs_P66TPLM1_W66TPLM1B;
        this.fs_P66TPLM1_W66TPLM1B = fs_P66TPLM1_W66TPLM1B;
        propertyChangeSupport.firePropertyChange("fs_P66TPLM1_W66TPLM1B", oldfs_P66TPLM1_W66TPLM1B,fs_P66TPLM1_W66TPLM1B);
    }

    public P66TPLM1_W66TPLM1B getFs_P66TPLM1_W66TPLM1B() {
        return fs_P66TPLM1_W66TPLM1B;
    }
}
