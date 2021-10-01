package com.oracle.e1.formservicetypes.p66tplm1;

import com.oracle.e1.formservicetypes.FormMobile;
import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;

public class P66TPLM1_W66TPLM1B extends FormMobile {

    private String title;
    private P66TPLM1_W66TPLM1B_FormData data = new P66TPLM1_W66TPLM1B_FormData();
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this); 

    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }

    public P66TPLM1_W66TPLM1B() {
        super();
    }

    public void setData(P66TPLM1_W66TPLM1B_FormData data) {
        P66TPLM1_W66TPLM1B_FormData olddata = this.data;
        this.data = data;
        propertyChangeSupport.firePropertyChange("data", olddata,data);
    }

    public P66TPLM1_W66TPLM1B_FormData getData() {
        return data;
    }
 public void setTitle(String title)
    {
        this.title = title;
    }
    public String getTitle()
    {
        return title;
    }
}
